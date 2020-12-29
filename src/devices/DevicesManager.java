package devices;

import sources.Order;
import utils.PoissonGenerator;

import java.util.ArrayList;
import java.util.List;

public class DevicesManager {
    private int devicesCount;
    private List<Device> deviceList = new ArrayList<>();

    public DevicesManager(int devicesCount) {
        this.devicesCount = devicesCount;
        for (int i = 0; i < devicesCount; ++i) {
            //TODO: input working time
            deviceList.add(new Device(i, 0.3 + PoissonGenerator.generateNum(3)));
        }
    }

    public double getMinTime() {
        double minTime = deviceList.get(devicesCount - 1).getTimeEnd();
        for (int i = 0; i < devicesCount - 1; ++i) {
            if (minTime > deviceList.get(i).getTimeEnd()) {
                minTime = deviceList.get(i).getTimeEnd();
            }
        }

        return minTime;
    }

    public void setOrderToDevice(Order order, double currentTime) {
        order.setTimeService(currentTime);
        for (int i = devicesCount - 1; i >= 0; --i) {
            if (deviceList.get(i).getTimeEnd() <= currentTime) {
                deviceList.get(i).setOrder(order, currentTime);
                break;
            }
        }
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

    public double getDeviceUsing(int deviceNum) throws Exception {
        double fullTime = 0.;
        for (Device device : deviceList) {
            if (device.getTimeEnd() > fullTime) {
                fullTime = device.getTimeEnd();
            }
        }
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

    public void printdevicesWorkingTime() {
        System.out.println("--------- DEVICES WORKING TIME");
        for (int i = 0; i < devicesCount; ++i) {
            System.out.println("Device #" + i + " has working time " + deviceList.get(i).getWorkingTime());
        }
        System.out.println("---------");
    }

}
