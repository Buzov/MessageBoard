package ru.messageBoard.view;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ru.messageBoard.model.util.Aligner;

/**
 * Класс для предоставления информационного окна
 * 
 * @author Артур Бузов
 *
 */

public class FrameInfo extends JFrame {

	private static final long serialVersionUID = 1524951588806114117L;

	/**
	 * Панель для текста
	 */
	private JScrollPane scrollPaneText;
	/**
	 * Текстовое поле
	 */
	private JTextArea text;

	/**
	 * Метод для текстового запуска
	 * 
	 */
	public static void main(String[] args) {
		String info = "Программа графического интерфейса пользователя, в которой осуществляется обработка объявлений различных пользователей.";
		FrameInfo frame = new FrameInfo(50, 50, 300, 200, info);
		// условие закрыти€ окна
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.show();
	}

	/**
	 * Конструктор для создания информационного окна
	 * 
	 * @param x
	 *            положение центра окна относительно ширины экрана в процентах
	 * @param y
	 *            положение центра окна относительно высоты экрана в процентах
	 * @param width
	 *            ширина окна в пикселях
	 * @param height
	 *            высота окна в пикселях
	 * @param info
	 *            строка, которая будет отображена кнутри текстового поля окна
	 */
	public FrameInfo(int x, int y, int width, int height, String info) {
		setTitle("Info");
		Aligner.align(x, y, width, height, this);
		Container pane = getContentPane();
		text = new JTextArea();
		scrollPaneText = new JScrollPane();
		scrollPaneText.setViewportView(text);

		 text.setLineWrap(true);
		 text.setWrapStyleWord(true); 
		 text.setEditable(false);
		 
		text.setText(info);

		pane.add(scrollPaneText);
		setVisible(true);
	}

}
