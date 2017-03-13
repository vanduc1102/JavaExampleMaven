package my.example.json.gson;

import com.google.gson.JsonObject;

/**
 *
 * @author nvduc
 */
public class ObjectAttribute {
    public static void main(String[] args) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("editable", Boolean.TRUE);
        jsonObject.addProperty("isLocked", Boolean.FALSE);
        System.out.println("my.example.json.gson.ObjectAttribute.main()" + jsonObject.toString());
    }
}
