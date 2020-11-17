package ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.codecheckerdto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;
import ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.multiPackDto.DetailDTO;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JacksonXmlRootElement
public class HierarchyInfoDto {
    @JacksonXmlProperty(localName = "sscc_up")
    private SsccUp ssccUp;

    @JacksonXmlProperty(localName = "sscc_down")
    private SsccDown ssccDown;

}
