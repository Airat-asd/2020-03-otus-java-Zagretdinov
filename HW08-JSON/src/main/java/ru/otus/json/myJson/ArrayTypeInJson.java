package ru.otus.json.myJson;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * @author Ayrat Zagretdinov
 * created on 01.07.2020
 */
public class ArrayTypeInJson {

    public static boolean isArrayType(Object obj) {
        if (obj.getClass().isArray()) {
            return true;
        }
        return false;
    }

    public static String ArrayTypeInJson(Object obj) throws IllegalAccessException {
        String json = "[";
        int arrayLength = Array.getLength(obj);
        for (int i = 0; i < arrayLength; i++) {
            if (Array.get(obj, i) != null) {
                if (PrimitiveTypeInJson.isPrimitiveType(Array.get(obj, i))) {
                    json = json + PrimitiveTypeInJson.primitiveTypeInJson(Array.get(obj, i));
                } else if (StringTypeInJson.isStringType(Array.get(obj, i))) {
                    json = json + StringTypeInJson.stringTypeInJson(Array.get(obj, i));
                } else if (ArrayTypeInJson.isArrayType(Array.get(obj, i))) {
                    json = json + ArrayTypeInJson.ArrayTypeInJson(Array.get(obj, i));
                } else if (Array.get(obj, i) instanceof Collection) {
                    json = json + CollectionTypeInJson.CollectionTypeInJson(Array.get(obj, i));
                } else {
                    json = json + ReferenceTypeInJson.referenceTypeWithTheFieldInJson(Array.get(obj, i));
                }
                if (i < arrayLength - 1) {
                    json = json + ",";
                }
            }
        }
        json = json + "]";
        return json;
    }
}
