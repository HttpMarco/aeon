package net.http.aeon.exceptions;

public final class NotImplementedYetException extends RuntimeException {

    public NotImplementedYetException(String message) {
        super(message);
    }

    public NotImplementedYetException() {
        super("This function is still being implemented.");
    }
}
