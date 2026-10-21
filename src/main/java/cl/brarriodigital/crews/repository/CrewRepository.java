package cl.brarriodigital.crews.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.brarriodigital.crews.model.Crew;

public interface CrewRepository extends JpaRepository<Crew, UUID> {

    List<Crew> findAllByOrderByNameAscIdAsc();
}
