import devices.DevicesManager;
import sources.SourcesManager;

import java.util.List;
import java.util.Scanner;

public class QueueingTheorySystem {
    private SourcesManager sourcesManager;
    private DevicesManager devicesManager;
    private double systemTime;
    QueueingTheorySystem() throws Exception {
        int sourcesCount = 5;
        int deviceCount = 6;
        int bufferSize = 10;
        int fullOrdersCount = 2500;

        sourcesManager = new SourcesManager(sourcesCount);
        devicesManager = new DevicesManager(deviceCount);
        Buffer buffer = new Buffer(bufferSize);
        int ordersCount = 0;

        boolean autoMode = false;

        while (ordersCount != fullOrdersCount || (ordersCount == fullOrdersCount && !buffer.isEmpty())) {
        //(ordersCount == fullOrdersCount && !buffer.isEmpty()) - special condition to clear buffer after all sources were stopped
            double sourceMinTime = sourcesManager.getMinTime();
            double deviceMinTime = devicesManager.getMinTime();

            if (deviceMinTime < sourceMinTime && buffer.isEmpty()) { //special condition for start of simulation
                deviceMinTime = sourceMinTime;
            }
            if (sourceMinTime <= deviceMinTime && ordersCount != fullOrdersCount) {
                buffer.setOrder(sourcesManager.createOrder());
                ++ordersCount;
            }
            if (deviceMinTime <= sourceMinTime || (ordersCount == fullOrdersCount && !buffer.isEmpty())) {
                try {
                    devicesManager.setOrderToDevice(buffer.getOrder(), sourceMinTime);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
            }

            systemTime = sourceMinTime < deviceMinTime ? sourceMinTime : deviceMinTime;

            //Print intermediate information
            if (!autoMode) {
                System.out.println("Next (n) or Auto (a)");
                Scanner input = new Scanner(System.in);
                String ch = input.next();

                if (!ch.equals("a")) {
                    buffer.print();
                    sourcesManager.printEventCalendar();
                    devicesManager.printEventCalendar(systemTime);
                    sourcesManager.printOrdersStats();
                    System.out.println();
                }
                else {
                    autoMode = true;
                }
            }
        }
        System.out.println("\n############### RESULTS");
        printStatistics(sourcesCount, deviceCount);
    }

    private void printStatistics(int sourceCount, int deviceCount) throws Exception {
        System.out.println("--------- ORDERS COUNT: ");
        for (int i = 0; i < sourceCount; ++i) {
            List<Integer> tmpList = sourcesManager.getSourceOrdersCountInfo(i);
            System.out.println("sources.Source #" + i + " created all " + tmpList.get(0) + " orders which canceled " + tmpList.get(1));
            System.out.println("Probability of cancel: " + (double)tmpList.get(1) / (double)tmpList.get(0));
        }
        System.out.println("--------- AVG TIMES:");
        for (int i = 0; i < sourceCount; ++i) {
            System.out.println("--- sources.Source #" + i);
            System.out.println("avg time in system " + sourcesManager.getSourceAvgTimeInSystem(i));
            System.out.println("avg waiting time " + sourcesManager.getSourceAvgWaitingTime(i));
            System.out.println("dispersion " + sourcesManager.computeSourceWaitingTimeDispersion(i));
            System.out.println("avg service time " + sourcesManager.getSourceAvgServiceTime(i));
            System.out.println("dispersion " + sourcesManager.computeSourceServiceTimeDispersion(i));
        }
        System.out.println("--------- DEVICE USING");
        for (int i = 0; i < deviceCount; ++i) {
            System.out.println("devices.Device #" + i + " " + devicesManager.getDeviceUsing(i, systemTime));
        }
    }
}
