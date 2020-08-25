package ru.zarwlad.utrace.unitedDtos.utraceDto.pagedDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos.EventMessageDto;

import java.util.List;

@JsonAutoDetect
public class PageDtoOfBusinessEventMessageDto {
    @JsonProperty(value = "data")
    List<EventMessageDto> eventMessageDtos;
    @JsonProperty(value = "page")
    Page page;

    public PageDtoOfBusinessEventMessageDto() {
    }

    public PageDtoOfBusinessEventMessageDto(List<EventMessageDto> eventMessageDtos, Page page) {
        this.eventMessageDtos = eventMessageDtos;
        this.page = page;
    }

    public List<EventMessageDto> getEventMessageDtos() {
        return eventMessageDtos;
    }

    public void setEventMessageDtos(List<EventMessageDto> eventMessageDtos) {
        this.eventMessageDtos = eventMessageDtos;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
