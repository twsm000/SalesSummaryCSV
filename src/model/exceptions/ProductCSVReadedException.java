package model.exceptions;

public class ProductCSVReadedException extends Exception {
    
    private static final long serialVersionUID = 1L;

    public ProductCSVReadedException(String message) {
	super(message);
    }
}
