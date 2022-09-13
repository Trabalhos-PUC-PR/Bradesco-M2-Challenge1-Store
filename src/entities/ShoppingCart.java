package entities;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

	private List<Product> shoppingCart;
	
	public ShoppingCart() {
		shoppingCart = new ArrayList<Product>();
	}
	
	public void add(Product selectedProduct, int selectedQuantity) {
		try {
			if (selectedProduct != null) {
				if (selectedQuantity > 0 && selectedQuantity <= selectedProduct.getQuantity()) {
					Product aux = new Product(selectedProduct);
					aux.setQuantity(selectedQuantity);
					selectedProduct.setQuantity(selectedProduct.getQuantity()-selectedQuantity);
					shoppingCart.add(aux);
				} else {
					System.out.println("Digite um número menor ou igual ao do estoque");
				}
			} else {
				System.out.println("Selecione um produto na busca de produtos!");
			}
		} catch (NumberFormatException e) {
			System.out.println("Digite um número valido");
		}
	}
	
	public double getTotalPrice() {
		double totalValue = 0;
		for (Product p : shoppingCart) {
			totalValue += p.totalValue();
		}
		return totalValue;
	}
	
	public void list() {
		System.out.printf("Carrinho de compras (%d): \n", shoppingCart.size());
		double totalValue = 0;
		for (Product p : shoppingCart) {
			System.out.printf("%-25s + %.2f\n", (p.getName() + "(" + p.getQuantity() + ")"), p.totalValue());
			totalValue += p.totalValue();
		}
		System.out.printf("%-25s - %.2f\n", "TOTAL PRICE", totalValue);
	}
	
	public int size() {
		return shoppingCart.size();
	}
	
	public void clear() {
		this.shoppingCart = new ArrayList<Product>();
	}
	
}
