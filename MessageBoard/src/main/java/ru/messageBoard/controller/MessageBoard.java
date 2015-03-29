package ru.messageBoard.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.messageBoard.model.Declaration;
import ru.messageBoard.model.TypeSort;
import ru.messageBoard.model.exception.NullException;
import ru.messageBoard.model.io.IoInterfase;
import ru.messageBoard.model.io.IoXmlDeclaration;
import ru.messageBoard.model.util.DateFormat;

/**
 * Класс-контейнер для объектов объявлений
 * 
 * @author Артур Бузов
 *
 */

public class MessageBoard implements Serializable {

	private static final long serialVersionUID = 4433147861334322335L;
	/*
	 * Путь к файлу настроек spring
	
	private static final String pathToBean = "./bean.xml";
	 */
	/**
	 * Массив объктов объявлений
	 */
	private ArrayList<Declaration> declarations = new ArrayList<Declaration>();
	/*
	 * объкт spring задания типа текстовых файлов, с которыми будем работать
	 
	private static ApplicationContext actx = new ClassPathXmlApplicationContext(pathToBean);
	*/
	/**
	 * Фабрика для чтения и записи массива объявлений
	 */
	private IoInterfase<Declaration> io = new IoXmlDeclaration();

	/**
	 * Метод для тестового запуска
	 */
	public static void main(String[] args) {
		MessageBoard list = new MessageBoard();
		list.addDeclaration(new Declaration("Артур", new Date(), "продажа",
				"Куплю", "Тестовый текст"));
		list.addDeclaration(new Declaration("Артур", new Date(), "покупка",
				"Продам", "Тестовый текст"));
		list.addDeclaration(new Declaration("Дмитрий", new Date(), "аренда",
				"Продам", "Тестовый текст"));
		list.addDeclaration(new Declaration("Кирил", new Date(), "продажа",
				"Куплю", "Тестовый текст"));
		System.out.println("------------------");
		list.print();
		try {
			list.write("./serialization.json2");
			list.read("./serialization.json2");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		list.print();
	}

	public MessageBoard() {
		/* получение бина реализующего чтение и запись файлов
		io = (IoInterfase) actx.getBean("print");
		*/
	}
	
	/**
	 * Инициализация содержимого, необходим при ошибки считывания из файла
	 * 
	 * @param name имя пользователя
	 */
	public void initialize(String name) {
		addDeclaration(new Declaration(name, new Date(), "", "", ""));
	}

	/**
	 * Проверка заполненности списка объявлений
	 * 
	 * @return true если массив пустой, false tckb tcnm хотябы один элемент в списке
	 */
	public boolean isEmpty() {
		if (size() == 0) {
			return true;
		}
		return false;
	}

	
	
	/**
	 * Поиск объявления по индексу
	 * 
	 * @param i индекс объявления в массиве
	 * @return объект объявления
	 * @throws NullException если индекс выходит за диапазон, то выбрасывается исключение
	 */
	public Declaration getDeclaration(int i) throws NullException {
		checkIndex(i);
		return declarations.get(i);
	}

	/**
	 * Установка объявления по индексу
	 * 
	 * @param i индекс объявления в массиве
	 * @param declaration объект объявления
	 * @throws NullException если индекс выходит за диапазон, то выбрасывается исключение
	 */
	public void setDeclaration(int i, Declaration declaration) throws NullException {
		checkIndex(i);
		declarations.set(i, declaration);
	}

	/**
	 * Возращает размер контейнера
	 * 
	 * @return количество элементов в контейнере
	 */
	public int size() {
		return declarations.size();
	}

	/**
	 * Добавляет объявление
	 * 
	 * @param declaration объект объявления
	 */
	public void addDeclaration(Declaration declaration) {
		declarations.add(declaration);
	}

	/**
	 * Удаление объявления по индексу
	 * 
	 * @param i индекс объявления
	 * @throws NullException если индекс выходит за диапазон, то выбрасывается исключение
	 */
	public void removeDeclaration(int i) throws NullException {
		checkIndex(i);
		declarations.remove(i);
	}
	
	private void checkIndex(int i) {
		if (i < 0 || i >= size()) {
			throw new NullException("Invalid index");
		}
	}

	/**
	 * Очистка списка объявлений
	 */
	public void clearDeclaration() {
		declarations.clear();
	}

	/**
	 * Добавление массива к существующему
	 * 
	 * @param declarations массив объявлений
	 */
	public void addAllDeclaration(ArrayList<Declaration> declarations) {
		for (int i = 0; i < declarations.size(); i++) {
			this.declarations.add(declarations.get(i));
		}
	}

	/**
	 * Ищет список объявлений по авторов с таким же именем 
	 * или с таким же фрагментом в имени
	 * 
	 * @param name имя автора или его часть
	 * @return список найденных обявлений по авторам
	 */
	public ArrayList<Declaration> findDeclarationsByName(String name) {
		Pattern pattern = Pattern.compile(name.toLowerCase());
		ArrayList<Declaration> result = new ArrayList<Declaration>();
		for (int i = 0; i < declarations.size(); i++) {
			Matcher matcher = pattern.matcher(declarations.get(i)
					.getNameOfAuthor().toLowerCase());
			if (matcher.find()) {
				result.add(declarations.get(i));
			}
		}
		return result;
	}

	/**
	 * Возвращает массив объявлений
	 * 
	 * @return массив объявлений
	 */
	public ArrayList<Declaration> getDeclarations() {
		return declarations;
	}

	/**
	 * Устанавливает массив объявлений
	 * 
	 * @param declarations массив объявлений
	 */
	public void setDeclarations(ArrayList<Declaration> declarations) {
		this.declarations = declarations;
	}

	/**
	 * Записывет массив объявлений в указанный файл
	 * 
	 * @param path путь в файлу
	 * @throws IOException при ошибке записи выбрасывает исключение
	 */
	public void write(String path) throws IOException {
		io.write(path, declarations);
	}

	/**
	 * Считывает массив объявлений из указанного файла
	 * 
	 * @param path путь к файлу с объявлениями
	 * @throws IOException при ошибке чтения выбрасывает исключение
	 */
	public void read(String path) throws IOException {
		declarations = io.read(path);
	}

	/**
	 * Получение объекта производящего запись и чтение массива
	 * 
	 * @return объект производящий запись и чтение
	 */
	public IoInterfase<Declaration> getIo() {
		return io;
	}

	/**
	 * Установка объекта производящего запись и чтение массива
	 * 
	 * @param io объект производящий запись и чтение
	 */
	public void setIo(IoInterfase<Declaration> io) {
		this.io = io;
	}

	
	public void readWithAdd(String path) throws IOException {
		ArrayList<Declaration> temp = io.read(path);
		for (int i = 0; i < temp.size(); i++) {
			declarations.add(temp.get(i));
		}
	}

	/**
	 * Распечатывает массив объявлений в консоль
	 */
	public void print() {
		System.out.println(toString());
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Declaration declaration : declarations) {
			stringBuilder.append(declaration.getNameOfAuthor());
			stringBuilder.append(" ");
			stringBuilder.append(DateFormat.format(declaration.getDate()));
			stringBuilder.append(" ");
			stringBuilder.append(declaration.getTopic());
			stringBuilder.append(" ");
			stringBuilder.append(declaration.getHead());
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	/**
	 * Сортирует по выбранному типу
	 * 
	 * @param typeSort тип сортировки
	 */
	public void sort(TypeSort typeSort) {
		switch (typeSort) {
		case NAME:
			Collections.sort(declarations, new CompareByName());
			break;
		case DATE:
			Collections.sort(declarations, new CompareByDate());
			break;
		case TOPIC:
			Collections.sort(declarations, new CompareByTopic());
			break;
		case HEAD:
			Collections.sort(declarations, new CompareByHead());
			break;
		default:
			break;
		}
	}

	/**
	 * Сортирует по имени с учетом русской локализации
	 */
	public void sortByName() {
		Collections.sort(declarations, new CompareByName());
	}

	/**
	 * Сортирует по дате
	 */
	public void sortByDate() {
		Collections.sort(declarations, new CompareByDate());
	}

	/**
	 * Сортирует по рубрике с учетом русской локализации
	 */
	public void sortByTopic() {
		Collections.sort(declarations, new CompareByTopic());
	}

	/**
	 * Сортирует по заголовку с учетом русской локализации
	 */
	public void sortByHead() {
		Collections.sort(declarations, new CompareByHead());
	}

	/**
	 * Класс для сравнения по именам
	 * 
	 * @author Артур Бузов
	 *
	 */
	class CompareByName implements Comparator<Declaration> {
		public int compare(Declaration s1, Declaration s2) {
			Collator collator = Collator.getInstance(new Locale("ru"));
			//return c1.getTopic().compareToIgnoreCase(c2.getTopic()); альтернативный способ
			return collator.compare(s1.getNameOfAuthor().toLowerCase(), s2
					.getNameOfAuthor().toLowerCase());
		}
	}

	/**
	 * Класс для сравнения по датам
	 * 
	 * @author Артур Бузов
	 *
	 */
	class CompareByDate implements Comparator<Declaration> {
		public int compare(Declaration s1, Declaration s2) {
			return Long.compare(s1.getDate().getTime(), s2.getDate().getTime());
		}
	}

	/**
	 * Класс для сравнения по рубрикам
	 * 
	 * @author Артур Бузов
	 *
	 */
	class CompareByTopic implements Comparator<Declaration> {
		public int compare(Declaration c1, Declaration c2) {
			Collator collator = Collator.getInstance(new Locale("ru"));
			
			return collator.compare(c2.getTopic().toLowerCase(), c2.getTopic().toLowerCase());
		}
	}

	/**
	 * Класс для сравнения по заголовкам
	 * 
	 * @author Артур Бузов
	 *
	 */
	class CompareByHead implements Comparator<Declaration> {
		public int compare(Declaration c1, Declaration c2) {
			Collator collator = Collator.getInstance(new Locale("ru"));
			return collator.compare(c1.getHead().toLowerCase(), c2.getHead()
					.toLowerCase());
		}
	}

}
