package sample;

/**
 * The type Duplicate.
 * Übernahme der Klasse von Florian Eimann
 */
public class Duplicate extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Duplicate.
     */
    public Duplicate() {
        super();
    }

    /**
     * Instantiates a new Duplicate.
     *
     * @param message the message
     */
    public Duplicate(final String message) {
        super(message);
    }

    /**
     * Instantiates a new Duplicate.
     *
     * @param message the message
     * @param cause   the cause
     */
    public Duplicate(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Duplicate.
     *
     * @param cause the cause
     */
    public Duplicate(final Throwable cause) {
        super(cause);
    }
}
