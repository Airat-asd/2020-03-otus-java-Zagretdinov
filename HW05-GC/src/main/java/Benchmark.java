import java.util.ArrayList;
import java.util.List;

public class Benchmark implements BenchmarkMBean {

    @Override
    public void outOfMemory(int AMOUNT, int NUMBNER_OF_CYCLE_REPEATS) {
        List<String> list = new ArrayList<>();
        System.out.println("Start");
        for (int i = 0; i < NUMBNER_OF_CYCLE_REPEATS; i++) {
            for (int j = 0; j < AMOUNT; j++) {
                list.add(new String(new char[10]));
            }
            for (int j = 0; j < (AMOUNT / 2); j++) {
                list.remove(j);
            }
        }

        System.out.println("Stop");
    }
}
