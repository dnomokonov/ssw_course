import org.example.Main;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.concurrent.*;

public class ThreadTest {
    private static final int COUNT_THREAD = 3;
    private static final int PROGRESSBAR_LENGTH = 20;
    private static final int STEP_SLEEP = 500;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Testing update progressbar")
    void testUpdateProgressBar() {
        long threadId = 1;
        int threadNumber = 1;
        int progress = 10;

        Main.updateProgressBar(threadNumber, threadId, progress, PROGRESSBAR_LENGTH);

        String output = outputStream.toString().replaceAll("\\033\\[H\\033\\[2J", "").trim();
        String expectedPart = "Thread 1 (ID: 1) | 50% ==========          |";

        assertTrue(output.contains(expectedPart));
    }

    @Test
    @DisplayName("testing is correct progress")
    void testProgressIsCorrect() {
        int progress = 10;
        int expectedProgress = (progress * 100) / PROGRESSBAR_LENGTH; // (progress * 100) / total

        long threadId = 1;
        int threadNumber = 1;

        Main.updateProgressBar(threadNumber,threadId, progress, PROGRESSBAR_LENGTH);

        String[] output = outputStream.toString().replaceAll("\\033\\[H\\033\\[2J", "").split(" ");
        String currentPercent = output[5];

        String expectedProgressStr = String.valueOf(expectedProgress) + "%";

        assertEquals(expectedProgressStr, currentPercent);
    }

    @Test
    @DisplayName("testing threads")
    void testThread() {
        ExecutorService executor = Executors.newFixedThreadPool(COUNT_THREAD);
        CountDownLatch latch = new CountDownLatch(COUNT_THREAD);

        for (int i = 1; i <= COUNT_THREAD; i++) {
            int threadNumber = i;
            executor.submit(() -> {
                long threadId = Thread.currentThread().threadId();

                for (int j = 1; j <= PROGRESSBAR_LENGTH; j++) {
                    Main.updateProgressBar(threadNumber, threadId, j, PROGRESSBAR_LENGTH);
                    try {
                        Thread.sleep(STEP_SLEEP);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                latch.countDown();
            });
        }

        try {
            boolean completed = latch.await(STEP_SLEEP, TimeUnit.SECONDS);
            assertTrue(completed);
        } catch (InterruptedException e) {
            fail();
        }

        executor.shutdown();
    }
}