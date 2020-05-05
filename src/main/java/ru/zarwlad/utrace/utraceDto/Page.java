package ru.zarwlad.utrace.utraceDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Page {
    Integer number;
    Integer size;
    Integer totalElements;
    Integer totalPages;

    public Page() {
    }

    public Page(Integer number, Integer size, Integer totalElements, Integer totalPages) {
        this.number = number;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
