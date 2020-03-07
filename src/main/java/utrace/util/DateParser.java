package utrace.util;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class DateParser {
    public static ZonedDateTime parseStringToZonedDateTime(String datetime){
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(datetime);
        return zonedDateTime;
    }
}
