package my.example.java.json.adapter;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.text.ParseException;
import java.util.Arrays;

public class JsonDateTypeAdapter implements JsonDeserializer<Date>, JsonSerializer<Date> {
    // Example: 2016-06-03T15:55:36+0700
    // This is not really ISO8601, use it for backward compatibility
    private static final String SELIALIZE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    private static final String[] DESERIALIZE_DATE_FORMAT = new String[]{
        "yyyy-MM-dd'T'HH:mm:ssX",//ISO8601 long
        "yyyy-MM-dd'T'HH:mm:ss.SSSX",//ISO8601 timezone
        "dd.MM.yyyy",
        "yyyy-MM-dd"
        //Add more at bottom
    };

    public JsonDateTypeAdapter() {
    }

    /**
     * Register an adapter to manage the date types that are received as long
     * values on json string.First the method try to deserialize the json value
     * with ISO8601 long. If the json value is unparsable, the method again
     * parse json value with ISO8601 timezone. it makes the method compliant
     * with both xcomponent 1.0 and 1.1.
     * @param jsonElement
     * @param context
     */
    @Override
    public Date deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        for (String format : DESERIALIZE_DATE_FORMAT) {
            try {
                return new SimpleDateFormat(format).parse(jsonElement.getAsString());
            } catch (ParseException e) {
                
            }
        }
        throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
                + "\". Supported formats: " + Arrays.toString(DESERIALIZE_DATE_FORMAT));
    }

    /**
     * Register an adapter to manage the date types and serialize date as ISO
     * 8601 when creating a json string. Always serialize the date with ISO long
     * pattern.
     * @param date
     * @param typeOfT
     * @param context
     */
    @Override
    public JsonElement serialize(Date date, Type typeOfT, JsonSerializationContext context) {
        //Default format is ISO8601 long
        SimpleDateFormat sdf = new SimpleDateFormat(SELIALIZE_DATE_FORMAT);
        return new JsonPrimitive(sdf.format(date));
    }
}
