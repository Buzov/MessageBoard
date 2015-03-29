package ru.messageBoard.model.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Этот класс предоставляет возможность преобразовать значение даты в строковое
 * значение по указанному шаблону
 * 
 * @author Артур Бузов
 *
 */
public class DateFormat {
	/**
	 * Шаблон для форматировани даты
	 */
	public static final String DATE_FORMAT_NOW = "yyyy.MM.dd hh:mm:ss";
	/**
	 * Объект для форматирования даты
	 */
	public static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

	/**
	 * Метод для тестового запуска
	 */
	public static void main(String[] args) {
		System.out.println(format(new Date()));
	}

	/**
	 * Этот метод позволяет преобразовать значение даты в строку по заданному
	 * шаблону
	 * 
	 * @param date
	 *            принимает объект типа Date
	 * @return возвращает дату в строковом представлении после форматирования
	 */
	public static String format(Date date) {
		return sdf.format(date.getTime());
	}
}
