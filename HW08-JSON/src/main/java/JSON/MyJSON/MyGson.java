package JSON.MyJSON;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Ayrat Zagretdinov
 * created on 24.06.2020
 */
public class MyGson {

    public String toJson(Object obj) throws IllegalAccessException {
        if ((obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Integer) || (obj instanceof Short) ||
                (obj instanceof Long) || (obj instanceof Float) || (obj instanceof Double) || (obj instanceof Boolean)) {
            return obj.toString();
        } else {
            String json = "";
            if (obj != null) {
                if (!(obj.getClass().getPackageName().equals("java.lang") || obj.getClass().getPackageName().equals("java.util"))) {
                    json = "{";
                    Field[] fieldsAll = obj.getClass().getDeclaredFields();
                    for (int i = 0; i < fieldsAll.length; i++) {
                        fieldsAll[i].setAccessible(true);
                        if (fieldsAll[i].get(obj) != null) {
                            json = json + "\"" + fieldsAll[i].getName() + "\"" + ":";
                            json = json + fieldIntoJson(fieldsAll[i], obj);
                            if (i < fieldsAll.length - 1) {
                                json = json + ",";
                            }
                        }
                    }
                    json = json + "}";
                    return json;
                } else if (obj.getClass().getPackageName().equals("java.util")) {
                    json = json + obj;
                    return json;

                } else {
                    json = json + "\"" + obj + "\"";
                    return json;
                }
            } else {
                return null;
            }
        }
    }

    private String fieldIntoJson(Field field, Object obj) throws IllegalAccessException { //Определение типа поля
        if (field.get(obj) != null) {
            String value = "";
            switch (definingTheTypeOfVariable(field.getType())) {
                case "primitive":
                    value = field.get(obj).toString();
                    break;
                case "string":
                    value = '"' + field.get(obj).toString() + '"';
                    break;
                case "array":
                    value = value + arrayFieldInAJson(field.get(obj));
                    break;
                case "notDefined":
                    if ((field.get(obj) instanceof Collection)) {
                        value = value + collectionInAJson(field.get(obj));
                    } else {
                        value = value + toJson(field.get(obj));
                    }
                    break;
                default:
                    break;
            }
            return value;
        } else {
            return null;
        }
    }

    private String arrayFieldInAJson(Object array) throws IllegalAccessException { //Добавление массива в JSON
        if (array != null) {
            String arrayValue = "[";
            int arrayLength = Array.getLength(array);
            for (int i = 0; i < arrayLength; i++) {
                if (Array.get(array, i) != null) {
                    switch (definingTheTypeOfVariable(Array.get(array, i).getClass())) {
                        case "primitive":
                            arrayValue = arrayValue + "\"" + Array.get(array, i) + "\"";
                            break;
                        case "string":
                            arrayValue = arrayValue + '"' + Array.get(array, i).toString() + '"';
                            break;
                        case "array":
                            arrayValue = arrayValue + "\"" + arrayFieldInAJson(Array.get(array, i)) + "\"";
                            break;
                        case "notDefined":
                            if (Array.get(array, i) instanceof Collection) {
                                arrayValue = arrayValue + collectionInAJson(array);
                            } else {
                                arrayValue = arrayValue + toJson(Array.get(array, i));
                            }
                            break;
                        default:
                            break;
                    }
                }
                if (i < arrayLength - 1) {
                    arrayValue = arrayValue + ",";
                }
            }
            arrayValue = arrayValue + "]";
            return arrayValue;
        }
        return null;
    }

    private String collectionInAJson(Object value) { //Добавление Collection в JSON
        String collectionValue = "[";
        Iterator iterator = ((Collection) value).iterator();
        while (iterator.hasNext()) {
            collectionValue = collectionValue + iterator.next();
            if (iterator.hasNext()) {
                collectionValue = collectionValue + ",";
            }
        }
        collectionValue = collectionValue + "]";
        return collectionValue;
    }

    private String definingTheTypeOfVariable(Class<?> variable) { //Определение типа переменной
        String type = "notDefined";
        if (variable.isPrimitive()) {
            type = "primitive";
        } else if (variable.isArray()) {
            type = "array";
        } else if (definingAString(variable)) {
            type = "string";
        }
        return type;
    }

    private boolean definingAString(Class<?> variable) {
        return variable.getName().equals("java.lang.String") ||
                variable.getName().equals("java.lang.StringBuilder") ||
                variable.getName().equals("java.lang.StringBuffer");
    }
}