package cl.brarriodigital.crews.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

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

    @Test
    void storesCrewAndFindsAssignmentsByRequest() {
        var crew = new Crew();
        crew.setName("Cuadrilla Norte");
        crew = crewRepository.saveAndFlush(crew);

        var requestId = UUID.randomUUID();
        var assignment = new CrewAssignment();
        assignment.setCrewId(crew.getId());
        assignment.setRequestId(requestId);
        assignmentRepository.saveAndFlush(assignment);

        assertThat(crewRepository.findAllByOrderByNameAscIdAsc())
            .extracting(Crew::getName)
            .containsExactly("Cuadrilla Norte");
        assertThat(assignmentRepository.findAllByRequestIdOrderByScheduledAtAscIdAsc(requestId))
            .hasSize(1);
    }
}
