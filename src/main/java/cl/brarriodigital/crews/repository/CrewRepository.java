package cl.brarriodigital.crews.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.brarriodigital.crews.model.Crew;

public interface CrewRepository extends JpaRepository<Crew, UUID> {

    List<Crew> findAllByOrderByNameAscIdAsc();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select crew from Crew crew where crew.id = :id")
    Optional<Crew> findByIdForUpdate(@Param("id") UUID id);
}
