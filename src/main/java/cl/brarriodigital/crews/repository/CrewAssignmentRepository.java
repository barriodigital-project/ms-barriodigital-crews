package cl.brarriodigital.crews.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.brarriodigital.crews.model.CrewAssignment;

public interface CrewAssignmentRepository extends JpaRepository<CrewAssignment, UUID> {

    List<CrewAssignment> findAllByOrderByScheduledAtAscIdAsc();

    List<CrewAssignment> findAllByRequestIdOrderByScheduledAtAscIdAsc(UUID requestId);

    List<CrewAssignment> findAllByCrew_IdOrderByScheduledAtAscIdAsc(UUID crewId);

    List<CrewAssignment> findAllByRequestIdAndCrew_IdOrderByScheduledAtAscIdAsc(
        UUID requestId,
        UUID crewId
    );
}
