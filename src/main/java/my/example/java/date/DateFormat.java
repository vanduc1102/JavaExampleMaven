package my.example.java.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author nvduc
 */
public class DateFormat {
    private static final String[] DESERIALIZE_DATE_FORMAT = new String[]{
        "yyyy-MM-dd'T'HH:mm:ssX",//ISO8601 long
        "yyyy-MM-dd'T'HH:mm:ssZ",
        "yyyy-MM-dd'T'HH:mm:ss.SSSX",//ISO8601 timezone
        "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
        "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
        "dd.MM.yyyy",
        "yyyy-MM-dd"
        //Add more at bottom
    };
    public static void main(String args[]) throws ParseException{
        SimpleDateFormat dateFormat1 = new SimpleDateFormat(DESERIALIZE_DATE_FORMAT[4]);
        SimpleDateFormat dateFormat2 = new SimpleDateFormat(DESERIALIZE_DATE_FORMAT[6]);
        String dateStr ="2015-09-07T00:00:00.000+07:00";
        Date date =dateFormat1.parse(dateStr);
        System.out.println("Date to String : "+dateFormat1.format(new Date()));
        Date date1 =dateFormat1.parse(dateStr);
        System.out.println("Date : "+date1);
        
    }
}
