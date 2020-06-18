package ru.zarwlad.mdlp.multiPackParser.dto.messages.multiPackDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JacksonXmlRootElement
public class DetailDTO {
    String sscc;

    @JacksonXmlElementWrapper(localName = "content")
    ArrayList<String> sgtin;
}
