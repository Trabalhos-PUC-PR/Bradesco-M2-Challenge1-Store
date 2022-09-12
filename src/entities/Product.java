package entities;

public class Product {

	private String name;
	private double price;
	private int quantity;

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

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String toDatabase() {
		return quantity + "," + name + "," + price;
	}
	
	public double totalValue() {
		return quantity * price;
	}
	
	@Override
	public String toString() {
		return String.format("%s (%d) - R$:%.2f", name, quantity, price);
	}

	public String getSerial() {
		return String.format("%s - R$%.2f", name, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return other.getSerial().equals(getSerial());
	}

	
	
}
