package services;

import utils.TextFileHandler;

public class UserManager {

	TextFileHandler userDatabase;

	public UserManager() {
		this.userDatabase = new TextFileHandler("./data/users");
	}

	public boolean addUser(String user, String pass) {
		if (user.isBlank() || pass.isBlank()) {
			return false;
		}
		if (loginExists(user)) {
			return false;
		}
		userDatabase.append(user + "," + pass + "\n");
		return true;
	}

	public boolean verifyUser(String login, String password) {
		String nextUser = userDatabase.nextLine();

		while (nextUser != null) {
			String[] fields = nextUser.split(",");
			if (login.equals(fields[0]) && password.equals(fields[1])) {
				return true;
			}
			nextUser = userDatabase.nextLine();
		}
		return false;
	}

	public boolean loginExists(String login) {
		String nextUser = userDatabase.readLine(0);
		while (nextUser != null) {
			String[] fields = nextUser.split(",");
			if (login.equals(fields[0])) {
				return true;
			}
			nextUser = userDatabase.nextLine();
		}
		return false;
	}

}
