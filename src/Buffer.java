import sources.Order;

public class Buffer {
    private int bufferSize;
    private Order[] ringArray;
    private int currentIndex;
    private int currentPackagePriority; //= -1 by default

    public Buffer(int bufferSize) {
        this.bufferSize = bufferSize;
        this.ringArray = new Order[bufferSize];
        currentIndex = 0;
        currentPackagePriority = -1;
    }

    boolean setOrder(Order newOrder) {
        int weakOrderIndex = -1;
        int leastPriority = newOrder.getPriority();
        for (int i = 0; i < bufferSize; ++i) {
            int tmpIndex = (i + currentIndex) % bufferSize;
            if (ringArray[tmpIndex] == null) {
                ringArray[tmpIndex] = newOrder;
                currentIndex = (i + currentIndex + 1) % bufferSize;

                return true;
            }
            else if (newOrder.getPriority() > ringArray[tmpIndex].getPriority() && leastPriority > ringArray[tmpIndex].getPriority()) {
                weakOrderIndex = tmpIndex;
                leastPriority = ringArray[tmpIndex].getPriority();
            }
        }

        if (weakOrderIndex != -1) {
            ringArray[weakOrderIndex].cancel();
            ringArray[weakOrderIndex] = newOrder;
            currentIndex = weakOrderIndex + 1;

            return true;
        }

        newOrder.cancel();
        System.out.println("CANCEL: " + newOrder.getPriority());
        return false;
    }

    Order getOrder() throws Exception {
        if (currentPackagePriority == -1) {
            for (int i = 0; i < bufferSize; ++i) {
                if (ringArray[i] != null && ringArray[i].getPriority() > currentPackagePriority) {
                    currentPackagePriority = ringArray[i].getPriority();
                }
            }
        }

        for (int i = 0; i < bufferSize; ++i) {
            int tmpIndex = (i + currentIndex) % bufferSize;
            if (ringArray[tmpIndex] != null && ringArray[tmpIndex].getPriority() == currentPackagePriority) {
                Order tmpOrder = ringArray[tmpIndex];
                ringArray[tmpIndex] = null;
                return  tmpOrder;
            }
        }

        if (!isEmpty()) {
            currentPackagePriority = -1;
            return getOrder();
        }

        throw new Exception("Buffer is empty");
    }

    public boolean isEmpty() {
        for (int i = 0; i < bufferSize; ++i) {
            if (ringArray[i] != null) {
                return false;
            }
        }

        return true;
    }

    public void print() {
        System.out.println("----- BUFFER: " + currentPackagePriority + " package " + currentIndex + " index");
        for (int i = 0; i < bufferSize; ++i) {
            if (ringArray[i] != null) {
                System.out.print(ringArray[i].getPriority() + " ; ");
            }
            else {
                System.out.print(null + " ; ");
            }
        }
        System.out.println("\n-----");
    }
}
