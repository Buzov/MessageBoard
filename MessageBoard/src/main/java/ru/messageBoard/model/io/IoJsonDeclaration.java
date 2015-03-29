package ru.messageBoard.model.io;

import java.io.IOException;
import java.util.ArrayList;

import ru.messageBoard.model.Declaration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Используется для чтения и записи массива объявлений в формате JSON
 * 
 * @author Артур Бузов
 *
 */

public class IoJsonDeclaration extends IoAbstract<Declaration> {

	@Override
	public void write(String path, ArrayList<Declaration> array)
			throws IOException {
		Gson gson = new GsonBuilder().create();
		String string = gson.toJson(array);
		writeToFile(path, formatSting(string));
	}

	@Override
	public ArrayList<Declaration> read(String path) throws IOException {
		Gson gson = new Gson();

		Declaration[] declarations = gson.fromJson(readStringFromFile(path),
				Declaration[].class);

		ArrayList<Declaration> array = new ArrayList<Declaration>(declarations.length);

		for (Declaration d : declarations) {
			array.add(d);
		}

		return array;
	}

	/**
	 * Метод для форматирования строки при записи для достижения лучшей
	 * читаемости
	 * 
	 * @param s
	 *            строка, которую необходимо отформатировать
	 * @return возвращает отформатированую строку
	 */
	protected String formatSting(String s) {
		s = s.replaceAll("},", "}, ");
		s = s.replaceAll("},", "},\n");
		s = s.replaceAll("\",", "\",  ");
		s = s.replaceAll("\",", "\",\n");
		return s;
	}

}
