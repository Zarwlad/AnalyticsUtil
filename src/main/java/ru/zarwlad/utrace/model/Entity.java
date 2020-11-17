package ru.zarwlad.utrace.model;

import ru.zarwlad.utrace.unitedDtos.utraceDto.Dto;

public interface Entity {
    Dto fromEntityToDto(Object object);
}
