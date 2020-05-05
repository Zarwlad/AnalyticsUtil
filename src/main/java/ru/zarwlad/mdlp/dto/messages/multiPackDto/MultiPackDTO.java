package ru.zarwlad.mdlp.dto.messages.multiPackDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;

public class MultiPackDTO {
    @JacksonXmlProperty(localName = "subject_id")
    String subjectId;

    @JacksonXmlProperty(localName = "operation_date")
    String operationDate;

    @JacksonXmlElementWrapper(localName = "by_sgtin")
    ArrayList<DetailDTO> detail;

    public MultiPackDTO() {
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public ArrayList<DetailDTO> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<DetailDTO> detailDTO) {
        this.detail = detailDTO;
    }
}
