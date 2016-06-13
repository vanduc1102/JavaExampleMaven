package my.example.java.json;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import my.example.java.json.adapter.JsonCalendarTypeAdapter;
import my.example.java.json.adapter.JsonDateTypeAdapter;
import my.example.java.json.adapter.JsonDoubleTypeAdapter;
import my.example.java.json.adapter.JsonFloatTypeAdapter;
import my.example.java.json.adapter.JsonIntegerTypeAdapter;


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
        Gson gsonAdapted = buildGsonWithAdapters(adapters);
        return gsonAdapted.fromJson(json, clazz);
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
     * Builds Gson object based on the given adapters
     *
     * @param clazz
     * @param adapter
     * @return Gson object
     */
    @SuppressWarnings("unchecked")
    public static Gson buildGsonWithAdapter(Class clazz, Object adapter) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(clazz, adapter);
        return builder.create();
    }

    /**
     * Method create GSON object with date format
     * @return
     */
    public static Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat(DATE_FORMAT_ISO_8601);
        
        builder.registerTypeAdapter(Date.class, new JsonDateTypeAdapter());

        // Register an adapter for calendar types
        builder.registerTypeAdapter(Calendar.class, new JsonCalendarTypeAdapter());
        builder.registerTypeAdapter(GregorianCalendar.class, new JsonCalendarTypeAdapter());
        builder.registerTypeAdapter(Double.class, new JsonDoubleTypeAdapter());
        builder.registerTypeAdapter(Integer.class, new JsonIntegerTypeAdapter());
        builder.registerTypeAdapter(Float.class, new JsonFloatTypeAdapter());

        return builder.create();
    }
}
