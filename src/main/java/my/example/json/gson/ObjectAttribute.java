/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
