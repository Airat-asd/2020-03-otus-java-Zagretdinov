package ru.otus.json.myJson;

import java.lang.reflect.Array;

/**
 * @author Ayrat Zagretdinov
 * created on 01.07.2020
 */
public class ArrayTypeInJson {

    public static boolean isArrayType(Object obj) {
        return obj.getClass().isArray();
    }

    public static String arrayTypeInJson(Object obj) throws IllegalAccessException {
        String json = "[";
        int arrayLength = Array.getLength(obj);
        for (int i = 0; i < arrayLength; i++) {
            if (Array.get(obj, i) == null) {
                json = json + "null";
            } else {
                json = json + DefiningTypeVariable.definingTypeVariable(Array.get(obj, i));
            }
            if (i < arrayLength - 1) {
                json = json + ",";
            }
        }
        json = json + "]";
        return json;
    }
}
