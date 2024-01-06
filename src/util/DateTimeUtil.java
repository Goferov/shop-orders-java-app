package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    private static String DEFAULT_FORMAT = "dd-MM-yyyy";

    public static String showDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
        return formatter.format(date);
    }
}
