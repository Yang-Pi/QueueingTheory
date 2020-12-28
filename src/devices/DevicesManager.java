package devices;

import sources.Order;
import utils.PoissonGenerator;

import java.util.ArrayList;
import java.util.List;

public class DevicesManager {
    private int devicesCount;
    private List<Device> deviceList = new ArrayList<>();
    private int readyDeviceNum;

    public DevicesManager(int devicesCount) {
        this.devicesCount = devicesCount;
        readyDeviceNum = devicesCount - 1;
        for (int i = 0; i < devicesCount; ++i) {
            //TODO: input working time
            deviceList.add(new Device(i, 0.3 + PoissonGenerator.generateNum(3)));
        }
    }

    public double getMinTime() {
        double minTime = deviceList.get(0).getTimeEnd();
        readyDeviceNum = 0;
        for (int i = 1; i < devicesCount; ++i) {
            if (minTime >= deviceList.get(i).getTimeEnd()) {
                minTime = deviceList.get(i).getTimeEnd();
                readyDeviceNum = i;
            }
        }

        return minTime;
    }

    public void setOrderToDevice(Order order, double currentTime) {
        order.setTimeService(currentTime);
        deviceList.get(readyDeviceNum).setOrder(order, currentTime);
    }

    public void printEventCalendar(double currentTime) {
        System.out.println("----- DEVICE CLOSEST EVENTS: ");
        for (Device device : deviceList) {
            System.out.print("Device #" + device.getDeviceNumber() + " end work at " + device.getTimeEnd());
            if (device.getTimeEnd() > currentTime) {
                System.out.println(" ACTIVE");
            }
            else {
                System.out.println();
            }
        }
        System.out.println("-----");
    }

    public double getDeviceUsing(int deviceNum, double fullTime) throws Exception {
        if (isValidDeviceNum(deviceNum)) {
            return deviceList.get(deviceNum).computeDeviceUsing(fullTime);
        }
        throw new Exception("Invalid device num");
    }

    private boolean isValidDeviceNum(int num) {
        if (num >= 0 && num < devicesCount) {
            return true;
        }
        return false;
    }

}
