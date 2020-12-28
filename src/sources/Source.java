package sources;

import utils.PoissonGenerator;

import java.util.ArrayList;
import java.util.List;

public class Source {
    private int sourceNumber;
    private int intensity;

    private List<Order> ordersList = new ArrayList<>();
    private double nextOrderTime;

    public Source(int sourceNumber, int intensity) {
        this.sourceNumber = sourceNumber;
        this.intensity = intensity;
        nextOrderTime = PoissonGenerator.generateNum(intensity);
    }

    public void createOrder() {
        System.out.print("CREATE ORDER: " + sourceNumber + " " + nextOrderTime + "; next order ");
        ordersList.add(new Order(sourceNumber, nextOrderTime));
        nextOrderTime += PoissonGenerator.generateNum(intensity);
        System.out.println(nextOrderTime);
    }

    public int getNumber() {
        return sourceNumber;
    }

    public void setNumber(int sourceNumber) {
        this.sourceNumber = sourceNumber;
    }

    public double getNextOrderTime() {
        return nextOrderTime;
    }

    public void setNextOrderTime(double nextOrderTime) {
        this.nextOrderTime = nextOrderTime;
    }

    public Order getLastOrder() {
        return ordersList.get(ordersList.size() - 1);
    }

    public int getOrdersCount() {
        return ordersList.size();
    }

    public int getCanceledOdersCount() {
        int count = 0;
        for (Order order : ordersList) {
            if (order.isCanceled()) {
                ++count;
            }
        }

        return count;
    }

    public double getAvgTimeInSystem() {
        double fullTime = 0.;
        for (Order order : ordersList) {
            if (!order.isCanceled()) {
                fullTime += order.getTimeEnd() - order.getTimeStart();
            }
        }
        return fullTime / ordersList.size();
    }

    public double getAvgWaitingTime() {
        double fullTime = 0.;
        for (Order order : ordersList) {
            if (!order.isCanceled()) {
                fullTime += order.getTimeService() - order.getTimeStart();
            }
        }
        return fullTime / ordersList.size();
    }

    public double getAvgServiceTime() {
        double fullTime = 0.;
        for (Order order : ordersList) {
            if (!order.isCanceled()) {
                fullTime += order.getTimeEnd() - order.getTimeService();
            }
        }
        return fullTime / ordersList.size();
    }

    public double getOrderWaitingTime(int orderNum) {
        return ordersList.get(orderNum).getTimeService() - ordersList.get(orderNum).getTimeStart();
    }

    public double getOrderServiceTime(int orderNum) {
        return ordersList.get(orderNum).getTimeEnd() - ordersList.get(orderNum).getTimeService();
    }
}
