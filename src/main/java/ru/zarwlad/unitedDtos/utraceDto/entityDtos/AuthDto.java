package ru.zarwlad.unitedDtos.utraceDto.entityDtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.zarwlad.utrace.model.Auth;
import ru.zarwlad.unitedDtos.utraceDto.Dto;

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
        AuthDto authDto = (AuthDto) this;
        return new Auth(accessToken);
    }
}
