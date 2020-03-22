package mdlp.dto.messages.ticketDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;

public class ResultDTO {
    String operation;

    @JacksonXmlProperty(localName = "operation_id")
    String operationId;

    @JacksonXmlProperty(localName = "operation_result")
    String operationResult;

    @JacksonXmlProperty(localName = "operation_comment")
    String operationComment;

    @JacksonXmlElementWrapper(useWrapping = false)
    ArrayList<ErrorDTO> errors;

    public ResultDTO() {
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public String getOperationComment() {
        return operationComment;
    }

    public void setOperationComment(String operationComment) {
        this.operationComment = operationComment;
    }

    public ArrayList<ErrorDTO> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<ErrorDTO> errors) {
        this.errors = errors;
    }
}
