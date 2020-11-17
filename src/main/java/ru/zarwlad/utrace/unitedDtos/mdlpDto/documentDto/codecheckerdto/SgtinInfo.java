package ru.zarwlad.utrace.unitedDtos.mdlpDto.documentDto.codecheckerdto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SgtinInfo {
    private String sgtin;

    private String sscc;

    private String status;

    private String gtin;

    @JacksonXmlProperty(localName = "series_number")
    private String seriesNumber;

    @JacksonXmlProperty(localName = "expiration_date")
    private String expirationDate;

}
