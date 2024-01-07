package run;

public class AutoTimeConsume extends TimeConsume {
    public AutoTimeConsume() {
        start();
    }

    public void print() {
        System.out.println("耗时 " + end() + " ms");
        start();
    }
}