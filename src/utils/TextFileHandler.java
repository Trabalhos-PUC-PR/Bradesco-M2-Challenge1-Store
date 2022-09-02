package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TextFileHandler {

	String path;
	BufferedReader reader;
	
	public TextFileHandler(String path) {
		this.path = path;
		this.reader = fileReaderFactory(path);
	}
	
	private BufferedReader fileReaderFactory(String path) {
		File file = new File(path);
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			return br;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Reads the next line of file
	 * @return The next line from file, returns null at the end of the file
	 */
	public String nextLine() {
		String line = "";
        try {
			if ((line = reader.readLine()) != null) {
				return line;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	/**
	 * Reads line from specified index
	 * @param index begins at line 0
	 * @return String from specified index, if index is invalid, returns null
	 */
	public String readLine(int index) {
		if(index >= getLineCount()) {
			return null;
		}
		reader = fileReaderFactory(path);
		String aux = nextLine();
        for(int i = 0 ; i < index; i++) {
        	aux = nextLine();
        }
        return aux;
	}
	
	/**
	 * Gets line count from file, count starts at 1
	 * @return number of lines
	 */
	public int getLineCount() {
		int lines = 0;
		try {
			while (reader.readLine() != null) lines++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
}
