package app;

import java.util.Scanner;

import services.Auth;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.printf("Type your login: ");
		String login = sc.nextLine();
		System.out.printf("Type your password: ");
		String password = sc.nextLine();
		
		Auth validator = new Auth();
		boolean isValidated = validator.verifyUser(login, password);

		if(isValidated) {
			System.out.println("Welcome");
		} else {
			
		}
		
		sc.close();
	}

}
