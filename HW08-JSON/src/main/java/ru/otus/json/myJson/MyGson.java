package ru.otus.json.myJson;

/**
 * @author Ayrat Zagretdinov
 * created on 24.06.2020
 */
public class MyGson {
    public String toJson(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return null;
        }
        return ObjectToJson.objectToJson(obj);
    }
}