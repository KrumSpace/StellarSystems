package utils;

import org.jetbrains.annotations.NotNull;

/**
 * An exception to be thrown when an unfinished feature is being used.
 * Used for indicating unfinished features.
 */
public class UnfinishedException extends Exception {
    private static final @NotNull String DEFAULT_MESSAGE = "Exception - feature not finished!";

    /**
     * Creates a new UnfinishedException with a default message.
     */
    public UnfinishedException() {
        super(DEFAULT_MESSAGE);
    }
}