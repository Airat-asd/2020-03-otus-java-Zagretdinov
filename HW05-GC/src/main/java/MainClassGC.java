//-XX:MaxGCPauseMillis=10

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class MainClassGC {
    public static void main(String[] args) throws Exception {
        final int AMOUNT = 100_000;
        final int NUMBNER_OF_CYCLE_REPEATS = 30;
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();
        long beginTime = System.currentTimeMillis();

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.OTUS:type=Benchmark");
        Benchmark mbean = new Benchmark();
        mbs.registerMBean(mbean, name);

        mbean.outOfMemory(AMOUNT, NUMBNER_OF_CYCLE_REPEATS);

        System.out.println("time:" + (System.currentTimeMillis() - beginTime) / 1000);
    }

    private static void switchOnMonitoring() {
        final int[] countMinor = {0};
        final int[] countMajor = {0};
        final float[] avrDurationMinor = {0};
        final float[] avrDurationMajor = {0};
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    double startTime = ((double) info.getGcInfo().getStartTime()) / 1000;
                    long duration = info.getGcInfo().getDuration();
                    if (gcAction.equals("end of minor GC")) {
                        avrDurationMinor[0] = (avrDurationMinor[0] + duration);
                        countMinor[0]++;
                    } else {
                        avrDurationMajor[0] = (avrDurationMajor[0] + duration);
                        countMajor[0]++;
                    }
                    System.out.printf("countMinor = " + countMinor[0] + "  countMajor = " + countMajor[0] + "  start: %.2f ", startTime);
                    System.out.println(" Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms) avrMinor=" + avrDurationMinor[0] / countMinor[0] + " avrMagor = " + avrDurationMajor[0] / countMajor[0]);
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
