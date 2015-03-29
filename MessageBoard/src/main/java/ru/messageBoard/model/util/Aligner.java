package ru.messageBoard.model.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
/**
 * Этот класс содержит в себе метод для выравнивания объектов типа Component 
 * относительно центра экрана
 * 
 * @author Артур Бузов
 *
 */
public class Aligner {
	/**
	 * Этот метод позволяет установить положение центра объкта типа Frame относительно
	 * центра экрана
	 * 
	 * @param x положение центра окна относительно ширины экрана в процентах
	 * @param y положение центра окна относительно высоты экрана в процентах
	 * @param width ширина окна в пикселях
	 * @param height высота окна в пикселях
	 * @param frame центрируемое окно
	 */
	public static void align(int x, int y, int width, int height, Component frame) {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int lx = screenSize.width;
		int ly = screenSize.height;
		frame.setBounds(x * lx / 100 - width / 2, y * ly / 100 - height / 2, width, height);
	}
}
