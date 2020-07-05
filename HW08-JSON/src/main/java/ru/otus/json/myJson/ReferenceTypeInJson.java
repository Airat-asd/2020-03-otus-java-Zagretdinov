package ru.otus.json.myJson;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Ayrat Zagretdinov
 * created on 30.06.2020
 */
public class ReferenceTypeInJson {
    public static boolean isReferenceType(Object obj) {
        return (obj.getClass().getClassLoader() != null);
    }

    public static String referenceTypeInJson(Object obj) throws IllegalAccessException {
        String json = "{";
        Field[] fieldsAll = obj.getClass().getDeclaredFields();
        String[] buffer = new String[fieldsAll.length];
        int positionBuffer = 0;
        for (int i = 0; i < fieldsAll.length; i++) {
            fieldsAll[i].setAccessible(true);
            if ((fieldsAll[i].get(obj) != null) & !(Modifier.isTransient(fieldsAll[i].getModifiers())) &
                    !(Modifier.isFinal(fieldsAll[i].getModifiers()) && Modifier.isStatic(fieldsAll[i].getModifiers()))) {
                buffer[positionBuffer] = "\"" + fieldsAll[i].getName() + "\"" + ":" +
                        ObjectToJson.objectToJson(fieldsAll[i].get(obj));
                positionBuffer++;
            }
        }
        for (int i = 0; i < positionBuffer; i++) {
            json = json + buffer[i];
            if (i < positionBuffer - 1) {
                json = json + ",";
            }
        }
        json = json + "}";
        return json;
    }
}
