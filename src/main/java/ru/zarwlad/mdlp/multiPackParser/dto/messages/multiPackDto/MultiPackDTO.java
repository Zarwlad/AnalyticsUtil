package ru.zarwlad.mdlp.multiPackParser.dto.messages.multiPackDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
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
public class MultiPackDTO {
    @JacksonXmlProperty(localName = "subject_id")
    String subjectId;

    @JacksonXmlProperty(localName = "operation_date")
    String operationDate;

    @JacksonXmlElementWrapper(localName = "by_sgtin")
    ArrayList<DetailDTO> detail;

}
