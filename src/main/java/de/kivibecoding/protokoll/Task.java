package de.kivibecoding.protokoll;

import java.time.Instant;

/**
 * Basisklasse für einen Task.
 * Verwaltet die Gesamtzahl der Schritte, den aktuellen Fortschritt und den Startzeitpunkt.
 */
public abstract class Task {
    protected final int totalSteps;
    protected int currentStep;
    protected final Instant startTime;

    /**
     * Erstellt einen neuen Task mit der angegebenen Anzahl an Schritten.
     * @param totalSteps Die Gesamtzahl der Schritte des Tasks.
     */
    public Task(int totalSteps) {
        this.totalSteps = totalSteps;
        this.currentStep = 0;
        this.startTime = Instant.now();
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public Instant getStartTime() {
        return startTime;
    }

    /**
     * Aktualisiert den Fortschritt des Tasks.
     * @param steps Die Anzahl der zusätzlich abgeschlossenen Schritte.
     */
    public abstract void updateProgress(int steps);
}
