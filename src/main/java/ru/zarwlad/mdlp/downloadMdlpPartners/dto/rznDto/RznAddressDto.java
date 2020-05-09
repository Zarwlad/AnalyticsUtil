package ru.zarwlad.mdlp.downloadMdlpPartners.dto.rznDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.List;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RznAddressDto {
    @JacksonXmlProperty(localName = "address")
    private String address;

    @JacksonXmlProperty(localName = "code_fias")
    private String fias;

    @JacksonXmlElementWrapper(localName = "works")
    private List<WorkDescriptionDto> works;

}
