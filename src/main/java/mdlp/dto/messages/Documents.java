package mdlp.dto.messages;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import mdlp.dto.messages.multiPackDto.MultiPackDTO;
import mdlp.dto.messages.ticketDto.ResultDTO;
import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class Documents {
    @JacksonXmlProperty(localName = "multi_pack")
    MultiPackDTO multiPackDTO;

    @JacksonXmlProperty(localName = "result")
    ResultDTO resultDTO;

    public MultiPackDTO getMultiPackDTO() {
        return multiPackDTO;
    }

    public void setMultiPackDTO(MultiPackDTO multiPackDTO) {
        this.multiPackDTO = multiPackDTO;
    }

    public ResultDTO getResultDTO() {
        return resultDTO;
    }

    public void setResultDTO(ResultDTO resultDTO) {
        this.resultDTO = resultDTO;
    }

    public Documents() {
    }
}
