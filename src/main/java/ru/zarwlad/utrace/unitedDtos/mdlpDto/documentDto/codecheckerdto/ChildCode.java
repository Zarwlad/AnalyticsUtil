package ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.codecheckerdto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ChildCode {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "sscc_info")
    private ArrayList<SsccInfo> ssccList;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "sgtin_info")
    private ArrayList<SgtinInfo> sgtinList;
}
