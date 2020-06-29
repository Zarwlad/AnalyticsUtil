package ru.zarwlad.unitedDtos.mdlpDto.documentDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;
import ru.zarwlad.unitedDtos.mdlpDto.documentDto.multiPackDto.MultiPackDTO;
import ru.zarwlad.unitedDtos.mdlpDto.documentDto.ticketDto.ResultDTO;

@JsonAutoDetect
@JacksonXmlRootElement
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Documents {
    @JacksonXmlProperty(localName = "multi_pack")
    MultiPackDTO multiPackDTO;

    @JacksonXmlProperty(localName = "result")
    ResultDTO resultDTO;
}
