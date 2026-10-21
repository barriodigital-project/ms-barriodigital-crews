package cl.brarriodigital.crews.dto.response;

import java.util.UUID;

public record CrewResponse(UUID id, String name, boolean available) {
}
