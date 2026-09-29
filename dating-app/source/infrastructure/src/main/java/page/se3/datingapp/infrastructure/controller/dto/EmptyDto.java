package page.se3.datingapp.infrastructure.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A record with no useful fields")
public record EmptyDto(
        Integer ignored
) {}
