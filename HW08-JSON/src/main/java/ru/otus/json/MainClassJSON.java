package ru.otus.json;

import com.google.gson.Gson;
import ru.otus.json.myJson.MyGson;
import ru.otus.json.testClass.EmbeddedObjectClass;
import ru.otus.json.testClass.TestClass1FromJson;
import ru.otus.json.testClass.TestClass2FromJson;

import java.util.*;

public class MainClassJSON {
    public static void main(String[] args) throws IllegalAccessException  {
        Gson gson = new Gson();
        MyGson myGson = new MyGson();
        //------------obj--------------------
        TestClass1FromJson<Integer> obj = new TestClass1FromJson(22, "String", Arrays.asList(1.0, 2.0, 3.0),
                new String[]{"arrayString1", "arrayString2", "arrayString3"}, new StringBuilder("StringBuilder"));
        String json = gson.toJson(obj);
        System.out.println("Create json =   " + json);
        String myJson = myGson.toJson(obj);
        System.out.println("Create myJson = " + myJson);
        TestClass1FromJson objFromMyJson = gson.fromJson(myJson, TestClass1FromJson.class);
        System.out.println("Equals1 = " + obj.equals(objFromMyJson));
        //------------obj2--------------------
        List<Double> collection = new ArrayList<>();
        collection.add(123.0);
        collection.add(567.0);
        collection.add(959.0);
        TestClass2FromJson<Double> obj2 = new TestClass2FromJson<>(new Object[]{new EmbeddedObjectClass
                (new StringBuilder("EmbeddedString")), null}, collection);
        String json2 = gson.toJson(obj2);
        System.out.println("Create json2 =   " + json2);
        String myJson2 = myGson.toJson(obj2);
        System.out.println("Create myJson2 = " + myJson2);
        TestClass2FromJson objFromMyJson2 = gson.fromJson(myJson2, TestClass2FromJson.class);
        System.out.println("Equals2 = " + obj2.equals(objFromMyJson2));
        //------------obj3--------------------
        TestClass2FromJson obj3 = null;
        String json3 = gson.toJson(obj3);
        System.out.println("Create json3 =   " + json3);
        String myJson3 = myGson.toJson(obj3);
        System.out.println("Create myJson3 = " + myJson3);
        //------------obj4--------------------
        Short obj4 = 2;
        String myJson4 = myGson.toJson(obj4);
        System.out.println("Create myJson4 = " + myJson4);
        String json4 = gson.toJson(obj4);
        System.out.println("Create json4 = " + json4);
        Short objFromMyJson4 = gson.fromJson(myJson4, Short.class);
        System.out.println("Equals4 = " + obj4.equals(objFromMyJson4));
        //------------obj5--------------------
        String obj5 = "String";
        String myJson5 = myGson.toJson(obj5);
        System.out.println("Create myJson5 = " + myJson5);
        String json5 = gson.toJson(obj5);
        System.out.println("Create Json5 = " + json5);
        String objFromMyJson5 = gson.fromJson(myJson5, String.class);
        System.out.println("Equals5 = " + obj5.equals(objFromMyJson5));
        //------------obj6--------------------
        char obj6 = 'C';
        String myJson6 = myGson.toJson(obj6);
        System.out.println("Create myJson6 = " + myJson6);
        String json6 = gson.toJson(obj6);
        System.out.println("Create json6 = " + json6);
        Character objFromMyJson6 = gson.fromJson(myJson6, Character.class);
        System.out.println("Equals6 = " + (obj6 == objFromMyJson6));
        //------------obj7--------------------
        Collection<Double> obj7 = Collections.singletonList(13.0);
        String myJson7 = myGson.toJson(obj7);
        System.out.println("Create myJson7 = " + myJson7);
        String json7 = gson.toJson(obj7);
        System.out.println("Create json7 = " + json7);
        Collection<Double> objFromMyJson7 = gson.fromJson(myJson7, Collection.class);
        System.out.println("Equals7 = " + (obj7.equals(objFromMyJson7)));
        //------------obj8--------------------
        Collection<Double> obj8 = collection;
        String myJson8 = myGson.toJson(obj8);
        System.out.println("Create myJson8 = " + myJson8);
        String json8 = gson.toJson(obj8);
        System.out.println("Create Json8 = " + json8);
        Collection objFromMyJson8 = gson.fromJson(myJson8, Collection.class);
        System.out.println("Equals8 = " + obj8.equals(objFromMyJson8));
        //------------obj9--------------------
        Double[] obj9 = {1.0, 2.0, 3.0, 4.0, 5.0};
        String json9 = gson.toJson(obj9);
        System.out.println("Create Json9 = " + json9);
        String myJson9 = myGson.toJson(obj9);
        System.out.println("Create myJson9 = " + myJson9);
        Double[] objFromMyJson9 = gson.fromJson(myJson9, Double[].class);
        System.out.println("Equals9 = " + Arrays.toString(obj9).equals(Arrays.toString(objFromMyJson9)));
        //------------obj10--------------------
        List<List> matryoshka = new ArrayList<>();
        matryoshka.add(Arrays.asList("matryoshka1", "matryoshka2"));
        matryoshka.add(Arrays.asList("matryoshka11", "matryoshka22"));
        TestClass2FromJson<List> obj10 = new TestClass2FromJson<>(null, matryoshka);
        String json10 = gson.toJson(obj10);
        System.out.println("Create json10 =   " + json10);
        String myJson10 = myGson.toJson(obj10);
        System.out.println("Create myJson10 = " + myJson10);
        TestClass2FromJson<List> objFromMyJson10 = gson.fromJson(myJson10, TestClass2FromJson.class);
        System.out.println("Equals10 = " + obj10.equals(objFromMyJson10));
    }
}
