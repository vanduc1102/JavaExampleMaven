package my.example.java.json.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonIntegerTypeAdapter implements JsonDeserializer<Integer>, JsonSerializer<Integer> {

    @Override
    public Integer deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Integer output;
        try {
            output = json.getAsInt();
        } catch (NumberFormatException e) {
            output = null;
        }
        return output;
    }

    @Override
    public JsonElement serialize(Integer src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
