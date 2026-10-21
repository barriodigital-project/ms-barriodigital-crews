package cl.brarriodigital.crews.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.brarriodigital.crews.dto.request.CreateCrewRequest;
import cl.brarriodigital.crews.dto.response.CrewResponse;
import cl.brarriodigital.crews.model.Crew;
import cl.brarriodigital.crews.repository.CrewRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrewService {

    private final CrewRepository repository;

    @Transactional(readOnly = true)
    public List<CrewResponse> findAll() {
        return repository.findAllByOrderByNameAscIdAsc().stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional
    public CrewResponse create(CreateCrewRequest request) {
        var crew = new Crew();
        crew.setName(request.name().trim());
        crew.setAvailable(request.available() == null || request.available());
        return toResponse(repository.save(crew));
    }

    private CrewResponse toResponse(Crew crew) {
        return new CrewResponse(crew.getId(), crew.getName(), crew.isAvailable());
    }
}
