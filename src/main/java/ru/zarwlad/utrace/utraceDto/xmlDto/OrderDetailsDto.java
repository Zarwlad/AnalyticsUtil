package ru.zarwlad.utrace.utraceDto.xmlDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@JacksonXmlRootElement(localName = "order_details")
public class OrderDetailsDto {
    @JacksonXmlElementWrapper(useWrapping = false)
    List<String> sgtin;

    @JacksonXmlElementWrapper(useWrapping = false)
    List<String> sscc;
}
