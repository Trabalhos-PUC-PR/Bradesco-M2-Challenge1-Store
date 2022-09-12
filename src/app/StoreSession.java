package app;

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

	public StoreSession(String username) {
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
		Scanner sc = new Scanner(System.in);
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
				try {
					if (selectedProduct != null) {
						System.out.printf("Digite a quantidade desejada (max:%d): ", selectedProduct.getQuantity());
						int quantity = Integer.parseInt(sc.nextLine());
						adicionarAoCarrinho(selectedProduct, quantity);
						selectedProduct = null;
					} else {
						System.out.println("Selecione um produto na busca de produtos!");
					}
				} catch (NumberFormatException e) {
					System.out.println("Digite um número valido");
				}
				break;
			case ("d"):
				listarCarrinho();
				System.out.println("Finalizar a compra? (y/n): ");
				String isPaying = sc.nextLine();
				if(isPaying.equals("y")) {
					generateClientLog();
					shoppingCart = new ArrayList<Product>();
				}
				break;
			case ("x"):
				return;
			}
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
			System.out.printf("%-25s + %.2f\n", p.getName(), p.totalValue());
			totalValue += p.totalValue();
		}
		System.out.printf("%25s - %.2f\n", "TOTAL PRICE", totalValue);
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

	private void adicionarAoCarrinho(Product product, int quantity) {
		if (quantity > 0 && quantity <= product.getQuantity()) {
			product.setQuantity(quantity);
			shoppingCart.add(product);
		} else {
			System.out.println("Digite um número menor ou igual ao do estoque");
		}
	}

	private void generateClientLog() {
		TextFileHandler logs = new TextFileHandler("./data/clientLog");
		logs.append(username+cartValue()+"\n");
	}
	
//	Adicionar o produto ao carrinho: Adiciona o produto ao carrinho. O cliente deve ser questionado sobre quantas unidades devem ser adicionadas. Ele não poderá adicionar mais produtos do que há no estoque.
//	Exibir carrinho: Exibe os produtos no carrinho, mostrando inclusive o total da compra. Também dá as opções de retornar as compras ou de finalizar a compra. Se a compra for finalizada, será salva no histórico do cliente.
//	Voltar ao menu principal
}
