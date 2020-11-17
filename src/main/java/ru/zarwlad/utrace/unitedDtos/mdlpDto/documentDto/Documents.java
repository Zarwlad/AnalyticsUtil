package ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;
import ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.codecheckerdto.HierarchyInfoDto;
import ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.multiPackDto.MultiPackDTO;
import ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.ticketDto.ResultDTO;

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

    @JacksonXmlProperty(localName = "hierarchy_info")
    HierarchyInfoDto hierarchyInfoDto;
}
