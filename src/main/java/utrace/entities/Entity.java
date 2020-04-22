package utrace.entities;

import utrace.dto.Dto;

public interface Entity {
    Dto fromEntityToDto(Object object);
}
