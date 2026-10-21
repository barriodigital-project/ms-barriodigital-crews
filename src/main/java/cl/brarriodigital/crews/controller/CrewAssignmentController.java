package cl.brarriodigital.crews.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.brarriodigital.crews.dto.request.CreateCrewAssignmentRequest;
import cl.brarriodigital.crews.dto.response.CrewAssignmentResponse;
import cl.brarriodigital.crews.service.CrewAssignmentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/crew-assignments")
@RequiredArgsConstructor
public class CrewAssignmentController {

    private final CrewAssignmentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CrewAssignmentResponse create(@Valid @RequestBody CreateCrewAssignmentRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<CrewAssignmentResponse> findAll(
        @RequestParam(required = false) UUID requestId,
        @RequestParam(required = false) UUID crewId
    ) {
        return service.findAll(requestId, crewId);
    }
}
