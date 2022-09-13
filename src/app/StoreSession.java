package app;

import java.util.Scanner;
import entities.Catalogue;
import entities.Product;
import entities.ShoppingCart;
import utils.TextFileHandler;

public class StoreSession {

	private String username;
	private Catalogue catalogue;
	private ShoppingCart cart;
	private Scanner sc;

	public StoreSession(String username) {
		this.sc = new Scanner(System.in);
		this.username = username;
		this.cart = new ShoppingCart();
		this.catalogue = new Catalogue();
	}

	public void menuLoop() {
		Product selectedProduct = null;
		while (true) {
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
			switch (selection) {
			case ("a"):
				System.out.println("Digite o nome, ou index do produto desejado!");
				String searchedProduct = sc.nextLine();
				try {
					int index = Integer.parseInt(searchedProduct);
					selectedProduct = catalogue.search(index);
				} catch (NumberFormatException e) {
					selectedProduct = catalogue.search(searchedProduct);
				}
				if (selectedProduct == null) {
					System.out.println("Produto não encontrado!");
				}
				break;
			case ("b"):
				catalogue.list();
				break;
			case ("c"):
				if (selectedProduct != null) {
					try {
						System.out.printf("Digite a quantidade desejada (max:%d): ", selectedProduct.getQuantity());
						int quantity = Integer.parseInt(sc.nextLine());
						if (quantity > 0 && quantity <= selectedProduct.getQuantity()) {
							cart.add(selectedProduct, quantity);
						} else {
							System.out.println("Digite um número menor ou igual ao do estoque");
						}
					} catch (NumberFormatException e) {
						System.out.println("Digite um número valido");
					}
				} else {
					System.out.println("Selecione um produto na busca de produtos!");
				}
				selectedProduct = null;
				break;
			case ("d"):
				cart.list();
				if (cart.size() != 0) {
					System.out.println("Finalizar a compra? (y/n): ");
					String isPaying = sc.nextLine();
					if (isPaying.equals("y")) {
						generateClientLog();
						catalogue.update();
						cart.clear();
					}
				}
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

	private void generateClientLog() {
		TextFileHandler logs = new TextFileHandler("./data/clientLog");
		logs.append(String.format("%s-%.2f\n", username, cart.getTotalPrice()));
	}

}
