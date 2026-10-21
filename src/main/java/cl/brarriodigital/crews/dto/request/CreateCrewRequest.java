package cl.brarriodigital.crews.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCrewRequest(@NotBlank String name, Boolean available) {
}
