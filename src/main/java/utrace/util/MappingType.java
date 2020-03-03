package utrace.util;

public class MappingType {
    public static String getAuditRecordTypeFromEventType(String eventType){
        switch (eventType){
            case "MULTI_PACK":
                return "multi-pack";
            case "FOREIGN_SHIPMENT":
                return "foreign-shipment";
            case "MOVE_ORDER":
                return "move-order";
            case "MOVE_OWNER":
                return "move-owner";
            case "INCOME_ACCEPT":
                return "income-accept";
        }

        return null;
    }
}
