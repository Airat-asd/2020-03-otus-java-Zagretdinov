package ru.otus.json.myJson;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Ayrat Zagretdinov
 * created on 01.07.2020
 */
public class CollectionTypeInJson {

    public static boolean isCollectionType(Object obj) {
        return obj instanceof Collection;
    }

    public static String CollectionTypeInJson(Object obj) throws IllegalAccessException {
        String json = "[";
        Iterator iterator = ((Collection) obj).iterator();
        while (iterator.hasNext()) {
            var element = iterator.next();
            json = json + ObjectToJson.objectToJson(element);
            if (iterator.hasNext()) {
                json = json + ",";
            }
        }
        json = json + "]";
        return json;
    }
}
