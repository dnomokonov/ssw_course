package org.example;

import java.util.concurrent.*;

public class Main {
    private static final int COUNT_THREAD = 3;
    private static final int PROGRESSBAR_LENGTH = 20;
    private static final int STEP_SLEEP = 500;

    private static final String[] progressBars = new String[COUNT_THREAD];
    private static final Object lock = new Object();

    public static void updateProgressBar(int threadNumber, long threadId, int done, int total) {
        int progress = (done * PROGRESSBAR_LENGTH) / total;

        StringBuilder barBuilder = new StringBuilder(PROGRESSBAR_LENGTH);

        String icon = "=";
        for (int i = 0; i < progress; i++) {
            barBuilder.append(icon);
        }
        for (int i = progress; i < PROGRESSBAR_LENGTH; i++) {
            barBuilder.append(' ');
        }

        synchronized (lock) {
            progressBars[threadNumber - 1] = String.format("Thread %d (ID: %d) |%3d%% %s|", threadNumber, threadId, (done * 100) / total, barBuilder.toString());

            System.out.print("\033[H\033[2J");
            for (String line : progressBars) {
                if (line != null) System.out.println(line);
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(COUNT_THREAD);
        CountDownLatch latch = new CountDownLatch(COUNT_THREAD);

        for (int i = 1; i <= COUNT_THREAD; i++) {
            int threadNumber = i;
            executor.submit(() -> {
                long threadId = Thread.currentThread().threadId();

                long startTimer = System.currentTimeMillis();

                try {
                    for (int j = 1; j <= PROGRESSBAR_LENGTH; j++) {
                        updateProgressBar(threadNumber, threadId, j, PROGRESSBAR_LENGTH);
                        Thread.sleep(STEP_SLEEP);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                long stopTimer = System.currentTimeMillis();

                synchronized (System.out) {
                    System.out.printf("Thread %d (ID: %d) finished at %d ms\n",
                            threadNumber, threadId, (stopTimer - startTimer));
                }

                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        
        executor.shutdown();
    }
}