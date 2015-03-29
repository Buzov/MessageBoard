package ru.messageBoard.model.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для проверки валидности строковых значений используемых в программе
 * 
 * @author Артур Бузов
 *
 */
public class Validator {
	/**
	 * Шаблон для проверки имени пользователя
	 */
	private static Pattern patternForName = Pattern
			.compile("[A-Za-zа-яА-Я].[^\\t\\v\\r\\n\\f]{2,18}");
	/**
	 * Шаблон для проверки заголовка объявления
	 */
	private static Pattern patternForHead = Pattern
			.compile("[A-Za-zа-яА-Я0-9].[\\w|\\W]{8,28}");
	/**
	 * Шаблон для проверки текста объявления
	 */
	private static Pattern patternForText = Pattern
			.compile("[A-Za-zа-яА-Я0-9].[\\w|\\W]{18,398}");
	/**
	 * Сличитель используемый при проверки валидности строк
	 */
	private static Matcher matcher;

	/**
	 * Этот метод проверяет валидность имени пользователя
	 * 
	 * @param name
	 *            строковое значение имени пользователя
	 * @return если строка валида - то возвращает true, если нет - false
	 */
	public static boolean checkName(String name) {
		matcher = patternForName.matcher(name);
		return matcher.matches();
	}

	/**
	 * Этот метод проверят валидность заголовка объявления
	 * 
	 * @param head
	 *            строковое значение заголовка объявления
	 * @return если строка валида - то возвращает true, если нет - false
	 */
	public static boolean checkHead(String head) {
		matcher = patternForHead.matcher(head);
		return matcher.matches();
	}

	/**
	 * Этот метод проверяет валидность текста объявления
	 * 
	 * @param text
	 *            строковое значение текста объявления
	 * @return если строка валида - то возвращает true, если нет - false
	 */
	public static boolean checkText(String text) {
		matcher = patternForText.matcher(text);
		return matcher.matches();
	}

}
