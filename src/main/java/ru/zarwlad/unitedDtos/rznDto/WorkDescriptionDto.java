package ru.zarwlad.unitedDtos.rznDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkDescriptionDto {
    @JacksonXmlProperty(localName = "work")
    private String work;
}
