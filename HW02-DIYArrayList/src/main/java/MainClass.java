import java.util.*;

public class MainClass {
    public static void main(String[] args) {
        final int defaultSize = 30;

        DIYArrayList<Integer> diyArrayList1 = new DIYArrayList<>(); //первый конструктор
        System.out.println("первый конструктор (пустой список): " + diyArrayList1.toString());

        DIYArrayList<Integer> diyArrayList2 = new DIYArrayList<>(defaultSize); // второй конструктор
        System.out.println("второй конструктор размером " + diyArrayList2.capacity() + " (пустой список): " + diyArrayList2.toString());

        DIYArrayList<String> diyArrayList3 = new DIYArrayList<>(Arrays.asList("Tuzik", "Barsik", "Strelka",
                "Belka", "moska", "Laika", "Bobik", "Mishka", "malutka")); // третий конструктор
        System.out.println("третий конструктор: " + diyArrayList3.toString());
        System.out.println("---------------------------------------------------");

        Collections.addAll(diyArrayList3, "T", "L", "a", "Mamba", "ponchik");
        System.out.println("после применения метода addAll: " + diyArrayList3.toString());
        System.out.println("---------------------------------------------------");

        DIYArrayList<String> diyArrayList4 = new DIYArrayList<>(Arrays.asList("1", "2", "3"));
        Collections.copy(diyArrayList3, diyArrayList4);
        System.out.println("после применения метода copy: " + diyArrayList3.toString());
        System.out.println("---------------------------------------------------");

        DIYArrayList<Message> messages = new DIYArrayList(Arrays.asList(new Message("Example message"), new Message("Spring"), new Message("Hilly")));
        System.out.println("подготовили список класса Message для упорядочивания: " + messages);
        Comparator<Message> comparator = Comparator.comparing(obj -> obj.getId());
        Collections.sort(messages, comparator);
        System.out.println("упорядочили: " + messages.toString());

        for (int i = 0; i < 30; i++) {
            diyArrayList2.add(new Random().nextInt(1000));
        }
        System.out.println("подготовили список int для упорядочивания: " + diyArrayList2);
        Collections.sort(diyArrayList2);
        System.out.println("упорядочили: " + diyArrayList2);
    }

}
