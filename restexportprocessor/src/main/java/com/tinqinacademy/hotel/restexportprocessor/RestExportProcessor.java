package com.tinqinacademy.hotel.restexportprocessor;

import com.squareup.javapoet.*;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

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
import java.util.*;

@SupportedAnnotationTypes("com.tinqinacademy.hotel.restexportprocessor.RestExport")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class RestExportProcessor extends AbstractProcessor {
    private Elements elements;
    private Types types;

    private final Map<String, TypeSpec.Builder> interfaceBuilders = new HashMap<>();


    @Override
    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elements = processingEnv.getElementUtils();
        types = processingEnv.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(RestExport.class)) {
            if (element.getKind() == ElementKind.METHOD) {
                ExecutableElement methodElement = (ExecutableElement) element;
                RestExport restExport = methodElement.getAnnotation(RestExport.class);
                String clientName = "HotelClient";

                TypeSpec.Builder interfaceBuilder = interfaceBuilders.computeIfAbsent(clientName, name ->
                        TypeSpec.interfaceBuilder(name)
                                .addModifiers(Modifier.PUBLIC)
                                .addAnnotation(AnnotationSpec.builder(Headers.class)
                                        .addMember("value", "$S", "Content-Type: application/json")
                                        .build()));

                addMethodToInterface(interfaceBuilder, methodElement);
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

    private void addMethodToInterface(TypeSpec.Builder interfaceBuilder, ExecutableElement methodElement) {
        String methodName = methodElement.getSimpleName().toString();
        String route = getRoute(methodElement);
        TypeMirror returnTypeMirror = getSwaggerResponseType(methodElement);

        if (Optional.ofNullable(returnTypeMirror).isPresent()) {
            TypeName returnType = TypeName.get(returnTypeMirror);

            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(methodName)
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .returns(returnType);

            setupRequestLine(addParameters(methodBuilder, methodElement), route, methodBuilder);

            interfaceBuilder.addMethod(methodBuilder.build());
        }

    }

    private TypeMirror getSwaggerResponseType(ExecutableElement methodElement) {
        ApiResponses apiResponses = methodElement.getAnnotation(ApiResponses.class);
        if (apiResponses != null) {
            for (ApiResponse apiResponse : apiResponses.value()) {
                Content[] contents = apiResponse.content();
                for (Content content : contents) {
                    Schema schema = content.schema();
                    if (schema != null) {
                        try {
                            schema.implementation();
                        } catch (MirroredTypeException mte) {
                            return mte.getTypeMirror();
                        }
                    }
                }
            }
        }
        return null;
    }

    private String getRoute(ExecutableElement methodElement) {
        String httpMethod = "";
        String restRoute = "";

        if (methodElement.getAnnotation(GetMapping.class) != null) {
            httpMethod = "GET";
            restRoute = extractRoute(methodElement.getAnnotation(GetMapping.class).value());
        } else if (methodElement.getAnnotation(PostMapping.class) != null) {
            httpMethod = "POST";
            restRoute = extractRoute(methodElement.getAnnotation(PostMapping.class).value());
        } else if (methodElement.getAnnotation(PutMapping.class) != null) {
            httpMethod = "PUT";
            restRoute = extractRoute(methodElement.getAnnotation(PutMapping.class).value());
        } else if (methodElement.getAnnotation(DeleteMapping.class) != null) {
            httpMethod = "DELETE";
            restRoute = extractRoute(methodElement.getAnnotation(DeleteMapping.class).value());
        }  else if (methodElement.getAnnotation(PatchMapping.class) != null) {
            httpMethod = "PATCH";
            restRoute = extractRoute(methodElement.getAnnotation(PatchMapping.class).value());
        } else if (methodElement.getAnnotation(RequestMapping.class) != null) {
            RequestMapping requestMapping = methodElement.getAnnotation(RequestMapping.class);
            if (requestMapping.method().length > 0) {
                httpMethod = requestMapping.method()[0].name();
            }
            restRoute = extractRoute(requestMapping.value());
        }


        return httpMethod + " " + restRoute;
    }

    private String extractRoute(String[] paths) {
        if (paths.length > 0) {
            return paths[0];
        }
        return "";
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
