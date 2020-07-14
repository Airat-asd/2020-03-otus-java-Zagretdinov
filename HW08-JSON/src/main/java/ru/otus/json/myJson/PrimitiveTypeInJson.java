package ru.otus.json.myJson;

/**
 * @author Ayrat Zagretdinov
 * created on 30.06.2020
 */
public class PrimitiveTypeInJson {

    public static boolean isPrimitiveType(Object obj) {
        return obj.getClass() == Byte.class ||
                obj.getClass() == Short.class ||
                obj.getClass() == Integer.class ||
                obj.getClass() == Long.class ||
                obj.getClass() == Float.class ||
                obj.getClass() == Float.class ||
                obj.getClass() == Double.class ||
                obj.getClass() == Boolean.class ||
                obj.getClass() == Void.class;
    }

    public static String primitiveTypeInJson(Object obj) {
        return obj.toString();
    }
}
