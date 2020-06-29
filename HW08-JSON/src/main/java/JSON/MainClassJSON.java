package JSON;

import JSON.MyJSON.MyGson;
import JSON.TestClass.EmbeddedObjectClass;
import JSON.TestClass.TestClass1FromJson;
import JSON.TestClass.TestClass2FromJson;
import com.google.gson.Gson;

import java.util.*;

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
        Short obj4 = 2;
        String obj5 = "String";
        char obj6 = 'C';
        Collection<Double> obj7 = Collections.singletonList(13.0);

        String json = gson.toJson(obj);
        System.out.println("Create json =   " + json);
        String myJson = myGson.toJson(obj);
        System.out.println("Create myJson = " + myJson);
        TestClass1FromJson objFromMyJson = gson.fromJson(myJson, TestClass1FromJson.class);
        System.out.println("Equals1 = " + obj.equals(objFromMyJson));

        String json2 = gson.toJson(obj2);
        System.out.println("Create json2 =   " + json2);
        String myJson2 = myGson.toJson(obj2);
        System.out.println("Create myJson2 = " + myJson2);
        TestClass2FromJson objFromMyJson2 = gson.fromJson(myJson2, TestClass2FromJson.class);
        System.out.println("Equals2 = " + obj2.equals(objFromMyJson2));

        String json3 = gson.toJson(obj3);
        System.out.println("Create json3 =   " + json3);
        String myJson3 = myGson.toJson(obj3);
        System.out.println("Create myJson3 = " + myJson3);

        String myJson4 = myGson.toJson(obj4);
        System.out.println("Create myJson4 = " + myJson4);
        String json4 = gson.toJson(obj4);
        System.out.println("Create json4 = " + json4);
        Short objFromMyJson4 = gson.fromJson(myJson4, Short.class);
        System.out.println("Equals4 = " + obj4.equals(objFromMyJson4));

        String myJson5 = myGson.toJson(obj5);
        System.out.println("Create myJson5 = " + myJson5);
        String json5 = gson.toJson(obj5);
        System.out.println("Create Json5 = " + json5);
        String objFromMyJson5 = gson.fromJson(myJson5, String.class);
        System.out.println("Equals5 = " + obj5.equals(objFromMyJson5));

        String myJson6 = myGson.toJson(obj6);
        System.out.println("Create myJson6 = " + myJson6);
        String json6 = gson.toJson(obj6);
        System.out.println("Create json6 = " + json6);
        Character objFromMyJson6 = gson.fromJson(myJson6, Character.class);
        System.out.println("Equals6 = " + (obj6 == objFromMyJson6));

        String myJson7 = myGson.toJson(obj7);
        System.out.println("Create myJson7 = " + myJson7);
        String json7 = gson.toJson(obj7);
        System.out.println("Create json7 = " + json7);
        Collection<Double> objFromMyJson7 = gson.fromJson(myJson7, Collection.class);
        System.out.println("Equals7 = " + (obj7.equals(objFromMyJson7)));
    }
}
