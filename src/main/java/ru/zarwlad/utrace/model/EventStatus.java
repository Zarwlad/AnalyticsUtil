package ru.zarwlad.utrace.model;

import lombok.*;
import ru.zarwlad.unitedDtos.utraceDto.Dto;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "event_status")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class EventStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "status")
    private String status;

    @Column(name = "change_operation_date")
    private ZonedDateTime changeOperationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private Event event;
}
