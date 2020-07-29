package ru.zarwlad.mdlp.downloadMdlpPartners.model;

public enum EntityType {
    RESIDENT("1"),
    NON_RES_AGENCY("2"),
    NON_RESIDENT("3");

    private String type;

    EntityType(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return "EntityType{" +
                "type=" + type +
                '}';
    }
}
