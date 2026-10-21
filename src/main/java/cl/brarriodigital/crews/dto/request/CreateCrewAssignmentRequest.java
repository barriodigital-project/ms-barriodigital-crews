package cl.brarriodigital.crews.dto.request;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CreateCrewAssignmentRequest(
    @NotNull UUID requestId,
    @NotNull UUID crewId,
    Instant scheduledAt
) {
}
