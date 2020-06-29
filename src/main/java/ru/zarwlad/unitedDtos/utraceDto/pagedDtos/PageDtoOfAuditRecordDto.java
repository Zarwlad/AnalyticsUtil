package ru.zarwlad.unitedDtos.utraceDto.pagedDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.AuditRecordDto;

import java.util.List;

@JsonAutoDetect
public class PageDtoOfAuditRecordDto {
    List<AuditRecordDto> data;
    Page page;

    public PageDtoOfAuditRecordDto() {
    }

    public List<AuditRecordDto> getData() {
        return data;
    }

    public void setData(List<AuditRecordDto> data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public PageDtoOfAuditRecordDto(List<AuditRecordDto> data, Page page) {
        this.data = data;
        this.page = page;
    }
}
