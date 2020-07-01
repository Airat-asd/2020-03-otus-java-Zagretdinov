package ru.otus.json.myJson;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Ayrat Zagretdinov
 * created on 01.07.2020
 */
public class CollectionTypeInJson {

    public static boolean isCollectionType(Object obj) {
        if (obj instanceof Collection) {
            return true;
        }
        return false;
    }

    public static String CollectionTypeInJson(Object obj) throws IllegalAccessException {
        String json = "[";
        Iterator iterator = ((Collection) obj).iterator();
        while (iterator.hasNext()) {
            var element = iterator.next();
            if (PrimitiveTypeInJson.isPrimitiveType(element)) {
                json = json + PrimitiveTypeInJson.primitiveTypeInJson(element);
            } else if (StringTypeInJson.isStringType(element)) {
                json = json + StringTypeInJson.stringTypeInJson(element);
            } else if (ArrayTypeInJson.isArrayType(element)) {
                json = json + ArrayTypeInJson.ArrayTypeInJson(element);
            } else if (element instanceof Collection) {
                json = json + CollectionTypeInJson.CollectionTypeInJson(element);
            } else {
                json = json + ReferenceTypeInJson.referenceTypeWithTheFieldInJson(element);
            }
            if (iterator.hasNext()) {
                json = json + ",";
            }
        }
        json = json + "]";
        return json;
    }
}
