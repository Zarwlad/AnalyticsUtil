package ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.codecheckerdto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SsccInfo {
    private String sscc;

    @JacksonXmlProperty(localName = "packing_date")
    private String packingDate;

    //@JacksonXmlElementWrapper(localName = "childs")
    private ChildCode childs;
}
