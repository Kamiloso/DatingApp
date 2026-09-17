package page.se3.datingapp.infrastructure.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app")
public record AppConfig(
        @NotBlank
        String startMessage,

        @Valid
        @NotNull
        Auth auth,

        @Valid
        @NotNull
        Limits limits,

        @Valid
        @NotNull
        Matching matching,

        @Valid
        @NotNull
        Quiz quiz
) {
    public record Auth(
            @NotBlank
            String url,

            @NotBlank
            String clientId,

            @NotBlank
            String allowedEmailRegex
    ) {}

    public record Limits(
            @NotNull
            @Positive
            Integer batchSize
    ) {}

    public record Matching(
            @NotNull
            @PositiveOrZero
            Integer maxMatchesPerUser,

            @NotNull
            @PositiveOrZero
            Integer maxAgeDifference,

            @Valid
            @NotNull
            Cycle cycle
    ) {
        public record Cycle(
                @NotBlank
                String cron,

                @NotBlank
                String timezone,

                @NotNull
                @Positive
                Integer lockAtLeastForMinutes,

                @NotNull
                @Positive
                Integer lockAtMostForMinutes
        ) {}
    }

    public record Quiz(
            @NotNull
            Integer readyIndex

            // TODO: make more quiz information
    ) {}
}
