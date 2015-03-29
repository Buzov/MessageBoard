package ru.messageBoard.model.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Класс для чтения текстового файла, содержащий перечень рубрик объявлений
 * 
 * @author Артур Бузов
 *
 */
public class ReadTopic {

	private static Scanner in;
	
	/**
	 * Метод для текстового запуска
	 */
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(read("./src/main/java/topic.properties"));
	}

	/**
	 * Метод для получения массива строк из внешнего файла
	 * 
	 * @param path путь к файлу
	 * @return массив строк
	 * @throws FileNotFoundException это исключение будет брошено если файл не найден
	 */
	public static String[] read(String path) throws FileNotFoundException {
		in = new Scanner(new FileReader(path));
		ArrayList<String> listString = new ArrayList<String>(50);
		String temp;
		while (in.hasNext()) {
			temp = in.nextLine();
			listString.add(temp);
		}
		return listString.toArray(new String[listString.size()]);
	}
}
