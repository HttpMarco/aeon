package net.http.aeon.exceptions;

public final class NotImplementedException extends RuntimeException {

    public NotImplementedException(String message) {
        super(message);
    }

    public NotImplementedException() {
        super("This function is still being implemented.");
    }
}
