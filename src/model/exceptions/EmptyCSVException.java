package model.exceptions;

public class EmptyCSVException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public EmptyCSVException(String message) {
        super(message);
    }
}
