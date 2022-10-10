package entities.clientSorters;

import java.util.Comparator;
import entities.Client;

public class SorterByPrice implements Comparator<Client> {

	@Override
	public int compare(Client o1, Client o2) {
		if(o2.getValueSpent() > o1.getValueSpent()) {
			return 1;
		}
		if(o2.getValueSpent() < o1.getValueSpent()) {
			return -1;
		}
		return 0;
	}

}
