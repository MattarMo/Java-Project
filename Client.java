import java.util.Random;

public class Client {

    private String customerId;
    private int waitTime;
    private int serverTime;
    private int wouldWaitTime;
    private int precederTimeAfterSwitch;
    private boolean switchedQueues;
    private String preceededBySwitchedId;
    private int originalQueue;
    private int currentQueue;

    private static Random random = new Random();

    Client(String ID) {
        customerId = ID;
        waitTime = 0;
        serverTime = random.nextInt(1 + 5);
        wouldWaitTime = 0;
        switchedQueues = false;
        preceededBySwitchedId = "";
        precederTimeAfterSwitch = -1;
    }

    public void copyClient(Client c1) {
        this.waitTime = c1.getWaitTime();
        this.serverTime = c1.getServerTime();
        this.wouldWaitTime = c1.getWouldHaveWaitedTime();
        this.switchedQueues = c1.getSwitchedQueues();
        this.preceededBySwitchedId = c1.getPrevBySwitchedId();
        this.precederTimeAfterSwitch = c1.getPrevTimeAfterSwitch();
    }

    public String getId() {
        return customerId;
    }

    public void setOriginalQueue(int ogQ) {
        originalQueue = ogQ;
    }

    public void setCurrentQueue(int cQ) {
        currentQueue = cQ;
    }

    public int getPrevTimeAfterSwitch() {
        return precederTimeAfterSwitch;
    }

    public void setPrevBySwitchedId(String sId) {
        preceededBySwitchedId = sId;
    }

    public String getPrevBySwitchedId() {
        return preceededBySwitchedId;
    }

    public boolean getSwitchedQueues() {
        return switchedQueues;
    }

    public void setSwitchedQueues(boolean b) {
        switchedQueues = b;
    }
    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int wt) {
        waitTime = wt;
    }

    public void setWouldHaveWaitedTime(int t) {
        wouldWaitTime = t;
    }

    public int getWouldHaveWaitedTime() {
        return wouldWaitTime;
    }

    public int getServerTime() {
        return serverTime;
    }

    public String toString() {
        boolean terseOutput = true;
        String outputStr = "\n Customer ID " + this.customerId + " current wait time " + this.waitTime + " original queue " + this.originalQueue + "  current queue " + this.currentQueue + " serverTime " + serverTime;
        if (terseOutput == true) outputStr += " time that would have been spent waiting " + wouldWaitTime;
        return outputStr;
    }
}