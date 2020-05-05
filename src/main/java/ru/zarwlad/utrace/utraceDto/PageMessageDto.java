package ru.zarwlad.utrace.utraceDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonAutoDetect
public class PageMessageDto {
    @JsonProperty(value = "data")
    List<MessageDto> messageDtos;

    @JsonProperty(value = "page")
    Page page;

    public PageMessageDto() {
    }

    public PageMessageDto(List<MessageDto> messageDtos, Page page) {
        this.messageDtos = messageDtos;
        this.page = page;
    }

    public List<MessageDto> getMessageDtos() {
        return messageDtos;
    }

    public void setMessageDtos(List<MessageDto> messageDtos) {
        this.messageDtos = messageDtos;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}