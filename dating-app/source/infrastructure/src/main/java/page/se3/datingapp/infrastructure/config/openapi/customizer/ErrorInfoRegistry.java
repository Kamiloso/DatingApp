package page.se3.datingapp.infrastructure.config.openapi.customizer;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import page.se3.datingapp.domain.exception.abort.AbortException;
import page.se3.datingapp.infrastructure.controller.dto.ErrorDto;

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
        String errorType = abortException.getErrorType();
        String reasonPhrase = HttpStatus.valueOf(httpStatus).getReasonPhrase();

        ApiResponse apiResponse = apiResponses.computeIfAbsent(httpStatusStr, key -> new ApiResponse());

        updateDescription(apiResponse, reasonPhrase, errorType);
        ensureContentSchema(apiResponse);
    }

    private void updateDescription(ApiResponse apiResponse, String reasonPhrase, String errorType) {
        String oldDescription = apiResponse.getDescription();
        apiResponse.setDescription(oldDescription == null || oldDescription.isEmpty()
                ? reasonPhrase + "\n\n`" + errorType + "`"
                : oldDescription + ", `" + errorType + "`"
        );
    }

    private void ensureContentSchema(ApiResponse apiResponse) {
        if (apiResponse.getContent() == null) {
            String schemaName = ErrorDto.class.getSimpleName();
            Schema<?> schema = new Schema<>().$ref("#/components/schemas/" + schemaName);

            MediaType mediaType = new MediaType().schema(schema);
            Content content = new Content().addMediaType("application/json", mediaType);

            apiResponse.setContent(content);
        }
    }
}
