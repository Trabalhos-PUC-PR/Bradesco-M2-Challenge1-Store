package app;

import java.util.Scanner;

import services.UserManager;
import utils.TextFileHandler;

public class Main {

	public static void main(String[] args) {
		TextFileHandler tfh = new TextFileHandler("./data/users");
		Scanner sc = new Scanner(System.in);
		
		System.out.printf("Type your login: ");
		String login = sc.nextLine();
		System.out.printf("Type your password: ");
		String password = sc.nextLine();
		
		UserManager validator = new UserManager();
		boolean isValidated = validator.verifyUser(login, password);

		if(isValidated) {
			System.out.println(tfh.nextLine());
			tfh.write("a",2);
		} else {
			System.out.println("Go out");
		}
		
		sc.close();
	}

}
