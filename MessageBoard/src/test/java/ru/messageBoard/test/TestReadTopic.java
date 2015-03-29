package ru.messageBoard.test;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Test;

import ru.messageBoard.model.util.ReadTopic;

/**
 * Тестирование чтения файла с рубриками
 * 
 * @author Артур Бузов
 *
 */

public class TestReadTopic {
	
	String path = "./src/main/test/";
	
	@Test(expected=FileNotFoundException.class)
	public void testFileFilter() throws FileNotFoundException {
		String[] topics = ReadTopic.read(path);
		assertTrue(topics.length == 5);
		assertTrue(topics[0].equals("продажа"));
		assertTrue(topics[1].equals("покупка"));
		assertTrue(topics[2].equals("аренда"));
		assertTrue(topics[3].equals("услуги"));
		assertTrue(topics[4].equals("знакомства"));
	}

}
