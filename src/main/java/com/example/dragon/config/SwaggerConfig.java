package com.example.dragon.config;

import com.example.dragon.swagger.GenerateApiDoc;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;

@Configuration
public class SwaggerConfig {

    @Bean
    public OperationCustomizer customizeOperation() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            GenerateApiDoc generateApiDoc = handlerMethod.getMethodAnnotation(GenerateApiDoc.class);
            if (generateApiDoc != null) {
                operation.setSummary(generateApiDoc.summary());
                operation.setDescription(generateApiDoc.description());

                ApiResponses apiResponses = new ApiResponses();
                ApiResponse apiResponse = new ApiResponse()
                        .description(generateApiDoc.responseDescription())
                        .content(new Content().addMediaType(generateApiDoc.mediaType(),
                                new MediaType().schema(new Schema<>().type("object").$ref("#/components/schemas/" + generateApiDoc.responseClass().getSimpleName()))));

                apiResponses.addApiResponse(generateApiDoc.responseCode(), apiResponse);
                operation.setResponses(apiResponses);
            }
            return operation;
        };
    }
}
