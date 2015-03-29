package ru.messageBoard.model;

import java.io.Serializable;
import java.util.Date;

import ru.messageBoard.model.util.DateFormat;

/**
 * Этот клас используется для представления объявления
 * 
 * @author Артур Бузов
 *
 */
public class Declaration implements Serializable {

	private static final long serialVersionUID = 938674237944589105L;
	/**
	 * Имя автора объявления
	 */
	private String nameOfAuthor = null;
	/**
	 * Дата созданияобъявления
	 */
	private Date date = null;
	/**
	 * Рубрика объявления
	 */
	private String topic = null;
	/**
	 * Заголовок объявления
	 */
	private String head = null;
	/**
	 * Текст объявления
	 */
	private String text = null;


	/**
	 * Пустой конструктор необходим для сериализации в XML
	 */
	public Declaration() {
		date = new Date();
	}

	/**
	 * Конструктор для создания объекта объявления
	 * 
	 * @param nameOfAuthor
	 *            имя автора
	 * @param date
	 *            дата создания
	 * @param topic
	 *            рубрика объявления
	 * @param head
	 *            заголовок объявления
	 * @param text
	 *            текст объявления
	 */
	

	public Declaration(String nameOfAuthor, Date date, String topic, String head, String text) {
		
		this.nameOfAuthor = nameOfAuthor;
		this.date = date;
		this.topic = topic;
		this.head = head;
		this.text = text;
	}

	/**
	 * Метод для получения имени автора
	 * 
	 * @return имя автора
	 */
	public String getNameOfAuthor() {
		return nameOfAuthor;
	}

	/**
	 * Метод для установки имени автора
	 * 
	 * @param nameOfAuthor
	 *            имя автора
	 */
	public void setNameOfAuthor(String nameOfAuthor) {
		this.nameOfAuthor = nameOfAuthor;
	}

	/**
	 * Метод для получения даты публикации объявления
	 * 
	 * @return дата публикации объявления
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Метод для получения строкового представления даты
	 * 
	 * @return дата в виде строки
	 */
	public String toStringDate() {
		return DateFormat.format(date);
	}

	/**
	 * Метод для установки даты публикации объявления
	 * 
	 * @param date
	 *            дата публикации
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Метод для получения рубрики объявления
	 * 
	 * @return рубрика объявления
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * Метод для установки рубрики объявления
	 * 
	 * @param topic
	 *            рубрика объявления
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * Метод для получения заголовка объявления
	 * 
	 * @return заголовок объявления
	 */
	public String getHead() {
		return head;
	}

	/**
	 * Метод для установки заголовка объявления
	 * 
	 * @param head
	 *            заголовок объявления
	 */
	public void setHead(String head) {
		this.head = head;
	}

	/**
	 * Метод для получения текста объявления
	 * 
	 * @return возвращает текст обявления
	 */
	public String getText() {
		return text;
	}

	/**
	 * Метод для установки текста объявления
	 * 
	 * @param text
	 *            текст объявления
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Метод для преобразования в строку
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(nameOfAuthor);
		sb.append(" ");
		sb.append(toStringDate());
		sb.append(" ");
		sb.append(topic);
		sb.append(" ");
		sb.append(head);
		sb.append(" ");
		sb.append(text);
		sb.append("\n");
		return sb.toString();
	}

}
