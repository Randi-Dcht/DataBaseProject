package be.ac.umons.projetBDD.Commands;

/**
 * This interface shows how a command asking for confirmation must look like.
 * @author Guillaume Cardoen (Student in computer sciences UMONS)
 */
public interface Confirmable {
    /**
     * Executed if the confirmation is positive.
     */
    void runAnyway();
}
