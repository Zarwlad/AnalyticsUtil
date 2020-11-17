package ru.zarwlad.utrace.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.utrace.model.Auth;
import ru.zarwlad.utrace.unitedDtos.utraceDto.Dto;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AuthDto implements Dto {
    @JsonProperty(value = "accessToken")
    private String accessToken;

    public Object fromDtoToEntity() {
        AuthDto authDto = this;
        return new Auth(accessToken);
    }
}
