class CountdownTimer extends Thread {
    private String timerName;
    private int seconds;

    CountdownTimer(String timerName, int seconds) {
        this.timerName = timerName;
        this.seconds = seconds;
    }

    public void run() {
        try {
            for (int i = seconds; i > 0; i--) {
                System.out.println(timerName + " - " + i + " seconds remaining");
                Thread.sleep(1000);  // wait 1 second
            }
            System.out.println(timerName + "DONE!");
        } catch (InterruptedException e) {
            System.out.println(timerName + "Interrupted!");
        }
    }
}

public class CountdownSimulation {
    public static void main(String[] args) {
        // Two timers running in parallel
        CountdownTimer timer1 = new CountdownTimer("Timer 1", 5);
        CountdownTimer timer2 = new CountdownTimer("Timer 2", 3);

        timer1.start();
        timer2.start();

        System.out.println(" Both timers started...");
    }
}
