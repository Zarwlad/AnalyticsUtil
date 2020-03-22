package utrace.entities;

import utrace.dto.Dto;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Month;
import java.time.ZonedDateTime;


public class EventStatistic implements Entity {
    private Event event;
    private BigDecimal eventPostingSeconds;
    private BigDecimal messagesSendSeconds;
    private Boolean isErrorEvent;
    private MessageErrorEnum isErrorMessage;
    private Boolean isEventPosted;
    private Boolean isMessageCreated;
    private Month eventMonth;

    public EventStatistic() {
    }

    public EventStatistic(Event event,
                          BigDecimal eventPostingSeconds,
                          BigDecimal messagesSendSeconds,
                          Boolean isErrorEvent,
                          MessageErrorEnum isErrorMessage,
                          Boolean isEventPosted,
                          Boolean isMessageCreated,
                          Month eventMonth) {
        this.event = event;
        this.eventPostingSeconds = eventPostingSeconds;
        this.messagesSendSeconds = messagesSendSeconds;
        this.isErrorEvent = isErrorEvent;
        this.isErrorMessage = isErrorMessage;
        this.isEventPosted = isEventPosted;
        this.isMessageCreated = isMessageCreated;
        this.eventMonth = eventMonth;
    }

    public Boolean getEventPosted() {
        return isEventPosted;
    }

    public void setEventPosted(Boolean eventPosted) {
        isEventPosted = eventPosted;
    }

    public Boolean getMessageCreated() {
        return isMessageCreated;
    }

    public void setMessageCreated(Boolean messageCreated) {
        isMessageCreated = messageCreated;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public BigDecimal getEventPostingSeconds() {
        return eventPostingSeconds;
    }

    public void setEventPostingSeconds(BigDecimal eventPostingSeconds) {
        this.eventPostingSeconds = eventPostingSeconds;
    }

    public BigDecimal getMessagesSendSeconds() {
        return messagesSendSeconds;
    }

    public void setMessagesSendSeconds(BigDecimal messagesSendSeconds) {
        this.messagesSendSeconds = messagesSendSeconds;
    }

    public Boolean getErrorEvent() {
        return isErrorEvent;
    }

    public void setErrorEvent(Boolean errorEvent) {
        isErrorEvent = errorEvent;
    }

    public MessageErrorEnum getErrorMessage() {
        return isErrorMessage;
    }

    public void setErrorMessage(MessageErrorEnum errorMessage) {
        isErrorMessage = errorMessage;
    }

    public Month getEventMonth() {
        return eventMonth;
    }

    public void setEventMonth(Month eventMonth) {
        this.eventMonth = eventMonth;
    }

    @Override
    public Dto fromEntityToDto(Object object) {
        return null;
    }

}
