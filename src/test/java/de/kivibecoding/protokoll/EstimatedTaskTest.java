package de.kivibecoding.protokoll;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

class EstimatedTaskTest {

    @Test
    void testRemainingTimeCalculation() throws InterruptedException {
        int totalSteps = 10;
        EstimatedTask task = new EstimatedTask(totalSteps);

        // Simuliere, dass der erste Schritt 100ms gedauert hat
        TimeUnit.MILLISECONDS.sleep(100);
        task.updateProgress(1);

        Duration remaining = task.getEstimatedRemainingTime();

        // Erwartet werden ca. 9 * 100ms = 900ms
        // Wir erlauben einen Spielraum für die Ausführungszeit
        long remainingMillis = remaining.toMillis();
        assertTrue(remainingMillis >= 800 && remainingMillis <= 1200,
            "Geschätzte Restzeit sollte um 900ms liegen, war aber: " + remainingMillis + "ms");
    }

    @Test
    void testProgressClamping() {
        EstimatedTask task = new EstimatedTask(5);
        task.updateProgress(10);
        assertEquals(5, task.getCurrentStep());
    }

    @Test
    void testInitialEstimation() {
        EstimatedTask task = new EstimatedTask(100);
        assertEquals(Duration.ZERO, task.getEstimatedRemainingTime());
    }
}
