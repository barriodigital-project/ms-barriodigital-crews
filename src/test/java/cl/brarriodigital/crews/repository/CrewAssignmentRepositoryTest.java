package cl.brarriodigital.crews.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import cl.brarriodigital.crews.model.Crew;
import cl.brarriodigital.crews.model.CrewAssignment;

@DataJpaTest
class CrewAssignmentRepositoryTest {

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private CrewAssignmentRepository assignmentRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void storesCrewAndFindsAssignmentsByRequest() {
        var crew = new Crew();
        crew.setName("Cuadrilla Norte");
        crew = crewRepository.saveAndFlush(crew);

        var requestId = UUID.randomUUID();
        var assignment = new CrewAssignment();
        assignment.setCrew(crew);
        assignment.setRequestId(requestId);
        assignmentRepository.saveAndFlush(assignment);

        assertThat(crewRepository.findAllByOrderByNameAscIdAsc())
            .extracting(Crew::getName)
            .containsExactly("Cuadrilla Norte");
        assertThat(assignmentRepository.findAllByRequestIdOrderByScheduledAtAscIdAsc(requestId))
            .hasSize(1);
        assertThat(assignmentRepository.findAllByCrew_IdOrderByScheduledAtAscIdAsc(crew.getId()))
            .extracting(CrewAssignment::getCrew)
            .extracting(Crew::getId)
            .containsExactly(crew.getId());

        entityManager.clear();
        var storedCrew = crewRepository.findById(crew.getId()).orElseThrow();
        assertThat(storedCrew.getAssignments()).hasSize(1);
    }
}
