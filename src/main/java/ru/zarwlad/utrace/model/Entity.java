package ru.zarwlad.utrace.model;

import ru.zarwlad.unitedDtos.utraceDto.Dto;

public interface Entity {
    Dto fromEntityToDto(Object object);
}
