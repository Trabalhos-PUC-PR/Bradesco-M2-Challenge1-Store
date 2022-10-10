package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import entities.Client;
import entities.clientSorters.SorterByDate;
import entities.clientSorters.SorterByPrice;
import services.UserManager;

public class Main {

	/* TODO:
	 * 1 Include purchase give option to order by date
	 * 2 CPF validation
	 * 4 Print cleanUp
	 * 5 Catalogue filters (with streams)
	 */
	
	private static Scanner sc;

	public static void main(String[] args) {
		new File(getDataFolder()).mkdir();
		sc = new Scanner(System.in);
		
		System.out.println(LocalDate.now());
		
		System.out.printf("Type your login: ");
		String login = sc.nextLine();
		System.out.printf("Type your password: ");
		String password = sc.nextLine();

		UserManager userMan = new UserManager();
		boolean isValidated = userMan.verifyUser(login, password);

		if (isValidated) {
			login = userMan.getLoginFromDatabase(login);
			System.out.println();
			System.out.println("Welcome!");
			menu(login);
		} else {
			if (userMan.loginExists(login)) {
				System.out.println("Wrong password");
			} else {
				System.out.println("Account not found! \nWould you like to register a new account? (y/n): ");
				String option = sc.nextLine();
				if (option.equals("y")) {
					if (userMan.addUser(login, password)) {
						System.out.println("User registered succesfully!");
					} else {
						System.out.println("There was an error registering a new user!");
					}
				}
			}
		}
		sc.close();
	}

	public static String getDataFolder() {
		return "./data";
	}
	
	public static void menu(String username) {
		boolean adminPerm = false;
		if (username.equals("admin"))
			adminPerm = true;
		while (true) {
			System.out.println();
			System.out.printf("Hello %s! What would you like to do today?\n", username);
			System.out.printf("a) Let's go shopping!\n");
			System.out.printf("b) Switch username\n");
			System.out.printf("c) System information\n");
			if (adminPerm)
				System.out.printf("d)(ADM) Print client logs sorted by most expensive purchase\n");
			System.out.printf("x) Exit\n");
			System.out.printf("Option: ");
			String selection = sc.nextLine();

			System.out.println();
			switch (selection) {
			case "a":
				StoreSession session = new StoreSession(username);
				session.menuLoop();
				break;
			case "b":
				main(null);
				return;
			case "c":
				printDetails();
				break;
			case "d":
				if (adminPerm) {
					String text = "";
					while(!text.equals("p") && !text.equals("d")) {
						System.out.print("Order by date or by price? (d/p): ");
						text = sc.nextLine();
					}
					if(text.equals("p")) {
						printSortedClientLog(new SorterByPrice());
					} else {
						printSortedClientLog(new SorterByDate());
					}
				}
				break;
			case "x":
				System.out.println("Come back again!");
				sc.close();
				return;
			}
		}
	}

	public static void printSortedClientLog(Comparator<Client> comp) {
		File file = new File(StoreSession.getClientLogPath());
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			file.createNewFile();
			System.out.println("Sorted Client logs:");
			String logLine = br.readLine();
			List<Client> clients = new ArrayList<Client>();
			while (logLine != null) {
				clients.add(new Client(logLine));
				logLine = br.readLine();
			}
			br.close();
			Collections.sort(clients, comp);
			for(Client c : clients) {
				System.out.println(c);
			}
			System.out.println("= ==+== =");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void printDetails() {
		System.out.println("\nShop System\n v1.0!");
		System.out.println("Made by: André Kovalski\n");
	}

}
