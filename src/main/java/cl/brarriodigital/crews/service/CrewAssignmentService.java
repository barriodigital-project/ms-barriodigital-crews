package cl.brarriodigital.crews.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.brarriodigital.crews.dto.request.CreateCrewAssignmentRequest;
import cl.brarriodigital.crews.dto.response.CrewAssignmentResponse;
import cl.brarriodigital.crews.exception.CrewUnavailableException;
import cl.brarriodigital.crews.exception.ResourceNotFoundException;
import cl.brarriodigital.crews.model.CrewAssignment;
import cl.brarriodigital.crews.model.CrewAssignmentStatus;
import cl.brarriodigital.crews.repository.CrewAssignmentRepository;
import cl.brarriodigital.crews.repository.CrewRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrewAssignmentService {

    private final CrewRepository crewRepository;
    private final CrewAssignmentRepository assignmentRepository;

    @Transactional
    public CrewAssignmentResponse create(CreateCrewAssignmentRequest request) {
        var crew = crewRepository.findById(request.crewId())
            .orElseThrow(() -> new ResourceNotFoundException(
                "Cuadrilla no encontrada: " + request.crewId()
            ));
        if (!crew.isAvailable()) {
            throw new CrewUnavailableException("La cuadrilla no está disponible");
        }

        var assignment = new CrewAssignment();
        assignment.setCrew(crew);
        assignment.setRequestId(request.requestId());
        assignment.setScheduledAt(request.scheduledAt());
        assignment.setStatus(CrewAssignmentStatus.ASIGNADA);
        return toResponse(assignmentRepository.save(assignment));
    }

    @Transactional(readOnly = true)
    public List<CrewAssignmentResponse> findAll(UUID requestId, UUID crewId) {
        List<CrewAssignment> assignments;
        if (requestId != null && crewId != null) {
            assignments = assignmentRepository
                .findAllByRequestIdAndCrew_IdOrderByScheduledAtAscIdAsc(requestId, crewId);
        } else if (requestId != null) {
            assignments = assignmentRepository.findAllByRequestIdOrderByScheduledAtAscIdAsc(requestId);
        } else if (crewId != null) {
            assignments = assignmentRepository.findAllByCrew_IdOrderByScheduledAtAscIdAsc(crewId);
        } else {
            assignments = assignmentRepository.findAllByOrderByScheduledAtAscIdAsc();
        }
        return assignments.stream().map(this::toResponse).toList();
    }

    private CrewAssignmentResponse toResponse(CrewAssignment assignment) {
        return new CrewAssignmentResponse(
            assignment.getId(),
            assignment.getRequestId(),
            assignment.getCrew().getId(),
            assignment.getScheduledAt(),
            assignment.getStatus()
        );
    }
}
