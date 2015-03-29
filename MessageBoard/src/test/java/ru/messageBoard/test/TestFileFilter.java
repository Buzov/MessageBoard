package ru.messageBoard.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import ru.messageBoard.model.util.MyFileFilter;

/**
 * Тест файлового фильтра
 * 
 * @author Артур Бузов
 *
 */

public class TestFileFilter {
	
	String[] exentions = {"xml", "json"};
	String[] fileTrue = {"file.xml", "./src/file.json", "./src/file.xml"};
	String[] fileFalse = {"file.txt", "./src/file.doc", "./src/file.pdf"};
	
	@Test
	public void testFileFilter() {
		MyFileFilter ff = new MyFileFilter(exentions, "");
		for(String s : fileTrue) {
			assertTrue(ff.accept(new File(s)));
		}
		
		for(String s : fileFalse) {
			assertFalse(ff.accept(new File(s)));
		}
	}

}
