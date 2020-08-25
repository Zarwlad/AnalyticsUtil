package ru.zarwlad.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class EventStateDto {
    private String id;
    private String code;
    private String parentCode;
}
