package my.example.java.json.adapter;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

public class JsonCalendarTypeAdapter implements JsonDeserializer<Calendar>, JsonSerializer<Calendar> {
    
    // Example: 2016-06-03T15:55:36+0700
    // This is not really ISO8601, use it for backward compatibility
    private static final String SELIALIZE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    private static final String[] DESERIALIZE_DATE_FORMAT = new String[]{
        "yyyy-MM-dd'T'HH:mm:ssX",//ISO8601 long
        "yyyy-MM-dd'T'HH:mm:ss.SSSX",//ISO8601 timezone
        "dd.MM.yyyy"
        //Add more at bottom
    };

    public JsonCalendarTypeAdapter() {
    }

    /**
     * Register an adapter to manage the calendar types that are received as
     * long values on json string. First the method try to deserialize the json
     * value with ISO8601 long. If the json value is unparsable, the method
     * again parse json value with ISO8601 timezone. it makes the method
     * compliant with both xcomponent 1.0 and 1.1.
     * @param json
     * @param typeOfT
     * @param context
     * @return 
     */
    @Override
    public Calendar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Calendar cal = Calendar.getInstance();
        for (String format : DESERIALIZE_DATE_FORMAT) {
            try {
                Date date = new SimpleDateFormat(format).parse(json.getAsString());
                cal.setTime(date);
                return cal;
            } catch (ParseException e) {
                
            }
        }
        throw new JsonParseException("Unparseable date: \"" + json.getAsString()
                + "\". Supported formats: " + Arrays.toString(DESERIALIZE_DATE_FORMAT));
    }

    /**
     * Register an adapter to manage the calendar types and serialize calendar
     * as ISO 8601 when creating a json string. Always serialize the date with
     * ISO long pattern.
     * @param calendar
     * @param typeOfT
     * @param context
     * @return 
     */
    @Override
    public JsonElement serialize(Calendar calendar, Type typeOfT, JsonSerializationContext context) {
        SimpleDateFormat sdf = new SimpleDateFormat(SELIALIZE_DATE_FORMAT);
        return new JsonPrimitive(sdf.format(calendar.getTime()));
    }

}
