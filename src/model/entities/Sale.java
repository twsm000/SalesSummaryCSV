package model.entities;

public class Sale {
    private Product product;
    private Integer quantity;

    public Sale(Product product, Integer quantity) {
	this.product = product;
	this.quantity = quantity;
    }
    
    public String productName() {
	return product.getName();
    }
    
    public Double total() {
	return product.getValue() * quantity;
    }
}
