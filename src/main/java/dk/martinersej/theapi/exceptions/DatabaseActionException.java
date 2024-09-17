package dk.martinersej.theapi.exceptions;

public class DatabaseActionException extends RuntimeException {

    public DatabaseActionException(final Throwable cause) {
        super(cause);
    }

    public static DatabaseActionException of(final Throwable cause) {
        return new DatabaseActionException(cause);
    }
}