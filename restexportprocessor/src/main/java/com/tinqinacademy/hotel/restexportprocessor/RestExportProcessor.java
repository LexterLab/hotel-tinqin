package com.tinqinacademy.hotel.restexportprocessor;

import com.squareup.javapoet.*;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.processing.*;
import javax.lang.model.element.*;

import javax.lang.model.SourceVersion;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("com.tinqinacademy.hotel.restexportprocessor.RestExport")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class RestExportProcessor extends AbstractProcessor {
    private Filer filer;
    private Elements elements;
    private Types types;

    private final Map<String, TypeSpec.Builder> interfaceBuilders = new HashMap<>();


    @Override
    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        elements = processingEnv.getElementUtils();
        types = processingEnv.getTypeUtils();
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(RestExport.class)) {
            if (element.getKind() == ElementKind.METHOD) {
                ExecutableElement methodElement = (ExecutableElement) element;
                RestExport restExport = methodElement.getAnnotation(RestExport.class);
                String clientName = "MotelClient";

                TypeSpec.Builder interfaceBuilder = interfaceBuilders.computeIfAbsent(clientName, name ->
                        TypeSpec.interfaceBuilder(name)
                                .addModifiers(Modifier.PUBLIC)
                                .addAnnotation(AnnotationSpec.builder(Headers.class)
                                        .addMember("value", "$S", "Content-Type: application/json")
                                        .build()));

                addMethodToInterface(interfaceBuilder, methodElement, restExport);
            }
        }

        for (Map.Entry<String, TypeSpec.Builder> entry : interfaceBuilders.entrySet()) {
            TypeSpec interfaceSpec = entry.getValue().build();
            JavaFile javaFile = JavaFile.builder("com.tinqinacademy.hotel.restexport", interfaceSpec).build();
            try {
                Path outputPath = Paths.get("./restexport/src/main/java/com/tinqinacademy/hotel/restexport", entry.getKey() + ".java");
                Files.createDirectories(outputPath.getParent());
                try (var writer = Files.newBufferedWriter(outputPath)) {
                    javaFile.writeTo(writer);
                }
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Error generating Feign client: " + e.getMessage());
            }
        }
        return true;
    }

    private void addMethodToInterface(TypeSpec.Builder interfaceBuilder, ExecutableElement methodElement, RestExport restExport) {
        String methodName = methodElement.getSimpleName().toString();
        String route = restExport.method() + " " + restExport.route();
        TypeMirror returnTypeMirror = null;

        try {
            restExport.output();
        } catch (MirroredTypeException mte) {
            returnTypeMirror = mte.getTypeMirror();
        }

        TypeName returnType = TypeName.get(returnTypeMirror);

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .returns(returnType);

        setupRequestLine(addParameters(methodBuilder, methodElement), route, methodBuilder);

        interfaceBuilder.addMethod(methodBuilder.build());
    }

    private List<? extends  VariableElement> addParameters(MethodSpec.Builder methodBuilder, ExecutableElement methodElement) {
        List<? extends VariableElement> parameters = methodElement.getParameters();

        for (VariableElement parameter : parameters) {
            TypeMirror paramTypeMirror = parameter.asType();
            TypeName paramType = TypeName.get(parameter.asType());
            String paramName = parameter.getSimpleName().toString();

            ParameterSpec.Builder parameterSpecBuilder = ParameterSpec.builder(paramType, paramName);


            if (!isOperationInput(paramTypeMirror, types, elements)) {
                parameterSpecBuilder.addAnnotation(AnnotationSpec.builder(Param.class)
                        .addMember("value", "$S", paramName)
                        .build());
            }

            methodBuilder.addParameter(parameterSpecBuilder.build());
        }
        return parameters;
    }

    private void setupRequestLine(List<? extends VariableElement> elements, String route, MethodSpec.Builder methodBuilder) {
        boolean firstElement = true;
        StringBuilder requestLine = new StringBuilder(route);
        for (VariableElement element : elements) {
            if (element.getAnnotation(RequestParam.class) != null) {
                if (firstElement) {
                    requestLine.append("?");
                    firstElement = false;
                }
                String paramName = element.getSimpleName().toString();
                requestLine.append(paramName).append("=").append(String.format("{%s}", paramName));
                int numberOfElement = elements.indexOf(element);
                if (numberOfElement < elements.size() - 1) {
                    requestLine.append("&");
                }
            }
        }

        methodBuilder.addAnnotation(AnnotationSpec.builder(RequestLine.class)
                .addMember("value", "$S", requestLine.toString())
                .build());
    }

    private boolean isOperationInput(TypeMirror typeMirror, Types typeUtils, Elements elementUtils) {
        TypeElement operationInputElement = elementUtils.getTypeElement("com.tinqinacademy.hotel.api.base.OperationInput");
        return typeUtils.isSubtype(typeMirror, operationInputElement.asType());
    }
}
