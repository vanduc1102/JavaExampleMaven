package my.example.json.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class JsonUtils {

    private static final String DATE_FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss";
    private static final Gson gson = new Gson();
    private JsonUtils() {
    }

    /**
     * Converts an object to JSON stream
     *
     * @param object Object to convert
     * @return JSON stream
     */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * Converts the given JSON stream to a JSON Object. This method is useful
     * when we want to explore on the content of the JSON stream.
     *
     * @param json JSON stream
     * @throws JsonSyntaxException
     * @return JSON Object. NULL if the given JSON stream is not a JSON Object
     */
    public static JsonObject toJsonObject(String json) throws JsonSyntaxException {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);
        return element.isJsonObject() ? element.getAsJsonObject() : null;
    }

    /**
     * Restores an object of the given class based on the given JSON stream.
     *
     * @param <T>
     * @param json JSON stream
     * @param clazz Class of JSON stream
     * @return Object of the class
     * @throws JsonSyntaxException
     */
    public static <T> T fromJson(String json, Class<T> clazz) throws JsonSyntaxException {
        return gson.fromJson(json, clazz);
    }

    /**
     * Restores an object of the given class based on the given JSON stream with
     * a list of adapters.
     *
     * @param <T>
     * @param json JSON stream
     * @param clazz Class of JSON stream
     * @param adapters A list of adapters (key: the class, value: adapter)
     * @return Object of the class
     * @throws JsonSyntaxException
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Class<T> clazz, Map<Class, Object> adapters)
            throws JsonSyntaxException {
        Gson gsonAdpated = buildGsonWithAdapters(adapters);
        return gsonAdpated.fromJson(json, clazz);
    }

    /**
     * Builds Gson object based on the given adapters
     *
     * @param adapters A map of adapters (key: the class, value: adapter)
     * @return Gson object
     */
    @SuppressWarnings("unchecked")
    public static Gson buildGsonWithAdapters(Map<Class, Object> adapters) {
        GsonBuilder builder = new GsonBuilder();
        if (adapters != null) {
            for (Class clazz : adapters.keySet()) {
                builder.registerTypeAdapter(clazz, adapters.get(clazz));
            }
        }
        return builder.create();
    }
    /**
     * Method create GSON object with date format <b>yyyy-MM-dd'T'HH:mm:ss</b>.<br />
     * There are three adapter types created: Date, Calendar, GregorianCalendar.
     * @return 
     */
    public static Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat(DATE_FORMAT_ISO_8601);

        // Register an adapter to manage the date types as long values
        // For example : "timeStamp":1391158848000         
        builder.registerTypeAdapter(Date.class, new JsonCalendarTypeAdapter());

        // Register an adapter for calendar types
        builder.registerTypeAdapter(Calendar.class, new JsonCalendarTypeAdapter());
        builder.registerTypeAdapter(GregorianCalendar.class, new JsonCalendarTypeAdapter());

        return builder.create();
    }
}
