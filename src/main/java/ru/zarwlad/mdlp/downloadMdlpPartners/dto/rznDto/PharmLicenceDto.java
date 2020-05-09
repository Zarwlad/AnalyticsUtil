package ru.zarwlad.mdlp.downloadMdlpPartners.dto.rznDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.List;

@ToString
@EqualsAndHashCode(of = "number")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PharmLicenceDto {

    @JacksonXmlProperty(localName = "inn")
    private String inn;

    @JacksonXmlProperty(localName = "ogrn")
    private String ogrn;

    @JacksonXmlProperty(localName = "number")
    private String number;

    @JacksonXmlProperty(localName = "date")
    private String date;

    @JacksonXmlProperty(localName = "termination")
    private String termination;

    @JacksonXmlProperty(localName = "activity_type")
    private String activityType;

    @JacksonXmlElementWrapper(localName = "work_address_list")
    private List<RznAddressDto> addressList;

}
