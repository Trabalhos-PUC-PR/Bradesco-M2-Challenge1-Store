package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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

	private BufferedWriter fileWriterFactory(File file) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			return bw;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private BufferedWriter fileWriterFactory(String path) {
		File file = new File(path);
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(file, true));
			return bw;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private File createAuxFileAtPath() {
		String[] splitPath = path.split("/");
		splitPath[splitPath.length - 1] = "aux";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < splitPath.length; i++) {
			sb.append(splitPath[i]);
			if (i != splitPath.length - 1) {
				sb.append("/");
			}
		}
		String auxPath = sb.toString();
		File aux = new File(auxPath);
		try {
			if (!aux.createNewFile()) {
				throw new RuntimeException("Cannot create file aux [" + auxPath + "] !");
			} else {
//				System.out.println("File created succesfully!");
				return aux;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void write(String text, int index) {
		File auxFile = createAuxFileAtPath();
		BufferedWriter auxWriter = fileWriterFactory(auxFile);
		
		
		try {
			auxWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		auxFile.delete();
	}

	public void write(String text) {
		BufferedWriter writer = fileWriterFactory(path);
		try {
			writer.append(text);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Reads the next line of file, resets if
	 * 
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
