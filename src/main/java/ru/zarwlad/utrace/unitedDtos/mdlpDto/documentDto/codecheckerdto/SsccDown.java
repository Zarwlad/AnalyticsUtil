package ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.codecheckerdto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SsccDown {
    @JacksonXmlProperty(localName = "sscc_info")
    private SsccInfo ssccDown;
}
