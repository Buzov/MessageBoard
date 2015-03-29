package ru.messageBoard.model.io;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import ru.messageBoard.model.Declaration;

/**
 * Используется для чтения и записи массива объявлений в формате XML
 * 
 * @author Артур Бузов
 *
 */
public class IoXmlDeclaration extends IoAbstract<Declaration> {

	@Override
	public void write(String path, ArrayList<Declaration> array)
			throws IOException {
		try (XMLEncoder xmlEncoder = new XMLEncoder(new FileOutputStream(path))) {
			xmlEncoder.writeObject(array);
			xmlEncoder.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Declaration> read(String path) throws IOException {
		ArrayList<Declaration> declarations = null;
		try (XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(path))) {
			declarations = (ArrayList<Declaration>) xmlDecoder.readObject();
		}
		return declarations;
	}

}
