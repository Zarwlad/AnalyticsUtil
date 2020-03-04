package utrace.util;

public class MappingType {
    public static String getAuditRecordTypeFromEventType(String eventType){
//        switch (eventType){
//            case "MULTI_PACK":
//                return "multi-pack";
//            case "FOREIGN_SHIPMENT":
//                return "foreign-shipment";
//            case "MOVE_ORDER":
//                return "move-order";
//            case "MOVE_OWNER":
//                return "move-owner";
//            case "INCOME_ACCEPT":
//                return "income-accept";
//            case "REFUSAL_SENDER":
//                return "refusal-sender";
//        }

        String[] strings = eventType.split("_");

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            stringBuilder.append(string.toLowerCase());

            if (!(i == (strings.length-1))){
                stringBuilder.append("-");
            }
        }

        return stringBuilder.toString();
        //return null;
    }
}
