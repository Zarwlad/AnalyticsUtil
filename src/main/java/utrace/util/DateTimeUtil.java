package utrace.util;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {

    public static ZonedDateTime toZonedDateTime(String dateTime){
        ZonedDateTime zonedDateTime = null;
        try {
            zonedDateTime = ZonedDateTime.parse(dateTime);
        }
        catch (DateTimeParseException e){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(dateTime);
            stringBuilder.append("Z");
            zonedDateTime = ZonedDateTime.parse(stringBuilder);
        }
        return zonedDateTime;
    }

}
