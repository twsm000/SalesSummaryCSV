package model.entities;

public class Product {

    private String name;
    private Double value;

    public Product(String name, Double value) {
	this.name = name;
	this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
