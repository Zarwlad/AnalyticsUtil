package ru.zarwlad.unitedDtos.utraceDto.pagedDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.EventDto;

import java.util.List;

@JsonAutoDetect
public class PageDtoOfBriefedBusinessEventDto {
    List<EventDto> data;
    Page page;

    public PageDtoOfBriefedBusinessEventDto() {
    }

    public PageDtoOfBriefedBusinessEventDto(List<EventDto> data, Page page) {
        this.data = data;
        this.page = page;
    }

    public List<EventDto> getData() {
        return data;
    }

    public void setData(List<EventDto> data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
