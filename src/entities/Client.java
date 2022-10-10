package entities;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Client{

	private double valueSpent;
	private String name;
	private LocalDate purchaseDate;
	
	public Client(String data) {
		String[] split = data.split("_");
		this.name = split[0];
		this.valueSpent = Double.parseDouble(split[1]);
		List<Integer> splitDate = Stream.of(split[2].split("-"))
				.map(value -> Integer.parseInt(value))
				.collect(Collectors.toList());
		this.purchaseDate = LocalDate.of(splitDate.get(0), splitDate.get(1), splitDate.get(2));
	}
	
	public double getValueSpent() {
		return valueSpent;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}
	
	@Override
	public String toString() {
		return String.format("%s - %.2f - %s", name, valueSpent, purchaseDate);
	}

	
	
}
