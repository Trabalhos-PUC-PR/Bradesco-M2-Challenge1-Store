package entities;

public class Client implements Comparable<Client>{

	private double valueSpent;
	private String name;
	
	public Client(String data) {
		String[] split = data.split("-");
		this.name = split[0];
		this.valueSpent = Double.parseDouble(split[1]);
	}
	
	public double getValueSpent() {
		return valueSpent;
	}

	@Override
	public int compareTo(Client c) {
		if(c.getValueSpent() > valueSpent) {
			return 1;
		}
		if(c.getValueSpent() < valueSpent) {
			return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return String.format("%s - %.2f", name, valueSpent);
	}

	
	
}
