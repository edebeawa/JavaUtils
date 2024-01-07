package run;

public class TimeConsume {
    private long time;

    public void start() {
        time = System.currentTimeMillis();
    }

    public long end() {
        return System.currentTimeMillis() - time;
    }
}