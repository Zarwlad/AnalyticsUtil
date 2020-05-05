package ru.zarwlad.mdlp.dto.messages.ticketDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ErrorDTO {
    @JacksonXmlProperty(localName = "error_code")
    String errorCode;

    @JacksonXmlProperty(localName = "error_desc")
    String errorDesc;

    @JacksonXmlProperty(localName = "object_id")
    String objectId;

    public ErrorDTO() {
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
