package JSON;

import JSON.MyJSON.MyGson;
import JSON.TestClass.EmbeddedObjectClass;
import JSON.TestClass.TestClass1FromJson;
import JSON.TestClass.TestClass2FromJson;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainClassJSON {
    public static void main(String[] args) throws IllegalAccessException {
        Gson gson = new Gson();
        MyGson myGson = new MyGson();
        TestClass1FromJson<Integer> obj = new TestClass1FromJson(22, "String", Arrays.asList(1.0, 2.0, 3.0),
                new String[]{"arrayString1", "arrayString2", "arrayString3"}, new StringBuilder("StringBuilder"));
        List<Integer> collection = new ArrayList<>();
        collection.add(123);
        collection.add(567);
        collection.add(959);
        TestClass2FromJson obj2 = new TestClass2FromJson(new Object[]{new EmbeddedObjectClass(new StringBuilder("EmbeddedString"))}, collection);
        TestClass2FromJson obj3 = null;

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

        TestClass1FromJson objFromJson = gson.fromJson(json, TestClass1FromJson.class);
        TestClass2FromJson objFromJson2 = gson.fromJson(json2, TestClass2FromJson.class);

        TestClass1FromJson objFromMyJson = gson.fromJson(myJson, TestClass1FromJson.class);
        TestClass2FromJson objFromMyJson2 = gson.fromJson(myJson2, TestClass2FromJson.class);

        System.out.println("obj2 =           " + obj2);
        System.out.println("objFromMyJson2 = " + objFromMyJson2);
        System.out.println("objFromJson2 =   " + objFromJson2);

        System.out.println("Equals1 = " + obj.equals(objFromMyJson));
        System.out.println("Equals2 = " + obj2.equals(objFromMyJson2));

    }
}
