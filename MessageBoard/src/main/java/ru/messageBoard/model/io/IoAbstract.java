package ru.messageBoard.model.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class IoAbstract<T> implements IoInterfase<T> {

	File file = null;

	protected void checkPath(String path) throws IOException {
		file = new File(path);
		if (file.exists()) {

		} else {
			// file.createNewFile();
			if (file.mkdirs()) {

			} else {
				throw new IOException();
			}
		}
	}

	/**
	 * Метод позволяет получить содержимое текстового файла в виде строки
	 * 
	 * @param path
	 *            путь к текстовому файлу
	 * @return возвращает строку хранимую в файле
	 * @throws IOException
	 */
	protected String readStringFromFile(String path) throws IOException {
		String string = null;
		StringBuilder sb = new StringBuilder();
		File file = new File(path);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			while ((string = br.readLine()) != null) {
				sb.append(string);
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {

				}
			}
		}

		return sb.toString();
	}

	/**
	 * Метод позволяет записать строку в текстовый файл
	 * 
	 * @param path
	 *            путь к файлу
	 * @param s
	 *            строка, которую необходимо записать в файл
	 */
	protected void writeToFile(String path, String s) {
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(path)); //другой способ
			/*bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "UTF-8"));*/
			bw.write(s);
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {

				}
			}
		}
	}

}
