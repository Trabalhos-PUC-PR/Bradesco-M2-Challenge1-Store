package entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Catalogue {

	private List<Product> catalogue;
	private BufferedReader br;
	private String cataloguePath;
	
	public Catalogue() {
		this.catalogue = new ArrayList<Product>();
		this.cataloguePath = "./data/catalogue";
		this.br = readerFactory();
		refresh();
	}

	private BufferedReader readerFactory() {
		try {
			File file = new File(cataloguePath);
			file.createNewFile();
			return br = new BufferedReader(new FileReader(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void refresh() {
		try {
			this.catalogue = new ArrayList<Product>();
			this.br = readerFactory();
			String productData = br.readLine();
			while (productData != null) {
				String[] splitData = productData.split(",");
				int quantity = Integer.parseInt(splitData[0]);
				String name = splitData[1];
				double price = Double.parseDouble(splitData[2]);
				catalogue.add(new Product(name, price, quantity));
				productData = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Product search(String name) {
		for (Product p : catalogue) {
			if (p.getName().toLowerCase().equals(name.toLowerCase())) {
				return p;
			}
		}
		return null;
	}

	public Product search(int index) {
		if (index > catalogue.size() || index <= 0) {
			return null;
		}
		index--;
		return catalogue.get(index);
	}

	public void list() {
		int cont = 1;
		for (Product p : catalogue) {
			System.out.printf("%02d : %s\n", cont, p);
			cont++;
		}
	}

	public void add(Product p) {
		catalogue.add(p);
	}
	
	public void remove(int index) {
		catalogue.remove(index);
	}
	
	public void update() {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(new File(cataloguePath)));
			for (Product product : catalogue) {
				bw.write(product.toStringDatabase() + "\n");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int size() {
		return catalogue.size();
	}
	
}
