package ru.otus.json.myJson;

import java.lang.reflect.Field;

/**
 * @author Ayrat Zagretdinov
 * created on 30.06.2020
 */
public class ReferenceTypeInJson {
    private final static int TRANSIENT_ID = 127;
    private final static int CONSTANT_ID = 26;

    public static String referenceTypeInJson(Object obj) throws IllegalAccessException {
        String json = "{";
        Field[] fieldsAll = obj.getClass().getDeclaredFields();
        String[] buffer = new String[fieldsAll.length];
        int positionBuffer = 0;
        for (int i = 0; i < fieldsAll.length; i++) {
            fieldsAll[i].setAccessible(true);
            if ((fieldsAll[i].get(obj) != null) & (fieldsAll[i].getModifiers() < TRANSIENT_ID) &
                    (fieldsAll[i].getModifiers() != CONSTANT_ID)) {
                buffer[positionBuffer] = "\"" + fieldsAll[i].getName() + "\"" + ":" +
                        DefiningTypeVariable.definingTypeVariable(fieldsAll[i].get(obj));
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
