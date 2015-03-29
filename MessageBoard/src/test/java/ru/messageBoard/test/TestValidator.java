package ru.messageBoard.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.messageBoard.model.util.Validator;

/**
 * Тест валидатора строк
 * 
 * @author Артур Бузов
 *
 */

public class TestValidator {

	String[] stringNameTrue = { "ArturArturArturArtur", "Artu", "sfsdf455", "df@34_0 dfd" };
	String[] stringNameFalse = { "g", "ggg", "8ghn", "ArturArturArturArturA" };

	String[] stringHeadTrue = { "ArturArtur", "4rturArtur. @Artur%^&Artur", "ArturArturArturArturArturArtur" };
	String[] stringHeadFalse = { " ArturArturArturArtur", "ArturArturArturArturArturArtur1" };
	
	String[] stringTextTrue = { "ArturArtur\nArturArtur", "ArturArturArturArtur",  "ArturArtur\r\n\tArturArtur"};
	String[] stringTextFalse = { " ArturArturArturArt", "ArturArturArturArtu" };

	@Test
	public void testName() {
		// тестируем проверку имени
		for (String s : stringNameTrue) {
			assertTrue(Validator.checkName(s));
		}

		for (String s : stringNameFalse) {
			assertFalse(Validator.checkName(s));
		}

		// тестируем проверку заголовка
		for (String s : stringHeadTrue) {
			assertTrue(Validator.checkHead(s));
		}

		for (String s : stringHeadFalse) {
			assertFalse(Validator.checkHead(s));
		}
		StringBuilder sb;
		// тестируем проверку текста сообщения
		for (int j = 19; j < 400; j++) {
			sb = new StringBuilder();
			sb.append("A");
			for (int i = 0; i < j; i++) {
				sb.append(generateRandomchar());
			}
			//String s = sb.toString();
			//System.out.println(s + " " +s.length());
			assertTrue(Validator.checkText(sb.toString()));
		}
		
		for (String s : stringTextTrue) {
			assertTrue(Validator.checkText(s));
		}

		for (String s : stringTextFalse) {
			assertFalse(Validator.checkText(s));
		}

	}

	public char generateRandomchar() {
		int min = 20;
		int max = 126;
		return (char) (min + Math.random() * (max - min));
	}

}
