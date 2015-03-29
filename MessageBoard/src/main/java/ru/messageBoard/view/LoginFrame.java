package ru.messageBoard.view;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.messageBoard.model.util.Aligner;
import ru.messageBoard.model.util.Validator;
/**
 * Окно для логирования пользователя
 * 
 * @author Артур Бузов
 *
 */
public class LoginFrame {
	
	/**
	 * Путь к файлу настроек spring
	 */
	private static final String pathToBean = "./bean.xml";
	/**
	 * объкт spring для создания экземпляра главного окна
	 */
	private static ApplicationContext actx = new ClassPathXmlApplicationContext(pathToBean);
	
	private MessageBoardFrame messageBoardFrame = null;

	private JFrame frame;
	private JLabel label;
	private JTextField textField;
	private JButton buttonOk;
	private JButton buttonExit;
	private String myName;
	private JPanel panel;
	private JProgressBar progressBar;
	private JLabel lblNewLabel;

	/**
	 * Этот метод запускает окно.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame window = new LoginFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Конструктор для создания объекта окна.
	 */
	public LoginFrame() {
		initialize();
	}

	/**
	 * Инициализасия содержимого окна.
	 */
	private void initialize() {
		frame = new JFrame();
		// Выравниваем окно по центу экрана
		Aligner.align(50, 50, 300, 125, frame);
		// Запрет изменения размера
		frame.setResizable(false);
		// Убираем оконную рамку
		frame.setUndecorated(true);
		// Установка типа операции при закрытии
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridwidth = 2;
		gbc_label.insets = new Insets(5, 5, 5, 0);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		frame.getContentPane().add(getLabel(), gbc_label);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		frame.getContentPane().add(getTextField(), gbc_textField);
		GridBagConstraints gbc_buttonOk = new GridBagConstraints();
		gbc_buttonOk.insets = new Insets(0, 0, 5, 5);
		gbc_buttonOk.gridx = 0;
		gbc_buttonOk.gridy = 2;
		frame.getContentPane().add(getButtonOk(), gbc_buttonOk);
		GridBagConstraints gbc_buttonExit = new GridBagConstraints();
		gbc_buttonExit.insets = new Insets(0, 0, 5, 0);
		gbc_buttonExit.gridx = 1;
		gbc_buttonExit.gridy = 2;
		frame.getContentPane().add(getButtonExit(), gbc_buttonExit);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 5, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		frame.getContentPane().add(getPanel(), gbc_panel);
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("Введите имя!");
		}
		return label;
	}

	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setColumns(10);
			textField.setHorizontalAlignment(JTextField.CENTER);
		}
		return textField;
	}

	private JButton getButtonOk() {
		if (buttonOk == null) {
			buttonOk = new JButton("Готово");
			//buttonOk.setMnemonic(KeyEvent.VK_S);
			buttonOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					myName = getTextField().getText();
					if (!Validator.checkName(myName)) {
						JOptionPane.showMessageDialog(null, "Неверное имя!\n"
							    + "Длина должна быть от 4 до 20 символов.\n"
							    + "Имя должно начинаться с буквы.");
					} else {
						buttonOk.setEnabled(false);
						getButtonExit().setEnabled(false);
						progressBar.setVisible(true);
						lblNewLabel.setVisible(true);
						
						new MyThread().start();
					}
				}
			});
		}
		return buttonOk;
	}

	private JButton getButtonExit() {
		if (buttonExit == null) {
			buttonExit = new JButton("Выход");
			//buttonExit.setMnemonic(KeyEvent.VK_W);
			buttonExit.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					Object[] options = { "Да", "Нет" };
					if (JOptionPane.showOptionDialog(buttonExit,
							"Вы уверены, что хотите выйти?", "",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, // do not use a
																// custom Icon
							options, // the titles of buttons
							options[0]) == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				}

			});
		}
		return buttonExit;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getLblNewLabel());
			panel.add(getProgressBar());
		}
		return panel;
	}
	private JProgressBar getProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar();
			progressBar.setToolTipText("");
			progressBar.setVisible(false);
			progressBar.setIndeterminate(true);
		}
		return progressBar;
	}
	
	
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Идет загрузка");
			lblNewLabel.setVisible(false);
		}
		return lblNewLabel;
	}
	
	/**
	 * Отдельный поток для запуска приложения после проведения логирования
	 * 
	 * @author Артур Бузов
	 *
	 */
	class MyThread extends Thread {

		@Override
		public void run() {
			//new MessageBoardFrame(myName);
			messageBoardFrame = (MessageBoardFrame) actx.getBean("mainFrame");
			messageBoardFrame.setMyName(myName);
			messageBoardFrame.initialize();
			
			frame.setVisible(false);
		}
		
	}
}
