package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserManager {

	private BufferedReader br;

	public UserManager() {
		this.br = createBufferedReader();
	}

	private BufferedReader createBufferedReader() {
		try {
			File file = new File(getUserDatabasePath());
			file.createNewFile();
			return new BufferedReader(new FileReader(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getUserDatabasePath() {
		return "./data/users";
	}

	public boolean addUser(String user, String pass) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(getUserDatabasePath()), true));
			if (user.isBlank() || pass.isBlank()) {
				return false;
			}
			if (loginExists(user)) {
				return false;
			}
			bw.append(user + "," + pass + "\n");
			bw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean verifyUser(String login, String password) {
		try {
			this.br = createBufferedReader();
			String nextUser = br.readLine();
			login = login.toLowerCase();

			while (nextUser != null) {
				String[] fields = nextUser.split(",");
				if (login.equals(fields[0].toLowerCase()) && password.equals(fields[1])) {
					return true;
				}
				nextUser = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean loginExists(String login) {
		return (getLoginFromDatabase(login) != null);
	}
	
	public String getLoginFromDatabase(String login) {
		try {
			this.br = createBufferedReader();
			String nextUser = br.readLine();
			login = login.toLowerCase();
			while (nextUser != null) {
				String[] fields = nextUser.split(",");
				if (login.equals(fields[0].toLowerCase())) {
					return fields[0];
				}
				nextUser = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
