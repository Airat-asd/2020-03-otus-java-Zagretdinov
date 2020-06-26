import com.google.gson.Gson;

import java.util.Arrays;
import java.util.TreeSet;

public class MainClassJSON {
    public static void main(String[] args) throws IllegalAccessException {
        Gson gson = new Gson();
        MyGson myGson = new MyGson();
        TestClassFromJson obj = new TestClassFromJson(22, "String", Arrays.asList(1, 2, 3), new int[]{1, 2, 3}, new StringBuilder("StringBuilder"));
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(123);
        treeSet.add(567);
        treeSet.add(9593);
        TestClassFromJson obj2 = new TestClassFromJson(33, null, treeSet, new int[]{1, 2, 3}, new StringBuilder("StringBuilder"));
        TestClassFromJson obj3 = null;

        String json = gson.toJson(obj);
        System.out.println("Create JSON =   " + json);

        String myJson = myGson.toJson(obj);
        System.out.println("Create MyJSON = " + myJson);

        String json2 = gson.toJson(obj2);
        System.out.println("Create JSON2 =   " + json2);

        String myJson2 = myGson.toJson(obj2);
        System.out.println("Create MyJSON2 = " + myJson2);

        String myJson3 = myGson.toJson(obj3);
        System.out.println("Create MyJSON3(null) = " + myJson3);

        TestClassFromJson objFromMyJson = gson.fromJson(myJson, TestClassFromJson.class);
        TestClassFromJson objFromMyJson2 = gson.fromJson(myJson2, TestClassFromJson.class);

        System.out.println(obj2);
        System.out.println(objFromMyJson2);

        System.out.println("Equals1 = " + obj.equals(objFromMyJson));
        System.out.println("Equals2 = " + obj2.equals(objFromMyJson2));

    }
}
