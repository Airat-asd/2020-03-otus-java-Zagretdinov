package ru.otus.json.myJson;

/**
 * @author Ayrat Zagretdinov
 * created on 30.06.2020
 */
public class StringTypeInJson {

    public static boolean isStringType(Object obj) {
        return obj.getClass() == String.class ||
                obj.getClass() == StringBuilder.class ||
                obj.getClass() == StringBuffer.class ||
                obj.getClass() == Character.class;
    }

    public static String stringTypeInJson(Object obj) {
        return "\"" + obj + "\"";
    }
}
