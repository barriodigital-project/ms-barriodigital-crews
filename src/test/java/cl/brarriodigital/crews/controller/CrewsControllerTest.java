package cl.brarriodigital.crews.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import cl.brarriodigital.crews.model.Crew;
import cl.brarriodigital.crews.repository.CrewAssignmentRepository;
import cl.brarriodigital.crews.repository.CrewRepository;

@SpringBootTest
@AutoConfigureMockMvc
class CrewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private CrewAssignmentRepository assignmentRepository;

    @BeforeEach
    void setUp() {
        assignmentRepository.deleteAll();
        crewRepository.deleteAll();
    }

    @Test
    void createsCrewAndAssignmentAndFiltersByRequest() throws Exception {
        mockMvc.perform(post("/api/v1/crews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Cuadrilla Sur\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.available").value(true));
        var crewId = crewRepository.findAll().getFirst().getId();
        var requestId = UUID.randomUUID();

        mockMvc.perform(post("/api/v1/crew-assignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"requestId":"%s","crewId":"%s","scheduledAt":"2026-06-01T10:00:00Z"}
                    """.formatted(requestId, crewId)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status").value("ASIGNADA"));

        mockMvc.perform(get("/api/v1/crew-assignments").param("requestId", requestId.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
        mockMvc.perform(get("/api/v1/crews"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void rejectsAssignmentToUnavailableOrMissingCrew() throws Exception {
        var unavailableCrew = new Crew();
        unavailableCrew.setName("Cuadrilla ocupada");
        unavailableCrew.setAvailable(false);
        unavailableCrew = crewRepository.saveAndFlush(unavailableCrew);

        var unavailableCrewId = unavailableCrew.getId();
        var requestId = UUID.randomUUID();
        mockMvc.perform(post("/api/v1/crew-assignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"requestId":"%s","crewId":"%s"}
                    """.formatted(requestId, unavailableCrewId)))
            .andExpect(status().isConflict());

        mockMvc.perform(post("/api/v1/crew-assignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"requestId":"%s","crewId":"%s"}
                    """.formatted(requestId, UUID.randomUUID())))
            .andExpect(status().isNotFound());
    }
}
