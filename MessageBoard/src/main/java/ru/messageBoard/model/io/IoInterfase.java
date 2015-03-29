package ru.messageBoard.model.io;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Используется для чтения и записи массива объектов
 * В качестве контейнера используется ArrayList
 * 
 * @author Артур Бузов
 *
 */

public interface IoInterfase<T> {
	/**
	 * Осуществляет запись массива объктов в текстовый файл
	 * 
	 * @param path путь к файлу
	 * @param array массив объектов
	 * @throws IOException при ошибке записи выбрасывает исключение
	 */
	void write(String path, ArrayList<T> array) throws IOException;

	/**
	 * Осуществляет чтение массива из текстового файла
	 * 
	 * @param path путь к файлу
	 * @return возвращает массив объектов прочитанных из текстового файла
	 * @throws IOException при ошибке записи выбрасывает исключение
	 */
	ArrayList<T> read(String path) throws IOException;

}
