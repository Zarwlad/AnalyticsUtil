package utrace.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonAutoDetect
public class PageMessageDto {
    @JsonProperty(value = "data")
    List<MessageDto> messageDtos;

    @JsonProperty(value = "page")
    Page page;
}
