package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	private BufferedWriter fileWriterFactory(String path) {
		return fileWriterFactory(path, false);
	}

	private BufferedWriter fileWriterFactory(String path, boolean isAppending) {
		File file = new File(path);
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(file, isAppending));
			return bw;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Writes text at specified index, resets last location of nextLine function
	 * 
	 * @param newText the new text to be inserted at specified index
	 * @param index begins at 0
	 */
	public void write(String newText, int index) {
		List<String> updatedText = new ArrayList<String>();
		reader = fileReaderFactory(path);
		String oldText = nextLine();
		int count = 0;
		while (oldText != null) {
			if (count == index) {
				oldText = newText;
			}
			updatedText.add(oldText);
			oldText = nextLine();
			count++;
		}
		try {
			BufferedWriter writer = fileWriterFactory(path);
			for (String line : updatedText) {
				writer.write(line + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		reader = fileReaderFactory(path);
	}

	public void append(String text) {
		BufferedWriter writer = fileWriterFactory(path, true);
		try {
			writer.append(text);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Reads the next line of file
	 * 
	 * @return The next line from file, returns null at the end of the file
	 */
	public String nextLine() {
		String line = "";
		try {
			if ((line = reader.readLine()) != null) {
				return line;
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Reads line from specified index, sets nextLine to the searched index
	 * 
	 * @param index begins at line 0
	 * @return String from specified index, if index is invalid, returns null
	 */
	public String readLine(int index) {
		reader = fileReaderFactory(path);
		if (index >= getLineCount()) {
			return null;
		}
		String aux = nextLine();
		for (int i = 0; i < index; i++) {
			aux = nextLine();
		}
		return aux;
	}

	/**
	 * Gets line count from file, count starts at 1
	 * 
	 * @return number of lines
	 */
	public int getLineCount() {
		int lines = 0;
		reader = fileReaderFactory(path);
		try {
			while (reader.readLine() != null)
				lines++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

}
