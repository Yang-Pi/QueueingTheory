package sources;

import java.util.ArrayList;
import java.util.List;

public class SourcesManager {
    private int sourcesCount;
    private List<Source> sourceList = new ArrayList<>();

    private int readySourceNum;

    public SourcesManager(int sourcesCount) {
        this.sourcesCount = sourcesCount;
        readySourceNum = -1;

        for (int i = 0; i < sourcesCount; ++i) {
            //TODO: input lambda for each source
            sourceList.add(new Source(i, (sourcesCount - i) / 2 + 1)); //hardcode 1
        }
    }

    public double getMinTime() {
        double minTime = sourceList.get(0).getNextOrderTime();
        readySourceNum = 0;

        for (int i = 1; i < sourcesCount; ++i) {
            if (minTime > sourceList.get(i).getNextOrderTime()) {
                minTime = sourceList.get(i).getNextOrderTime();
                readySourceNum = i;
            }
        }
        return minTime;
    }

    public Order createOrder() throws Exception {
        if (readySourceNum != -1) {
            sourceList.get(readySourceNum).createOrder();
            return sourceList.get(readySourceNum).getLastOrder();
        }

        throw new Exception("No ready sources");
    }

    public void printOrdersStats() {
        System.out.println("SOURCES: ");
        for (Source source : sourceList) {
            System.out.println("All orders " + source.getOrdersCount() + ", canceled ones " + source.getCanceledOdersCount() + "; ");
        }
    }

    public void printEventCalendar() {
        System.out.println("----- SOURCES CLOSEST EVENTS: ");
        for (Source source : sourceList) {
            System.out.println("Source #" + source.getNumber() + " future order time " + source.getNextOrderTime());
        }
        System.out.println("-----");
    }

    public double getFullCancelProb() {
        double sumCanceled = 0.;
        double sumAll = 0.;
        for (Source source : sourceList) {
            sumCanceled += source.getCanceledOdersCount();
            sumAll += source.getOrdersCount();
        }

        return sumCanceled / sumAll;
    }

    public List<Integer> getSourceOrdersCountInfo(int sourceNum) throws Exception {
        if (!isValidSourceNum(sourceNum)) {
            throw new Exception("Invalid source number");
        }

        List<Integer> resList = new ArrayList<>();
        resList.add(sourceList.get(sourceNum).getOrdersCount());
        resList.add(sourceList.get(sourceNum).getCanceledOdersCount());
        return resList;
    }

    public double getSourceAvgTimeInSystem(int sourceNum) throws Exception {
        if (!isValidSourceNum(sourceNum)) {
            throw new Exception("Invalid source number");
        }
        return sourceList.get(sourceNum).getAvgTimeInSystem();
    }

    public double getSourceAvgWaitingTime(int sourceNum) throws Exception {
        if (!isValidSourceNum(sourceNum)) {
            throw new Exception("Invalid source number");
        }
        return sourceList.get(sourceNum).getAvgWaitingTime();
    }

    public double getSourceAvgServiceTime(int sourceNum) throws Exception {
        if (!isValidSourceNum(sourceNum)) {
            throw new Exception("Invalid source number");
        }
        return sourceList.get(sourceNum).getAvgServiceTime();
    }

    public double computeSourceWaitingTimeDispersion(int sourceNum) throws Exception {
        if (!isValidSourceNum(sourceNum)) {
            throw new Exception("Invalid source number");
        }
        double avg = getSourceAvgWaitingTime(sourceNum);
        double sum = 0.;
        for (int i = 0; i < sourceList.get(sourceNum).getOrdersCount(); ++i) {
            sum += Math.pow(sourceList.get(sourceNum).getOrderWaitingTime(i) - avg, 2);
        }
        return sum / (sourceList.get(sourceNum).getOrdersCount() - 1);
    }

    public double computeSourceServiceTimeDispersion(int sourceNum) throws Exception {
        if (!isValidSourceNum(sourceNum)) {
            throw new Exception("Invalid source number");
        }
        double avg = getSourceAvgServiceTime(sourceNum);
        double sum = 0.;
        for (int i = 0; i < sourceList.get(sourceNum).getOrdersCount(); ++i) {
            sum += Math.pow(sourceList.get(sourceNum).getOrderServiceTime(i) - avg, 2);
        }
        return sum / (sourceList.get(sourceNum).getOrdersCount() - 1);
    }

    private boolean isValidSourceNum(int num) {
        if (num >= 0 && num < sourcesCount) {
            return true;
        }
        return false;
    }
}
