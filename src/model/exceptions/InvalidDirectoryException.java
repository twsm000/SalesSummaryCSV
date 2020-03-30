package model.exceptions;

public class InvalidDirectoryException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidDirectoryException(String message) {
	super(message);
    }
}
