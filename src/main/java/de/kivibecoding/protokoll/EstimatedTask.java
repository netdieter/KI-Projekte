package de.kivibecoding.protokoll;

import java.time.Duration;
import java.time.Instant;

/**
 * Eine Erweiterung der Task-Klasse, die die verbleibende Zeit basierend auf der
 * bisherigen Bearbeitungszeit berechnet und anzeigt.
 */
public class EstimatedTask extends Task {

    public EstimatedTask(int totalSteps) {
        super(totalSteps);
    }

    /**
     * Aktualisiert den Fortschritt und berechnet die Schätzung neu.
     * @param steps Die Anzahl der zusätzlich abgeschlossenen Schritte.
     */
    @Override
    public void updateProgress(int steps) {
        this.currentStep += steps;
        if (this.currentStep > totalSteps) {
            this.currentStep = totalSteps;
        }
        displayEstimation();
    }

    /**
     * Berechnet die geschätzte verbleibende Zeit und gibt sie auf der Konsole aus.
     */
    private void displayEstimation() {
        if (currentStep <= 0) {
            System.out.println("Fortschritt: 0% - Verbleibende Zeit: Unbekannt");
            return;
        }

        Duration elapsed = Duration.between(startTime, Instant.now());
        long elapsedMillis = elapsed.toMillis();

        double millisPerStep = (double) elapsedMillis / currentStep;
        int remainingSteps = totalSteps - currentStep;
        long estimatedRemainingMillis = (long) (remainingSteps * millisPerStep);

        Duration remaining = Duration.ofMillis(estimatedRemainingMillis);
        double percentage = (double) currentStep / totalSteps * 100;

        System.out.printf("Fortschritt: %.2f%% (%d/%d) - Vergangen: %02d:%02d:%02d - Verbleibend (ca.): %02d:%02d:%02d%n",
                percentage,
                currentStep,
                totalSteps,
                elapsed.toHoursPart(), elapsed.toMinutesPart(), elapsed.toSecondsPart(),
                remaining.toHoursPart(), remaining.toMinutesPart(), remaining.toSecondsPart());
    }

    /**
     * Gibt die aktuell geschätzte verbleibende Zeit zurück.
     * @return Die geschätzte verbleibende Dauer.
     */
    public Duration getEstimatedRemainingTime() {
        if (currentStep <= 0) {
            return Duration.ZERO;
        }
        Duration elapsed = Duration.between(startTime, Instant.now());
        double millisPerStep = (double) elapsed.toMillis() / currentStep;
        return Duration.ofMillis((long) ((totalSteps - currentStep) * millisPerStep));
    }
}
