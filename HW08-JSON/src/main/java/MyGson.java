import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author Ayrat Zagretdinov
 * created on 24.06.2020
 */
public class MyGson {

    public String toJson(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return null;
        } else {
            String Json = "{";
            Field[] fieldsAll = obj.getClass().getDeclaredFields();
            for (int i = 0; i < fieldsAll.length; i++) {
                fieldsAll[i].setAccessible(true);
                if (!(fieldsAll[i].get(obj) == null)) {
                    Json = Json + "\"" + fieldsAll[i].getName() + "\"" + ":";
                    Json = Json + valueInAJson(fieldsAll[i], obj);
                    if (i < fieldsAll.length - 1) {
                        Json = Json + ",";
                    } else {
                        Json = Json + "}";
                    }
                }
            }
            return Json;
        }
    }

    private String valueInAJson(Field field, Object obj) throws IllegalAccessException {
        if (field.get(obj) == null) {
            return null;
        } else {
            String value = "";
            switch (field.getGenericType().getTypeName()) {
                case "int":
                    value = field.get(obj).toString();
                    break;
                case "java.lang.String":
                    value = '"' + field.get(obj).toString() + '"';
                    break;
                case "java.lang.StringBuilder":
                    value = '"' + field.get(obj).toString() + '"';
                    break;
                case "java.lang.StringBuffer":
                    value = '"' + field.get(obj).toString() + '"';
                    break;
                case "int[]":
                    value = Arrays.toString((int[]) field.get(obj)).replace(" ", "");
                    break;
                default:
                    value = field.get(obj).toString().replace(" ", "");
                    break;
            }
            return value;
        }
    }
}
