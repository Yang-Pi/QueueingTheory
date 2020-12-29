package devices;

import sources.Order;

public class Device {
    private int deviceNumber;
    private double workingTime;

    private double timeEnd;
    private int ordersCount;

    public Device(int deviceNumber, double workingTime) {
        this.deviceNumber = deviceNumber;
        this.workingTime = workingTime;
        timeEnd = 0.;
        ordersCount = 0;
    }

    void setOrder(Order order, double currentTime) {
        timeEnd = currentTime + workingTime;
        ordersCount++;
        order.setTimeEnd(currentTime + workingTime);
        System.out.println("DEVICE " + deviceNumber + " set order " + order.getPriority() + " at "+ currentTime + ", finish " + timeEnd);
    }

    public int getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(int deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public double getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(double workingTime) {
        this.workingTime = workingTime;
    }

    public double getTimeEnd() {
        return timeEnd;
    }

    public int getOrdersCount() {
        return ordersCount;
    }

    public double computeDeviceUsing(double fullTime) {
        return ordersCount * workingTime / fullTime;
    }
}
