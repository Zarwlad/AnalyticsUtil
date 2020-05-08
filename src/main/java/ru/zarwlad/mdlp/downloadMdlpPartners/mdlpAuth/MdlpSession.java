package ru.zarwlad.mdlp.downloadMdlpPartners.mdlpAuth;

import java.time.LocalDateTime;
import java.time.Month;

class MdlpSession {
    private String mdlpToken;
    private LocalDateTime createdDate;
    private long tokenLifetime;

    public long getTokenLifetime() {
        return tokenLifetime;
    }

    public String getMdlpToken() {
        return mdlpToken;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    protected void setMdlpToken(String mdlpToken) {
        this.mdlpToken = mdlpToken;
    }

    protected void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    protected void setTokenLifetime(long tokenLifetime) {
        this.tokenLifetime = tokenLifetime;
    }

    protected MdlpSession() {
    }

}
