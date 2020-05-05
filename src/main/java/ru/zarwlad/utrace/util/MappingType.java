package ru.zarwlad.utrace.util;

public class MappingType {
    public static String getAuditRecordTypeFromEventType(String eventType){
        String[] strings = eventType.split("_");

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            stringBuilder.append(string.toLowerCase());

            if (i != (strings.length-1)){
                stringBuilder.append("-");
            }
        }

        return stringBuilder.toString();
    }
}
