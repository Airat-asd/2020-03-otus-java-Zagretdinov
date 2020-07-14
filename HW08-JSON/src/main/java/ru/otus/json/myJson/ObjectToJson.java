package ru.otus.json.myJson;

import java.util.Collection;

/**
 * @author Ayrat Zagretdinov
 * created on 02.07.2020
 */
public class ObjectToJson {

    public static String objectToJson(Object obj) throws IllegalAccessException {
        return primitiveType(obj) + stringType(obj) + arrayType(obj) + collectionType(obj) + referenceType(obj);
    }

    private static String primitiveType(Object obj) {
        if (PrimitiveTypeInJson.isPrimitiveType(obj)) {
            return PrimitiveTypeInJson.primitiveTypeInJson(obj);
        }
        return "";
    }

    private static String stringType(Object obj) {
        if (StringTypeInJson.isStringType(obj)) {
            return StringTypeInJson.stringTypeInJson(obj);
        }
        return "";
    }

    private static String arrayType(Object obj) throws IllegalAccessException {
        if (ArrayTypeInJson.isArrayType(obj)) {
            return ArrayTypeInJson.arrayTypeInJson(obj);
        }
        return "";
    }

    private static String collectionType(Object obj) throws IllegalAccessException {
        if (obj instanceof Collection) {
            return CollectionTypeInJson.CollectionTypeInJson(obj);
        }
        return "";
    }

    private static String referenceType(Object obj) throws IllegalAccessException {
        if (ReferenceTypeInJson.isReferenceType(obj)) {
            return ReferenceTypeInJson.referenceTypeInJson(obj);
        }
        return "";
    }
}
