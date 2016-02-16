package my.example.json.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JsonCalendarTypeAdapter implements JsonDeserializer<Calendar>, JsonSerializer<Calendar> {

    //ISO8601 long
    private static final String DATE_FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ssZ";
    //ISO8601 timezone
    private static final String DATE_FORMAT_ISO_8601_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

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
        String jsonString = json.getAsJsonPrimitive().getAsString();
        SimpleDateFormat sdf;
        try {
            sdf = new SimpleDateFormat(DATE_FORMAT_ISO_8601);
            cal.setTime(sdf.parse(jsonString));
            return cal;
        } catch (ParseException e) {
            try {
                sdf = new SimpleDateFormat(DATE_FORMAT_ISO_8601_TIMEZONE);
                cal.setTime(sdf.parse(jsonString));
                return cal;
            } catch (ParseException e1) {
                throw new JsonParseException(e1);
            }
        }
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
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_ISO_8601);
        return new JsonPrimitive(sdf.format(calendar.getTime()));
    }
}
