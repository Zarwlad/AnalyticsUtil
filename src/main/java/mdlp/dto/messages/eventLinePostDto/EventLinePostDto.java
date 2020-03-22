package mdlp.dto.messages.eventLinePostDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonAutoDetect
public class EventLinePostDto {
    String code;

    @JsonProperty(value = "isSscc")
    boolean sscc;

    Object parent;

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public EventLinePostDto() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSscc() {
        return sscc;
    }

    public void setSscc(boolean sscc) {
        this.sscc = sscc;
    }
}
