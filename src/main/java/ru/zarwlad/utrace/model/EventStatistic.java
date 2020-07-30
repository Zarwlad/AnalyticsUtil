package ru.zarwlad.utrace.model;

import lombok.*;
import ru.zarwlad.unitedDtos.utraceDto.Dto;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.Month;
import java.util.UUID;

@Entity
@Table(name = "event_stat")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class EventStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "event_posting_secs")
    private BigDecimal eventPostingSeconds;

    @Column(name = "message_send_secs_avg")
    private BigDecimal messagesSendSecondsAvg;

    @Column(name = "is_error_event")
    private Boolean isErrorEvent;

    @Column(name = "is_error_message")
    private MessageErrorEnum isErrorMessage;

    @Column(name = "is_event_posted")
    private Boolean isEventPosted;

    @Column(name = "is_message_created")
    private Boolean isMessageCreated;

    @Column(name = "event_month")
    @Enumerated(value = EnumType.STRING)
    private Month eventMonth;

    @Column(name = "total_sending_secs")
    private BigDecimal totalSendingSeconds;

}
