package ru.otus.json.myJson;

import java.lang.reflect.Field;

/**
 * @author Ayrat Zagretdinov
 * created on 24.06.2020
 */
public class MyGson {
    public String toJson(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return null;
        }
        if (PrimitiveTypeInJson.isPrimitiveType(obj)) {
            return PrimitiveTypeInJson.primitiveTypeInJson(obj);
        }
        if (StringTypeInJson.isStringType(obj)) {
            return StringTypeInJson.stringTypeInJson(obj);
        }
        if (ArrayTypeInJson.isArrayType(obj)) {
            return ArrayTypeInJson.ArrayTypeInJson(obj);
        }
        if (CollectionTypeInJson.isCollectionType(obj)) {
            return CollectionTypeInJson.CollectionTypeInJson(obj);
        }
        String json = "{";
        Field[] fieldsAll = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fieldsAll.length; i++) {
            fieldsAll[i].setAccessible(true);
            if (fieldsAll[i].get(obj) != null) {
                json = json + "\"" + fieldsAll[i].getName() + "\"" + ":";
                if (PrimitiveTypeInJson.isPrimitiveType(fieldsAll[i].get(obj))) {
                    json = json + PrimitiveTypeInJson.primitiveTypeInJson(fieldsAll[i].get(obj));
                } else if (StringTypeInJson.isStringType(fieldsAll[i].get(obj))) {
                    json = json + StringTypeInJson.stringTypeInJson(fieldsAll[i].get(obj));
                } else if (CollectionTypeInJson.isCollectionType(fieldsAll[i].get(obj))) {
                    json = json + CollectionTypeInJson.CollectionTypeInJson(fieldsAll[i].get(obj));
                } else if (ArrayTypeInJson.isArrayType(fieldsAll[i].get(obj))) {
                    json = json + ArrayTypeInJson.ArrayTypeInJson(fieldsAll[i].get(obj));
                } else {
                    json = json + ReferenceTypeInJson.referenceTypeWithTheFieldInJson(fieldsAll[i].get(obj));
                }
                if (i < fieldsAll.length - 1) {
                    json = json + ",";
                }
            }
        }
        json = json + "}";
        return json;
    }
}