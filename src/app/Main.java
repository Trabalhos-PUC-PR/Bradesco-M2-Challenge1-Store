package app;

import java.util.Scanner;
import services.UserManager;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.printf("Type your login: ");
		String login = sc.nextLine();
		System.out.printf("Type your password: ");
		String password = sc.nextLine();

		UserManager userMan = new UserManager();
		boolean isValidated = userMan.verifyUser(login, password);

		if (isValidated) {
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

	public static void menu(String username) {
		Scanner sc = new Scanner(System.in);
		boolean adminPerm = false;
		if (username.equals("admin"))
			adminPerm = true;
		while (true) {
			System.out.printf("Olá %s! O que você gostaria de fazer hoje?\n", username);
			System.out.printf("a) Fazer compras\n");
			System.out.printf("b) Trocar usuário\n");
			System.out.printf("c) Informações do sistema\n");
			if (adminPerm)
				System.out.printf("d)(ADM) Relatório de clientes\n");
			System.out.printf("x) Sair\n");
			String selection = sc.nextLine();
			switch (selection) {
			case "a":
				System.out.println("To be implemented yet!");
				break;
			case "b":
				main(null);
				return;
			case "c":
				printDetails();
				break;
			case "d":
				if (adminPerm)
					System.out.println("To be implemented yet!");
				break;
			case "x":
				System.out.println("Volte sempre!");
				return;
			}
		}
	}
	
	public static void printDetails() {
		System.out.println("\nShop System\n v0.4!");
		System.out.println("Made by: André Kovalski\n");
	}

}
