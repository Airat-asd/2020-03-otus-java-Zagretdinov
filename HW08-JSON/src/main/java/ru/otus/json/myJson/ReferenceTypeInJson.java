package ru.otus.json.myJson;

import java.lang.reflect.Field;

/**
 * @author Ayrat Zagretdinov
 * created on 30.06.2020
 */
public class ReferenceTypeInJson {

    public static String referenceTypeWithTheFieldInJson(Object obj) throws IllegalAccessException {
        String json = "{";
        Field[] fieldsAll = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fieldsAll.length; i++) {
            fieldsAll[i].setAccessible(true);
            if (fieldsAll[i].get(obj) != null) {
                json = json + "\"" + fieldsAll[i].getName() + "\"" + ":";
                if (PrimitiveTypeInJson.isPrimitiveType(fieldsAll[i].get(obj))) {
                    json = json + PrimitiveTypeInJson.primitiveTypeInJson(fieldsAll[i].get(obj));
                }
                if (StringTypeInJson.isStringType(fieldsAll[i].get(obj))) {
                    json = json + StringTypeInJson.stringTypeInJson(fieldsAll[i].get(obj));
                }
                if (ArrayTypeInJson.isArrayType(fieldsAll[i].get(obj))) {
                    json = json + ArrayTypeInJson.ArrayTypeInJson(fieldsAll[i].get(obj));
                }
                if (CollectionTypeInJson.isCollectionType(fieldsAll[i].get(obj))) {
                    json = json + CollectionTypeInJson.CollectionTypeInJson(fieldsAll[i].get(obj));
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
