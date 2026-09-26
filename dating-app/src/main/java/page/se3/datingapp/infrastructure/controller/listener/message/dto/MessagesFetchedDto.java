package page.se3.datingapp.infrastructure.controller.listener.message.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MessagesFetchedDto(
        @NotNull
        List<@NotNull @Valid MessageDto> persistent,

        @NotNull
        List<@NotNull @Valid MessageDto> dirty
) {}
