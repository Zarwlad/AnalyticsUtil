package utrace.entities;

import utrace.dto.Dto;

public interface Entity {
    //Object fromDtoToEntity(Dto dto);
    Dto fromEntityToDto(Object object);
}
