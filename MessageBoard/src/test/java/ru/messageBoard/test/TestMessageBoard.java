package ru.messageBoard.test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.messageBoard.controller.MessageBoard;
import ru.messageBoard.model.Declaration;
import ru.messageBoard.model.exception.NullException;
import ru.messageBoard.model.io.IoJsonDeclaration;
import ru.messageBoard.model.io.IoXmlDeclaration;

public class TestMessageBoard {
	MessageBoard mb;

	@Before
	public void first() {
		initialize();
	}

	public void initialize() {
		int year = 2015 - 1900;
		int month = 2;
		int date = 29;
		int hrs = 2;
		int min = 20;
		int sec = 20;
		@SuppressWarnings("deprecation")
		Date d1 = new Date(year, month, date, hrs, min, sec + 1);
		@SuppressWarnings("deprecation")
		Date d2 = new Date(year, month, date, hrs, min, sec + 2);
		@SuppressWarnings("deprecation")
		Date d3 = new Date(year, month, date, hrs, min, sec + 3);
		@SuppressWarnings("deprecation")
		Date d4 = new Date(year, month, date, hrs, min, sec + 4);
		mb = new MessageBoard();
		mb.addDeclaration(new Declaration("Ъртур", d1, "аренда", "Я Последний",
				"Тестовый текст"));
		mb.addDeclaration(new Declaration("Артур", d2, "Ъпокупка", "Продам",
				"Тестовый текст"));
		mb.addDeclaration(new Declaration("Дмитрий", d3, "продажа", "Продам",
				"Тестовый текст"));
		mb.addDeclaration(new Declaration("Кирил", d4, "япродажа", "А первый",
				"Тестовый текст"));
	}

	@Test(expected = NullException.class)
	public void testMain() throws IOException {
		testSize();
		testFind();
		testSortByName();
		testSortByDate();
		testSortByTopic();
		testSortByHead();
		testRemoveSetAndGet();
		testClear();
		// testIO();
	}

	public void testSize() {
		assertTrue(mb.size() == 4);
	}

	public void testFind() {
		assertTrue(mb.findDeclarationsByName("тур").size() == 2);
	}

	public void testSortByName() {
		mb.sortByName();
		assertTrue(mb.getDeclaration(0).getNameOfAuthor().equals("Артур"));
		assertTrue(mb.getDeclaration(3).getNameOfAuthor().equals("Ъртур"));
	}

	public void testSortByDate() {
		mb.sortByDate();
		assertTrue(mb.getDeclaration(0).getNameOfAuthor().equals("Ъртур"));
		assertTrue(mb.getDeclaration(3).getNameOfAuthor().equals("Кирил"));
	}

	public void testSortByTopic() {
		mb.sortByTopic();
		assertTrue(mb.getDeclaration(0).getTopic().equals("аренда"));
		assertTrue(mb.getDeclaration(3).getTopic().equals("япродажа"));
	}

	public void testSortByHead() {
		mb.sortByHead();
		assertTrue(mb.getDeclaration(0).getHead().equals("А первый"));
		assertTrue(mb.getDeclaration(3).getHead().equals("Я Последний"));
	}

	public void testRemoveSetAndGet() throws NullException {
		mb.sortByName();
		mb.removeDeclaration(0);
		assertTrue(mb.size() == 3);
		mb.setDeclaration(1, mb.getDeclaration(0));
		assertTrue(mb.getDeclaration(1).getNameOfAuthor().equals("Дмитрий"));
		mb.getDeclaration(10);
		mb.setDeclaration(10, mb.getDeclaration(0));
		mb.removeDeclaration(-5);
	}

	public void testClear() {
		mb.clearDeclaration();
		assertTrue(mb.size() == 0);
	}

	@After
	public void testIO() throws IOException {
		String pathToXml = "./src/test/java/testFile/testFile.xml";
		String pathToJson = "./src/test/java/testFile/testFile.json";
		// Устанавливаем фабрику для чтения и записи XML
		mb.setIo(new IoXmlDeclaration());
		// Инициализируем
		initialize();

		testForIO(pathToXml);

		// Устанавливаем фабрику для чтения и записи JSON
		mb.setIo(new IoJsonDeclaration());

		testForIO(pathToJson);
	}

	public void testForIO(String path) throws IOException {
		// Записываем в формате XML в указанный файл
		mb.write(path);

		// Очищаем
		testClear();
		// Считываем из ранее записанного файла
		mb.read(path);

		// проверяем считанные данные
		testSize();
		testFind();

	}

}
