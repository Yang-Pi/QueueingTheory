package sources;

public class Order {
    private int priority;
    private double timeStart;
    private double timeService;
    private double timeEnd;
    private boolean canceled;

    public Order(int priority, double timeStart) {
        this.priority = priority;
        this.timeStart = timeStart;
        timeService = timeStart;
        timeEnd = timeStart;
        canceled = false;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public double getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(double timeStart) {
        this.timeStart = timeStart;
    }

    public double getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(double timEnd) {
        this.timeEnd = timEnd;
    }

    public void cancel() {
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public double getTimeService() {
        return timeService;
    }

    public void setTimeService(double timeService) {
        this.timeService = timeService;
    }
}
