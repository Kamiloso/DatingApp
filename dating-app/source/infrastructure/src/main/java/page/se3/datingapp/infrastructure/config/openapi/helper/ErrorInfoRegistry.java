package page.se3.datingapp.infrastructure.config.openapi.helper;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import page.se3.datingapp.domain.exception.abort.AbortException;
import page.se3.datingapp.infrastructure.config.controller.response.dto.ResponseErrorDto;

public class ErrorInfoRegistry {

    private final Operation operation;
    private final ApiResponses apiResponses;

    public ErrorInfoRegistry(Operation operation) {
        this.operation = operation;
        this.apiResponses = operation.getResponses();
    }

    public void addSecurityRequirement(String schemeName) {
        operation.addSecurityItem(new SecurityRequirement().addList(schemeName));
    }

    public void addDocumentation(AbortException abortException) {
        int httpStatus = abortException.getStatus();

        String httpStatusStr = String.valueOf(httpStatus);
        String reasonPhrase = HttpStatus.valueOf(httpStatus).getReasonPhrase();
        String code = abortException.getErrorCode();
        String message = abortException.getMessage();

        ApiResponse apiResponse = apiResponses.computeIfAbsent(httpStatusStr, key -> new ApiResponse());

        String description = apiResponse.getDescription();
        if (description == null || description.isEmpty()) {
            description = reasonPhrase;
        }

        description += "\n\n`" + code + "` *- " + message + "*";
        apiResponse.setDescription(description);

        if (apiResponse.getContent() == null) {
            String schemaName = ResponseErrorDto.class.getSimpleName();
            Schema<?> schema = new Schema<>().$ref("#/components/schemas/" + schemaName);

            MediaType mediaType = new MediaType().schema(schema);
            Content content = new Content().addMediaType("application/json", mediaType);

            apiResponse.setContent(content);
        }
    }
}
