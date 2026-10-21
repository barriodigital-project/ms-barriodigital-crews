package cl.brarriodigital.crews.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.brarriodigital.crews.dto.request.CreateCrewRequest;
import cl.brarriodigital.crews.dto.response.CrewResponse;
import cl.brarriodigital.crews.service.CrewService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/crews")
@RequiredArgsConstructor
public class CrewController {

    private final CrewService service;

    @GetMapping
    public List<CrewResponse> findAll() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CrewResponse create(@Valid @RequestBody CreateCrewRequest request) {
        return service.create(request);
    }
}
