package my.example.json.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonDoubleTypeAdapter implements JsonDeserializer<Double>, JsonSerializer<Double> {

  @Override
  public Double deserialize(
      JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    Double output;
    try {
      output = json.getAsDouble();
    } catch (NumberFormatException e) {
      output = null;
    }
    return output;
  }

  @Override
  public JsonElement serialize(
      Double src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.toString());
  }
}
