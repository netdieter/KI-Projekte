package de.kivibecoding.protokoll;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Simuliert einen Task mit 200 Schritten, wobei jeder Schritt 2-4 Sekunden dauert.
 */
public class TaskSimulation {

    public static void main(String[] args) {
        int steps = 200;
        EstimatedTask task = new EstimatedTask(steps);
        Random random = new Random();

        System.out.println("Starte Simulation eines Tasks mit " + steps + " Schritten...");

        for (int i = 1; i <= steps; i++) {
            // Simuliere Bearbeitungszeit zwischen 2 und 4 Sekunden
            int sleepTime = 2000 + random.nextInt(2001);
            try {
                TimeUnit.MILLISECONDS.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Simulation unterbrochen.");
                break;
            }

            // Fortschritt um einen Schritt aktualisieren
            task.updateProgress(1);
        }

        System.out.println("Simulation abgeschlossen.");
    }
}
