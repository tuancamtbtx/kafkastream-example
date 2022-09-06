package vn.dataplatform.transformer.common.utils;

import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@UtilityClass
public class XTimeUtils {

    /**
     * Transform Calendar to ISO 8601 string.
     */
    public static String fromCalendar(final Calendar calendar) {
        Date date = calendar.getTime();
        String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .format(date);
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    /**
     * Transform ISO 8601 string to Calendar.
     */
    public static Calendar toCalendar(String iso8601string)
            throws ParseException {
        Calendar calendar = GregorianCalendar.getInstance();
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(iso8601string);
        calendar.setTime(date);
        return calendar;
    }

    public static void main(String[] args) throws ParseException {
        String time = "2021-07-12 09:00:00";
        long time1 = toCalendar(time).getTimeInMillis();
        System.out.println(time1);
    }
}
