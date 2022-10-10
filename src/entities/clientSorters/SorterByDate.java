package entities.clientSorters;

import java.util.Comparator;

import entities.Client;

public class SorterByDate implements Comparator<Client> {

	@Override
	public int compare(Client o1, Client o2) {
		if(o1.getPurchaseDate().isAfter(o2.getPurchaseDate())) {
			return 1;
		}
		if(o1.getPurchaseDate().isBefore(o2.getPurchaseDate())) {
			return -1;
		}
		return 0;
	}

}
