package ru.zarwlad.utrace.model;

import lombok.*;
import ru.zarwlad.unitedDtos.utraceDto.Dto;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "message_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
@ToString(exclude = "message")
public class MessageHistory {
    @Id
    private UUID id;

    @Column(name = "authored_by")
    private String authoredBy;

    @Column(name = "created_date")
    private ZonedDateTime created;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id")
    private Message message;

}
