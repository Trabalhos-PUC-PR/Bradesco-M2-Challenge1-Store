package entities;

public class Product {

	private String name;
	private double price;
	private int quantity;

	public Product(Product p) {
		this.name = p.getName();
		this.price = p.getPrice();
		this.quantity = p.getQuantity();
	}
	
	public Product(String name, double value, int quantity) {
		this.name = name;
		this.price = value;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String toDatabase() {
		return quantity + "," + name + "," + price;
	}
	
	public double totalValue() {
		return quantity * price;
	}
	
	public String toStringDatabase() {
		return String.format("%d,%s,%.2f", quantity, name, price);
	}
	
	@Override
	public String toString() {
		return String.format("%s (%d) - R$:%.2f", name, quantity, price);
	}
	
}
