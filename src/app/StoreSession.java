package app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import entities.Catalogue;
import entities.Product;
import entities.ShoppingCart;

public class StoreSession {

	private String username;
	private Catalogue catalogue;
	private ShoppingCart cart;
	private Scanner sc;
	private String clientLogPath;

	public StoreSession(String username) {
		this.clientLogPath = getClientLogPath();
		this.sc = new Scanner(System.in);
		this.username = username;
		this.cart = new ShoppingCart();
		this.catalogue = new Catalogue();
	}

	public static String getClientLogPath() {
		return "./data/clientLog";
	}
	
	public void menuLoop() {
		Product selectedProduct = null;
		while (true) {
			System.out.println();
			System.out.printf("\t - AZAMON -\n");
			System.out.printf("a) Buscar produtos\n");
			System.out.printf("b) Listar produtos\n");
			System.out.printf("c) Adicionar ao carrinho\n");
			System.out.printf("d) Exibir carrinho\n");
			System.out.printf("e) Limpar carrinho\n");
			System.out.printf("x) Voltar ao menu principal\n");
			if (selectedProduct != null) {
				System.out.printf("\nProduto selecionado: %s\n\n", selectedProduct);
			}
			System.out.printf("Opção: ");
			String selection = sc.nextLine();
			System.out.println();
			switch (selection) {
			case ("a"):
				System.out.println("Digite o nome, ou index do produto desejado!");
				String query = sc.nextLine();
				selectedProduct = lookOnCatalogue(query);
				break;
			case ("b"):
				catalogue.list();
				break;
			case ("c"):
				addToCart(selectedProduct);
				selectedProduct = null;
				break;
			case ("d"):
				cart.list();
				if (cart.size() > 0)
					payment();
				break;
			case ("e"):
				cart.clear();
				catalogue.refresh();
				selectedProduct = null;
				break;
			case ("x"):
				return;
			}
		}
	}

	private Product lookOnCatalogue(String searchedProduct) {
		try {
			int index = Integer.parseInt(searchedProduct);
			return catalogue.search(index);
		} catch (NumberFormatException e) {
			return catalogue.search(searchedProduct);
		}
	}

	private void addToCart(Product product) {
		if (product != null) {
			try {
				System.out.printf("Digite a quantidade desejada (max:%d): ", product.getQuantity());
				int quantity = Integer.parseInt(sc.nextLine());
				if (quantity > 0 && quantity <= product.getQuantity()) {
					cart.add(product, quantity);
				} else {
					System.out.println("Digite um número menor ou igual ao do estoque");
				}
			} catch (NumberFormatException e) {
				System.out.println("Digite um número valido");
			}
		} else {
			System.out.println("Selecione um produto na busca de produtos!");
		}
	}

	private void payment() {
		System.out.println("Finalizar a compra? (y/n): ");
		String isPaying = sc.nextLine();
		if (isPaying.equals("y")) {
			generateClientLog();
			catalogue.update();
			cart.clear();
		}

	}

	private void generateClientLog() {
		try {
			File file = new File(clientLogPath);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.append(String.format("%s-%.2f\n", username, cart.getTotalPrice()));
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
