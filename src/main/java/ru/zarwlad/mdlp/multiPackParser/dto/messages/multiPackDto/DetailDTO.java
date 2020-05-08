package ru.zarwlad.mdlp.multiPackParser.dto.messages.multiPackDto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.ArrayList;

public class DetailDTO {
    String sscc;
    @JacksonXmlElementWrapper(localName = "content")
    ArrayList<String> sgtin;

    public String getSscc() {
        return sscc;
    }

    public void setSscc(String sscc) {
        this.sscc = sscc;
    }

    public ArrayList<String> getSgtin() {
        return sgtin;
    }

    public void setSgtin(ArrayList<String> sgtin) {
        this.sgtin = sgtin;
    }

    public DetailDTO() {
    }
}
