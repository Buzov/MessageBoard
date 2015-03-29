package ru.messageBoard.view;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import ru.messageBoard.controller.MessageBoard;
import ru.messageBoard.model.Declaration;
import ru.messageBoard.model.exception.NullException;
import ru.messageBoard.model.util.DateFormat;
/**
 * Класс представляем модель таблицы для приложения
 * 
 * @author Артур Бузов
 *
 */
public class MyTableModel implements TableModel {

	private MessageBoard messageBoard = null;
	private String[] title = { "Автор", "Дата", "Рубрика", "Заголовок", "Текст" };

	private boolean boolAuthor = true;
	private boolean boolDate = true;
	private boolean boolTopic = true;
	private boolean boolHead = true;
	private boolean boolText = true;

	private ArrayList<String> headTable = new ArrayList<String>();

	public MyTableModel(MessageBoard messageBoard) {
		this.messageBoard = messageBoard;
	}
	
	public void setMessageBoard(MessageBoard messageBoard) {
		this.messageBoard = messageBoard;
	}

	public String[] getTitle() {
		return title;
	}

	public void setTitle(String[] title) {
		this.title = title;
	}

	public void addCol(String name) {
		switch (name) {
		case "Автор":
			boolAuthor = true;
		case "Дата":
			boolDate = true;
		case "Рубрика":
			boolTopic = true;
		case "Заголовок":
			boolHead = true;
		case "Текст":
			boolText = true;
		}
	}

	public void removeCol(String name) {
		switch (name) {
		case "Автор":
			boolAuthor = false;
		case "Дата":
			boolDate = false;
		case "Рубрика":
			boolTopic = false;
		case "Заголовок":
			boolHead = false;
		case "Текст":
			boolText = false;
		}
	}

	public ArrayList<String> getHeadTableArray() {
		headTable.clear();
		if (boolAuthor) {
			headTable.add("Автор");
		}
		if (boolDate) {
			headTable.add("Дата");
		}
		if (boolTopic) {
			headTable.add("Рубрика");
		}
		if (boolHead) {
			headTable.add("Заголовок");
		}
		if (boolText) {
			headTable.add("Текст");
		}
		return headTable;
	}

	@Override
	public int getRowCount() {
		return messageBoard == null ? 1 : messageBoard.size();
	}

	@Override
	public int getColumnCount() {
		return getHeadTableArray().size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return getHeadTableArray().get(columnIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (messageBoard.isEmpty()) {
			return Object.class;
		}
		return getValueAt(0, columnIndex).getClass();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return false;
		case 1:
			return false;
		case 2:
			return false;
		case 3:
			return false;
		case 4:
			return false;
	    default:
			return true;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Declaration declaration = messageBoard.getDeclaration(rowIndex);
		String col = getHeadTableArray().get(columnIndex);
		getHeadTableArray();
		switch (col) {
		case "Автор":
			return declaration.getNameOfAuthor();
		case "Дата":
			return DateFormat.format(declaration.getDate());
		case "Рубрика":
			return declaration.getTopic();
		case "Заголовок":
			return declaration.getHead();
		case "Текст":
			return declaration.getText();
		}
		return "";
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		String col = getHeadTableArray().get(columnIndex);
		switch (col) {
		case "Автор":
			messageBoard.getDeclaration(rowIndex).setNameOfAuthor((String) aValue);
			break;
		case "Дата":
			messageBoard.getDeclaration(rowIndex).setDate(new Date());
			break;
		case "Рубрика":
			messageBoard.getDeclaration(rowIndex).setTopic((String) aValue);
			break;
		case "Заголовок":
			messageBoard.getDeclaration(rowIndex).setHead((String) aValue);
			break;
		case "Текст":
			messageBoard.getDeclaration(rowIndex).setText((String) aValue);
			break;
		}

	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
	}

	public void removeRow(int row) throws NullException {
		messageBoard.removeDeclaration(row);
	}

	public void addRow() {
		messageBoard.addDeclaration(new Declaration());
	}

}
