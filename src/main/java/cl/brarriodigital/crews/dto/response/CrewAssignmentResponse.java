package cl.brarriodigital.crews.dto.response;

import java.time.Instant;
import java.util.UUID;

import cl.brarriodigital.crews.model.CrewAssignmentStatus;

public record CrewAssignmentResponse(
    UUID id,
    UUID requestId,
    UUID crewId,
    Instant scheduledAt,
    CrewAssignmentStatus status
) {
}
