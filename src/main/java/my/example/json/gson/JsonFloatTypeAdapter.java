package my.example.java.json.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonFloatTypeAdapter implements JsonDeserializer<Float>, JsonSerializer<Float> {

    @Override
    public Float deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Float output;
        try {
            output = json.getAsFloat();
        } catch (NumberFormatException e) {
            output = null;
        }
        return output;
    }

    @Override
    public JsonElement serialize(Float src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
