package cl.brarriodigital.crews.model;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "crew_assignments",
    indexes = {
        @Index(name = "idx_crew_assignments_request", columnList = "request_id"),
        @Index(name = "idx_crew_assignments_crew", columnList = "crew_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
public class CrewAssignment {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "request_id", nullable = false)
    private UUID requestId;

    @Column(name = "crew_id", nullable = false)
    private UUID crewId;

    @Column(name = "scheduled_at")
    private Instant scheduledAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private CrewAssignmentStatus status = CrewAssignmentStatus.ASIGNADA;
}
