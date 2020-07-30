package ru.zarwlad.utrace.model;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "mdlp_message")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class Message {
    @Id
    private UUID id;

    @Column(name = "status")
    private String status;

    @Column(name = "document_type_id")
    private String documentTypeId;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "operation_date")
    private ZonedDateTime operationDate;

    @OneToMany(mappedBy = "message_id")
    private Set<MessageHistory> messageHistories;

    @Column(name = "for_mdlp")
    private Boolean forMdlp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;
}
