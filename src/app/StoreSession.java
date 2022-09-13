package app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entities.Product;
import utils.TextFileHandler;

public class StoreSession {

	private String username;
	private List<Product> catalogue;
	private List<Product> shoppingCart;
	private TextFileHandler productDatabase;
	private Scanner sc;

	public StoreSession(String username) {
		sc = new Scanner(System.in);
		this.username = username;
		shoppingCart = new ArrayList<Product>();
		catalogue = new ArrayList<Product>();
		productDatabase = new TextFileHandler("./data/products");

		String databaseProduct = productDatabase.nextLine();
		while (databaseProduct != null) {
			String[] splitData = databaseProduct.split(",");
			int quantity = Integer.parseInt(splitData[0]);
			String name = splitData[1];
			double price = Double.parseDouble(splitData[2]);
			catalogue.add(new Product(name, price, quantity));
			databaseProduct = productDatabase.nextLine();
		}
	}

	public void menuLoop() {
		Product selectedProduct = null;
		while (true) {
			System.out.printf("\t - AZAMON -\n");
			System.out.printf("a) Buscar produtos\n");
			System.out.printf("b) Listar produtos\n");
			System.out.printf("c) Adicionar ao carrinho\n");
			System.out.printf("d) Exibir carrinho\n");
			System.out.printf("x) Voltar ao menu principal\n");
			if (selectedProduct != null) {
				System.out.printf("\nProduto selecionado: %s\n\n", selectedProduct);
			}
			System.out.printf("Opção: ");
			String selection = sc.nextLine();
			switch (selection) {
			case ("a"):
				System.out.println("Digite o nome, ou index do produto desejado!");
				String searchedProduct = sc.nextLine();
				try {
					int index = Integer.parseInt(searchedProduct);
					selectedProduct = buscar(index);
				} catch (NumberFormatException e) {
					selectedProduct = buscar(searchedProduct.toLowerCase());
				}
				if (selectedProduct == null) {
					System.out.println("Produto não encontrado!");
				}
				break;
			case ("b"):
				listar();
				break;
			case ("c"):
				adicionarAoCarrinho(selectedProduct);
				selectedProduct = null;
				break;
			case ("d"):
				listarCarrinho();
				if (shoppingCart.size() != 0) {
					System.out.println("Finalizar a compra? (y/n): ");
					String isPaying = sc.nextLine();
					if (isPaying.equals("y")) {
						generateClientLog();
						try {
							BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./data/products")));
							for(Product p : catalogue) {
								bw.write(p.toStringDatabase()+"\n");
							}
							bw.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						shoppingCart = new ArrayList<Product>();
					}
				}
				break;
			case ("x"):
				return;
			}
		}
	}

	private void adicionarAoCarrinho(Product selectedProduct) {
		try {
			if (selectedProduct != null) {
				System.out.printf("Digite a quantidade desejada (max:%d): ", selectedProduct.getQuantity());
				int selectedQuantity = Integer.parseInt(sc.nextLine());
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
	
	private double cartValue() {
		double totalValue = 0;
		for (Product p : shoppingCart) {
			totalValue += p.totalValue();
		}
		return totalValue;
	}

	private void listarCarrinho() {
		System.out.printf("Carrinho de compras (%d): \n", shoppingCart.size());
		double totalValue = 0;
		for (Product p : shoppingCart) {
			System.out.printf("%-25s + %.2f\n", (p.getName() + "(" + p.getQuantity() + ")"), p.totalValue());
			totalValue += p.totalValue();
		}
		System.out.printf("%-25s - %.2f\n", "TOTAL PRICE", totalValue);
	}

	public Product buscar(String name) {
		for (Product p : catalogue) {
			if (p.getName().toLowerCase().equals(name)) {
				return p;
			}
		}
		return null;
	}

	public Product buscar(int index) {
		if (index > catalogue.size() || index <= 0) {
			return null;
		}
		index--;
		return catalogue.get(index);
	}

	public void listar() {
		int cont = 1;
		for (Product p : catalogue) {
			System.out.printf("%02d : %s\n", cont, p);
			cont++;
		}
	} 

	private void generateClientLog() {
		TextFileHandler logs = new TextFileHandler("./data/clientLog");
		logs.append(String.format("%s-%.2f\n", username, cartValue()));
	}
	
}
