package ru.zarwlad.mdlp.downloadMdlpPartners.dto.rznDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.util.List;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect
@JacksonXmlRootElement(localName = "licenses_list")
public class LicencesListDto {

    @JacksonXmlProperty(localName = "licenses")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<PharmLicenceDto> pharmLicenceDtoList;
}
