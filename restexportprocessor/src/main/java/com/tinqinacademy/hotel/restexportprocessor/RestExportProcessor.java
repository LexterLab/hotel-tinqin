package com.tinqinacademy.hotel.restexportprocessor;

import com.squareup.javapoet.*;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.processing.*;
import javax.lang.model.element.*;

import javax.lang.model.SourceVersion;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Slf4j
@SupportedAnnotationTypes("com.tinqinacademy.hotel.restexportprocessor.RestExport")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class RestExportProcessor extends AbstractProcessor {
    private Filer filer;
    private Elements elements;
    private Types types;

    @Override
    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        elements = processingEnv.getElementUtils();
        types = processingEnv.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getRootElements()) {
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement classElement = (TypeElement) element;
                for (Element enclosedElement : classElement.getEnclosedElements()) {
                    if (enclosedElement.getKind() == ElementKind.METHOD) {
                        RestExport restExport = enclosedElement.getAnnotation(RestExport.class);
                        if (restExport != null) {
                            generateFeignClientInterface(classElement, restExport);
                        }
                    }
                }
            }
        }
        return true;
    }

    private void generateFeignClientInterface(Element methodElement, RestExport restExport) {
        String clientName = "MotelClient";

        TypeSpec.Builder interfaceBuilder = TypeSpec.interfaceBuilder(clientName)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(Headers.class)
                        .addMember("value", "$S", "Content-Type: application/json")
                        .build());

        String methodName = methodElement.getSimpleName().toString();
        String route = restExport.route();
        TypeMirror returnTypeMirror = null;

        try {
            restExport.output();
        } catch (MirroredTypeException mte) {
            returnTypeMirror = mte.getTypeMirror();
        }

        TypeName returnType = TypeName.get(returnTypeMirror);
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .returns(returnType)
                .addAnnotation(AnnotationSpec.builder(RequestLine.class)
                        .addMember("value", "$S", route)
                        .build());

        addParameters(methodBuilder, (ExecutableElement) methodElement); //WIP

        interfaceBuilder.addMethod(methodBuilder.build());

        JavaFile javaFile = JavaFile.builder("com.tinqinacademy.hotel.restexport", interfaceBuilder.build())
                .build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Error generating Feign client: " + e.getMessage());
        }
    }


    private void addParameters(MethodSpec.Builder methodBuilder, ExecutableElement methodElement) {
        List<? extends VariableElement> parameters = methodElement.getParameters();

        for (VariableElement parameter : parameters) {
            TypeMirror paramTypeMirror = parameter.asType();
            TypeName paramType = TypeName.get(parameter.asType());
            String paramName = parameter.getSimpleName().toString();

            methodBuilder.addParameter(paramType, paramName);

            if (!isOperationInput(paramTypeMirror, types, elements)) {
                methodBuilder.addAnnotation(AnnotationSpec.builder(Param.class)
                        .addMember("value", "$S", paramName)
                        .build());
            }
        }
    }
    private boolean isOperationInput(TypeMirror typeMirror, Types typeUtils, Elements elementUtils) {
        TypeElement operationInputElement = elementUtils.getTypeElement("com.tinqinacademy.hotel.api.base.OperationInput");
        return typeUtils.isSubtype(typeMirror, operationInputElement.asType());
    }
}
