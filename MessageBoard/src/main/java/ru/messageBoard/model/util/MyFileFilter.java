package ru.messageBoard.model.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Класс-шаблон для фильтрования файлов
 * 
 * @author Артур Бузов
 *
 */

public class MyFileFilter extends FileFilter {
	String[] ext;
	String description;

	/**
	 * Конструктор файлового фильтра
	 * 
	 * @param ext расширение файла
	 * @param description описание
	 */
	public MyFileFilter(String[] ext, String description) {
		this.ext = ext;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public boolean accept(File f) {
		if (f != null) {
			if (f.isDirectory()) {
				return true;
			}
			for(String s : ext) {
				if(f.toString().endsWith(s)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}
}
