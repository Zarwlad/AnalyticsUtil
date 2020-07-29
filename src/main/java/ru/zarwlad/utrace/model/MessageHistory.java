package ru.zarwlad.utrace.model;

import lombok.*;
import ru.zarwlad.unitedDtos.utraceDto.Dto;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "message_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class MessageHistory {
    @Id
    private String id;

    @Column(name = "authored_by")
    private String authoredBy;

    @Column(name = "created_date")
    private ZonedDateTime created;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    private Message message;

}
