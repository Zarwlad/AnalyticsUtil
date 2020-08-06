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
public class EventStat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "event_posting_ms")
    private BigDecimal eventPostingSeconds;

    @Column(name = "message_send_ms_avg")
    private BigDecimal messagesSendSecondsAvg;

    @Transient
    @Deprecated
    private Boolean isErrorEvent;

    @Transient
    @Deprecated
    private MessageErrorEnum isErrorMessage;

    @Transient
    @Deprecated
    private Boolean isEventPosted;

    @Transient
    @Deprecated
    private Boolean isMessageCreated;

    @Transient
    @Deprecated
    private Month eventMonth;

    @Column(name = "total_sending_ms")
    private BigDecimal totalSendingSeconds;

}
