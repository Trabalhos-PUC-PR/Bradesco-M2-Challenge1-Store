package services;

import java.util.Scanner;

import entities.Catalogue;
import entities.Product;

public class StockManager {

	private Catalogue catalogue;
	private Scanner sc;

	public StockManager(Catalogue catalogue) {
		sc = new Scanner(System.in);
		this.catalogue = catalogue;
	}

	public void menuLoop() {
		int index = 0;
		Product p = null;
		while (true) {
			System.out.println();
			System.out.println("\t - Stock management -");
			System.out.println("a) Add product to catalogue");
			System.out.println("b) List catalogue");
			System.out.println("c) Remove product from index");
			System.out.println("d) Change product name from index");
			System.out.println("e) Change product quantity from index");
			System.out.println("f) Change product price from index");
			System.out.println("g) Exit Stock manager");
			System.out.println("Option:");
			String selection = sc.nextLine();
			switch (selection) {
			case ("a"):
				System.out.println("Type the new product name: ");
				String name = sc.nextLine();
				System.out.println("Type the new product price: ");
				double price = Double.parseDouble(sc.nextLine());
				System.out.println("Type the new product quantity: ");
				int quantity = Integer.parseInt(sc.nextLine());
				catalogue.refresh();
				catalogue.add(new Product(name, price, quantity));
				catalogue.update();
				break;
			case ("b"):
				catalogue.list();
				break;
			case ("c"):
				index = askIndex();
				catalogue.refresh();
				if (index > 0 || index <= catalogue.size()) {
					catalogue.remove(index - 1);
				}
				catalogue.update();
				break;
			case ("d"):
				catalogue.refresh();
				index = askIndex();
				p = catalogue.search(index);
				System.out.printf("Type the new name for the product (%s): ", p.getName());
				String newName = sc.nextLine();
				p.setName(newName);
				catalogue.update();
				break;
			case ("e"):
				catalogue.refresh();
				index = askIndex();
				p = catalogue.search(index);
				System.out.printf("Type the new quantity for the product (%s, %d): ", p.getName(), p.getQuantity());
				int newQuantity = Integer.parseInt(sc.nextLine());
				p.setQuantity(newQuantity);
				catalogue.update();
				break;
			case ("f"):
				catalogue.refresh();
				index = askIndex();
				p = catalogue.search(index);
				System.out.printf("Type the new price for the product (%s, %.2f): ", p.getName(), p.getPrice());
				double newPrice = Double.parseDouble(sc.nextLine());
				p.setPrice(newPrice);
				catalogue.update();
				break;
			case ("g"):
				return;
			}
		}
	}

	public int askIndex() {
		catalogue.list();
		System.out.println("Select the index: ");
		return Integer.parseInt(sc.nextLine());
	}

}
