package ru.messageBoard.view;

import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;

import ru.messageBoard.controller.MessageBoard;
import ru.messageBoard.model.Declaration;
import ru.messageBoard.model.exception.NullException;
import ru.messageBoard.model.io.IoInterfase;
import ru.messageBoard.model.io.IoXmlDeclaration;
import ru.messageBoard.model.util.Aligner;
import ru.messageBoard.model.util.DateFormat;
import ru.messageBoard.model.util.MyFileFilter;
import ru.messageBoard.model.util.ReadTopic;
import ru.messageBoard.model.util.Validator;

import java.awt.FlowLayout;

/**
 * Этот класс является основной графической частью приложения
 * 
 * @author Артур Бузов
 *
 */
public class MessageBoardFrame {

	/**
	 * логгер
	 */
	private static final Logger LOG = Logger.getLogger(MessageBoardFrame.class);
	/**
	 * Информация о приложении
	 */
	private static final String INFO = "Приложение для работы с объявлениями.\n"
			+ "Позволяет добавлять, редактировать и удалять собственные объявления.\n"
			+ "Программа не позволяет удалять и редактировать чужие записи.\n";
	/**
	 * Строка используется при неправильной минимальной длине заголовка
	 * объявления
	 */
	private static final String HEAD_ERROR_MIN = "Длина заголовка должна быть больше 10 символов!\n"
			+ "Сейчас длина заголовка равна %d символов!\n";
	/**
	 * Строка используется при неправильной максимальной длине заголовка
	 * объявления
	 */
	private static final String HEAD_ERROR_MAX = "Длина заголовка должна быть меньше 30 символов!\n"
			+ "Сейчас длина заголовка равна %d символов!\n";
	/**
	 * Строка используется при неправильном формате заголовка объявления
	 */
	private static final String HEAD_ERROR = "Неверный формат заголовка!\n"
			+ "Заголовок должен начинаться с буквы или цифры!\n";
	/**
	 * Строка используется при неправильной минимальной длине текста объявления
	 */
	private static final String TEXT_ERROR_MIN = "Длина текста объявления должна быть больше 20 символов!\n"
			+ "Сейчас длина текста объявления равна %d символов!\n";
	/**
	 * Строка используется при неправильной максимальной длине текста объявления
	 */
	private static final String TEXT_ERROR_MAX = "Длина текста объявления быть меньше 400 символов!\n"
			+ "Сейчас длина текста объявления равна %d символов!\n";
	/**
	 * Строка используется при неправильном формате текста объявления
	 */
	private static final String TEXT_ERROR = "Неверный формат текста объявления!\n"
			+ "Объявление должно начинаться с буквы или цифры!\n";

	/**
	 * Массив рубрик по умолчанию (если не будут считаны из внешнего файла)
	 */
	private static String[] topics = { "продажа", "покупка", "аренда",
			"услуги", "знакомства" };
	private JFileChooser chooser = new JFileChooser();
	/**
	 * Расширения файлов для фильтрации при просмотре каталогов
	 */
	private String[] extensions = { "xml", "json" };
	/**
	 * Кореневой католо приложения
	 */
	private String rootDirectory = "./";
	/**
	 * Путь к файлу для чтения
	 */
	private String pathFromFile = "./serialization.xml";
	/**
	 * Путь к файлу для сохранения
	 */
	private String pathToFile = null;
	/**
	 * Путь к файлу со списком рубрик
	 */
	private String pathToTopic = "./topic.properties";
	/**
	 * Массив для отката
	 */
	private ArrayList<Declaration> oldList = new ArrayList<Declaration>();

	/**
	 * Имя пользователя программы
	 */
	private String myName;

	/**
	 * Контейнер для объявлений
	 */
	private MessageBoard messageBoard;
	/**
	 * Объект для чтения и записи файлов с объявлениями
	 */
	private IoInterfase<Declaration> io = new IoXmlDeclaration();
	// Элементы пользовательского интерфейса
	private JFrame frmMessageBoard;
	private JScrollPane scrollPane;
	private JTable table;
	private MyTableModel myTableModel;
	private TableRowSorter<MyTableModel> sorter;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmOpen;
	private JMenuItem mntmSave;
	private JMenuItem mntmSaveAs;
	private JMenuItem mntmExit;
	private JSeparator separator;
	private Button btnDelete;
	private Button btnAdd;
	private JLabel lblSortBy;
	private JComboBox<String> comboBox;
	private JMenu mnHelp;
	private JMenuItem mntmAbout;
	private JTextField textField;
	private JScrollPane scrollPane_1;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTextArea textAreaTextDec;

	private JPanel panel;
	private JLabel label;
	private JButton btnMyDeclaration;
	private JButton btnAllDeclaration;
	private JButton btnEdit;

	/**
	 * Тестовый запуск приложения.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MessageBoardFrame window = new MessageBoardFrame("");
					window.frmMessageBoard.setVisible(true);
					LOG.info("Приложение запустилось !");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MessageBoardFrame() {

	}

	public MessageBoardFrame(String pathFromFile) {
		this.pathFromFile = pathFromFile;
		System.out.println(pathFromFile);
	}

	/**
	 * Конструктор с инициализацией приложения
	 * 
	 * @param myName
	 *            имя пользользователя
	 */
	public MessageBoardFrame(String pathFromFile, String myName) {
		this.pathFromFile = pathFromFile;
		this.myName = myName;
		initialize();
		frmMessageBoard.setVisible(true);
	}

	/**
	 * Возвращает корневой каталог приложения
	 * 
	 * @return корневой каталог приложения
	 */
	public String getRootDirectory() {
		return rootDirectory;
	}

	/**
	 * Устанавливает корневой каталог приложения
	 * 
	 * @param rootDirectory
	 *            корневой каталог приложения
	 */
	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	/**
	 * Метод получает путь к загрузочному файлу
	 * 
	 * @return путь к загрузочному файлу
	 */
	public String getPathFromFile() {
		return pathFromFile;
	}

	/**
	 * Метод устанавливает путь к загрузочному файлу
	 * 
	 * @param pathFromFile
	 *            путь к загрузочному файлу
	 */
	public void setPathFromFile(String pathFromFile) {
		this.pathFromFile = pathFromFile;
	}

	/**
	 * Метод дляполучения именни текущего пользователя
	 * 
	 * @return имя пользователя
	 */
	public String getMyName() {
		return myName;
	}

	/**
	 * Устанавливает имя пользователя
	 * 
	 * @param myName
	 *            имя пользователя
	 */
	public void setMyName(String myName) {
		this.myName = myName;
	}

	/**
	 * Получает путь к файлу с рубриками
	 * 
	 * @return путь к файлу с рубриками
	 */
	public String getPathToTopic() {
		return pathToTopic;
	}

	/**
	 * Устанавливает путь к файлу с рубрикаим
	 * 
	 * @param pathToTopic
	 *            путь к файлу с рубриками
	 */
	public void setPathToTopic(String pathToTopic) {
		this.pathToTopic = pathToTopic;
	}

	/**
	 * Инициализация содержимого окна.
	 */
	public void initialize() {
		frmMessageBoard = new JFrame();

		frmMessageBoard.setTitle("Доска объявлений");
		// установка фильтра файлов
		chooser.setFileFilter(new MyFileFilter(extensions, "xml, json"));
		// Необходимо начинать поиск файлов из текущей папки проекта:
		chooser.setCurrentDirectory(new File(rootDirectory));
		try {
			topics = ReadTopic.read(pathToTopic);
		} catch (FileNotFoundException e) {
			LOG.error("Не удалась загрузка рубрик из файла - " + pathToTopic);
		}

		Aligner.align(50, 50, 1000, 380, frmMessageBoard);
		// frmMessageBoard.setBounds(100, 100, 750, 373); альтернативный способ
		// задания размера
		frmMessageBoard.setMinimumSize(new java.awt.Dimension(750, 300));
		
		//frmMessageBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); операция при закрытии
		// устанавливаетм слушатель закрытия
		frmMessageBoard.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmMessageBoard.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				exit();
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				System.out.println(e.getID());
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 400, 0, 300, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		frmMessageBoard.getContentPane().setLayout(gridBagLayout);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		frmMessageBoard.getContentPane().add(getScrollPane(), gbc_scrollPane);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(5, 0, 5, 5);
		gbc_scrollPane_1.gridx = 2;
		gbc_scrollPane_1.gridy = 0;
		frmMessageBoard.getContentPane().add(getScrollPane_1(),
				gbc_scrollPane_1);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 5, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		frmMessageBoard.getContentPane().add(getPanel_2(), gbc_panel_2);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(5, 5, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		frmMessageBoard.getContentPane().add(getPanel(), gbc_panel_1);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 5, 5, 5);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		frmMessageBoard.getContentPane().add(getPanel_1(), gbc_panel);
		frmMessageBoard.setJMenuBar(getMenuBar());
		frmMessageBoard.setVisible(true);
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	@SuppressWarnings("finally")
	private MessageBoard getMessageBoard() {
		try {
			if (messageBoard == null) {
				messageBoard = new MessageBoard();
				messageBoard.setIo(getIo());
				messageBoard.read(pathFromFile);
			}
		} catch (FileNotFoundException e1) {
			LOG.error("Не найден файл с объвлениями - " + pathFromFile);
			messageBoard.initialize(myName);
		} catch (IOException e) {
			LOG.error("Ошибка при загрузке файла с объявлениями - "
					+ pathFromFile);
			messageBoard.initialize(myName);
		} 
		
		return messageBoard;
		
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable(getMyTableModel());
			// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); изменение
			// размера таблицы

			setSelectionListener();

		}
		columFormat();
		// table.setModel(getMyTableModel());
		return table;
	}

	private void setSelectionListener() {
		table.setAutoCreateRowSorter(true);

		table.setRowSorter(getTableRowSorter());
		// For the purposes of this example, better to have a single
		// selection.
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// When selection changes, provide user with row numbers for
		// both view and model.
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						int viewRow = table.getSelectedRow();
						if (viewRow < 0) {
							// Selection got filtered away.
							getTextAreaTextDec().setText("");
						} else {
							int modelRow = table
									.convertRowIndexToModel(viewRow);
							getTextAreaTextDec().setText(
									(String) table.getValueAt(viewRow, 4));
						}
					}
				});
	}

	/**
	 * Форматирование ширины столбцов таблицы
	 */
	private void columFormat() {
		ArrayList<String> list = getMyTableModel().getHeadTableArray();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				String s = list.get(i);
				switch (s) {
				case "Имя":
					table.getColumnModel().getColumn(i).setMinWidth(100);
					break;
				case "Дата":
					table.getColumnModel().getColumn(i).setMinWidth(150);
					break;
				case "Рубрика":
					table.getColumnModel().getColumn(i).setMinWidth(40);
					break;
				case "Заголовок":
					table.getColumnModel().getColumn(i).setMinWidth(100);
					break;
				case "Текст":
					// table.getColumnModel().getColumn(i).setMinWidth();
					break;
				}
			}
		}

	}

	private MyTableModel getMyTableModel() {
		if (myTableModel == null) {
			myTableModel = new MyTableModel(getMessageBoard());
		}
		return myTableModel;
	}

	private TableRowSorter<MyTableModel> getTableRowSorter() {
		if (sorter == null) {
			sorter = new TableRowSorter<MyTableModel>(getMyTableModel());
		}
		return sorter;
	}

	private JMenuBar getMenuBar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnFile());
			menuBar.add(getMnHelp());
		}
		return menuBar;
	}

	private JMenu getMnFile() {
		if (mnFile == null) {
			mnFile = new JMenu("Файл");
			mnFile.add(getMntmOpen());
			mnFile.add(getMntmSave());
			mnFile.add(getMntmSaveAs());
			mnFile.add(getSeparator());
			mnFile.add(getMntmExit());
		}
		return mnFile;
	}

	private JMenuItem getMntmOpen() {
		if (mntmOpen == null) {
			mntmOpen = new JMenuItem("Открыть");
			mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
					InputEvent.ALT_MASK));
			// mntmOpen.setMnemonic('o');
			mntmOpen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (chooser.showOpenDialog(getPanel()) == JFileChooser.APPROVE_OPTION) {
						pathFromFile = chooser.getSelectedFile().getPath();
						try {
							getMessageBoard().readWithAdd(pathFromFile);
							LOG.info("Загрузка базы из файла - " + pathFromFile);
						} catch (FileNotFoundException e1) {
							LOG.error("Файл базы не найден - " + pathFromFile);
						} catch (IOException e1) {
							LOG.error("Ошибка записи базы в файл - "
									+ pathFromFile);
						}
						oldList = getMessageBoard().getDeclarations();
						myTableModel = new MyTableModel(getMessageBoard());
						System.out.println(getMessageBoard().toString());
						table.setModel(myTableModel);
						columFormat();
						// перерисовываем таблицу
						// getTable().revalidate() альтернативный способ;
						// getTable().repaint() альтернативный способ;
						table.updateUI();
					}
				}
			});
		}
		return mntmOpen;
	}

	private JMenuItem getMntmSave() {
		if (mntmSave == null) {
			mntmSave = new JMenuItem("Сохранить");
			mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					InputEvent.CTRL_MASK));
			mntmSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						messageBoard.sortByName();
						messageBoard.write(pathFromFile);
						LOG.info("Сохранение базы в файл - " + pathFromFile);
					} catch (IOException e1) {
						LOG.error("Сохранение базы в файл не удалось - "
								+ pathFromFile);
						;
					}
				}
			});
		}
		return mntmSave;
	}

	private JMenuItem getMntmSaveAs() {
		if (mntmSaveAs == null) {
			mntmSaveAs = new JMenuItem("Сохранить как");
			mntmSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					InputEvent.ALT_MASK));
			mntmSaveAs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (chooser.showSaveDialog(getPanel()) == JFileChooser.APPROVE_OPTION) {
						pathToFile = chooser.getSelectedFile().getPath();
						try {
							messageBoard.sortByName();
							messageBoard.write(pathToFile);
						} catch (IOException e1) {
							LOG.error("Ошибка сохранения файла - " + pathToFile);
						}
						LOG.info("Сохранение базы в XML:" + pathToFile);
					}
				}
			});

		}
		return mntmSaveAs;
	}

	private JMenuItem getMntmExit() {
		if (mntmExit == null) {
			mntmExit = new JMenuItem("Выход");
			mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
					InputEvent.ALT_MASK));
			mntmExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					exit();
				}
			});
			// mntmExit.setMnemonic('q');
		}
		return mntmExit;
	}
	
	private void exit() {
		String[] buttons = { "Да!", "Сохранить и выйти!", "Нет!" };

	    int rc = JOptionPane.showOptionDialog(null, "Хотите выйти?", "",
	        JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[2]);
	    
	    switch(rc) {
	    	case 1:
	    		messageBoard.sortByName();
				try {
					messageBoard.write(pathFromFile);
				} catch (IOException e1) {
					LOG.error("Ошибка сохранения файла - " + pathFromFile);
				}
	    	case 0:
	    		System.exit(0);
	    		LOG.info("Выход из приложения");
	    		break;
	    	case 2:   		
	    }
	}

	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
		}
		return separator;
	}

	private Button getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new Button("Удалить");

			btnDelete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int viewRow = table.getSelectedRow();
					if (viewRow >= 0) {
						if (!myName.equals(getTable().getValueAt(viewRow, 0))) {
							JOptionPane.showMessageDialog(null,
									"Нельзя удалять чужие записи!");
						} else {
							del();
						}
					}

				}
			});
		}
		return btnDelete;
	}

	// удаление записи
	private void del() {
		int row = table.getSelectedRow();
		// -1 because counting starts at 0
		int rowCount = table.getRowCount() - 1;

		if (row != -1) {
			row = table.convertRowIndexToModel(row);

			int result = JOptionPane.showOptionDialog(null,
					"Вы уверены что хотите удалить запись с ",
					"Запрос удаления записи", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "Да",
							"Нет" }, "Да");
			if (result == JOptionPane.YES_OPTION) {
				try {
					LOG.info("Удален из таблицы: "
							+ getMessageBoard().getDeclaration(row));
					getMessageBoard().removeDeclaration(row);
				} catch (NullException e) {
					LOG.error("Неверный индекс при удалении" + row);
				}
			}
			System.out.println(getMessageBoard());
			getMyTableModel().setMessageBoard(getMessageBoard());
			// table.setModel(getMyTableModel());

			table.setModel(new MyTableModel(getMessageBoard()));
			columFormat();
			setSelectionListener();
			// getTable().revalidate();
			// getTable().repaint();
			table.updateUI();
		}
	}

	private Button getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new Button("Добавить");
			btnAdd.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// int row = table.getSelectedRow();
					getTextField().setText("");
					((MyTableModel) table.getModel()).addRow();

					oldList = messageBoard.getDeclarations();
					// System.out.println(oldList);
					messageBoard.setDeclarations(oldList);
					table.setModel(new MyTableModel(messageBoard));
					setSelectionListener();
					table.updateUI();
					int rowCount = getTable().getRowCount() - 1;

					runAddInputDialog(rowCount, "Добавление записи");
				}
			});
		}
		return btnAdd;
	}

	class ColumnKeeper implements ActionListener {
		protected MyTableModel model;
		protected TableColumn column;
		protected int k;

		public ColumnKeeper(MyTableModel model, int k) {
			// this.column = model.getColumn(k);
			this.model = model;
			this.k = k;
		}

		public void actionPerformed(ActionEvent e) {
			JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();
			if (item.isSelected()) {
				// getTable().addColumn(aColumn);
				model.addCol(model.getColumnName(k));
			} else {
				model.removeCol(model.getColumnName(k));
				// getTable().removeColumn(aColumn);
			}

			getTable().tableChanged(new TableModelEvent(model));
			getTable().repaint();
			// getTable().updateUI();
		}
	}

	private JLabel getLblSortBy() {
		if (lblSortBy == null) {
			lblSortBy = new JLabel("Сортировать по");
		}
		return lblSortBy;
	}

	private JComboBox<String> getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox<String>();
			comboBox.setModel(new DefaultComboBoxModel(TypeCol.values()));
		}
		return comboBox;
	}

	private JMenu getMnHelp() {
		if (mnHelp == null) {
			mnHelp = new JMenu("Помощь");
			mnHelp.add(getMntmAbout());
		}
		return mnHelp;
	}

	private JMenuItem getMntmAbout() {
		if (mntmAbout == null) {
			mntmAbout = new JMenuItem("О программе");
			mntmAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
					InputEvent.ALT_MASK));
			mntmAbout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					LOG.info("Запрос информации о программе.");
					FrameInfo frame = new FrameInfo(50, 50, 300, 200, INFO);
				}
			});
		}
		return mntmAbout;
	}

	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField("");
			textField.setColumns(12);
			textField.setHorizontalAlignment(JTextField.CENTER);

			textField.getDocument().addDocumentListener(new DocumentListener() {
				public void changedUpdate(DocumentEvent e) {
					newFilter();
				}

				public void insertUpdate(DocumentEvent e) {
					newFilter();
				}

				public void removeUpdate(DocumentEvent e) {
					newFilter();
				}

				private void newFilter() {
					RowFilter<MyTableModel, Object> rf = null;
					// If current expression doesn't parse, don't update.
					int colum = 0;
					switch (getComboBox().getSelectedIndex()) {
					case 0:
						LOG.info("Сортировка по фамилии.");
						colum = 0;
						break;
					case 1:
						LOG.info("Сортировка по имени.");
						colum = 2;
						break;
					}

					try {
						rf = RowFilter.regexFilter(textField.getText(), colum);
					} catch (java.util.regex.PatternSyntaxException e) {
						return;
					}
					getTableRowSorter().setRowFilter(rf);
				}
			});
		}
		return textField;
	}

	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setViewportView(getTextAreaTextDec());
		}
		return scrollPane_1;
	}

	private JPanel getPanel() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panel_1.add(getLblSortBy());
			panel_1.add(getComboBox());
			panel_1.add(getTextField());
		}
		return panel_1;
	}

	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panel_2.add(getBtnDelete());
			panel_2.add(getBtnAdd());
			panel_2.add(getBtnEdit());
		}
		return panel_2;
	}

	private JTextArea getTextAreaTextDec() {
		if (textAreaTextDec == null) {
			textAreaTextDec = new JTextArea();
			textAreaTextDec.setText("");
			textAreaTextDec.setLineWrap(true);
			textAreaTextDec.setWrapStyleWord(true);

		}
		return textAreaTextDec;
	}

	private JPanel getPanel_1() {
		if (panel == null) {
			panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panel.add(getLabel());
			panel.add(getBtnMyDeclaration());
			panel.add(getBtnAllDeclaration());
		}
		return panel;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("Показать записи");
		}
		return label;
	}

	private JButton getBtnMyDeclaration() {
		if (btnMyDeclaration == null) {
			btnMyDeclaration = new JButton("Только свои");
			btnMyDeclaration.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					getTextField().setText(myName);
				}
			});
		}
		return btnMyDeclaration;
	}

	private JButton getBtnAllDeclaration() {
		if (btnAllDeclaration == null) {
			btnAllDeclaration = new JButton("Все");
			btnAllDeclaration.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getTextField().setText("");
				}
			});
		}
		return btnAllDeclaration;
	}

	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton("Редактировать");
			btnEdit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int viewRow = getTable().getSelectedRow();
					// table.setUpdateSelectionOnSort(true);
					if (viewRow >= 0) {
						if (!myName.equals(getTable().getValueAt(viewRow, 0))) {
							JOptionPane.showMessageDialog(null,
									"Нельзя редактировать чужие записи!");
						} else {
							runInputDialog(viewRow, "Редактирование записи");
						}
					}
				}
			});
		}
		return btnEdit;
	}

	private void runInputDialog(int row, String title) {
		JTable table = getTable();

		// String name = (String) table.getValueAt(row, 0);
		String topic = (String) table.getValueAt(row, 2);
		String head = (String) table.getValueAt(row, 3);
		String text = (String) table.getValueAt(row, 4);
		new InputDeclaration(myName, new Date(), topic, head, text, row, title);
	}

	private void runAddInputDialog(int row, String title) {
		new InputDeclaration(myName, new Date(), "", "", "", row, title);
	}

	/**
	 * Возвращает объект, определяющий с каким форматом данных будет работать
	 * программа
	 * 
	 * @return объект, определяющий с каким форматом данных будет работать
	 *         программа
	 */
	public IoInterfase<Declaration> getIo() {
		return io;
	}

	/**
	 * Устанавливает объект, определяющий с каким форматом данных будет работать
	 * программа
	 * 
	 * @param io
	 *            объект, определяющий с каким форматом данных будет работать
	 *            программа
	 */
	public void setIo(IoInterfase<Declaration> io) {
		this.io = io;
	}

	/**
	 * Этот класс представляет собой окно, которое используеться при
	 * редактировании объявлений и добавлении новых
	 * 
	 * @author Артур Бузов
	 *
	 */
	private class InputDeclaration extends JDialog {

		private static final long serialVersionUID = 8672879222311032079L;
		private JPanel contentPane;
		private JLabel labelName;
		private JLabel labelDate;
		private JLabel labelTopic;
		private JTextField textFieldName;
		private JTextField textFieldDate;
		private JComboBox<String> comboBoxTopic;
		private JLabel labelHead;
		private JLabel labelText;
		private JScrollPane scrollPaneText;
		private JTextArea textAreaText;
		private JScrollPane scrollPaneHead;
		private JTextArea textAreaHead;
		private JPanel panelOk;
		private JButton btnOk;
		private JScrollPane scrollPaneError;
		private JTextArea textAreaError;

		private String name;
		private Date date;
		private String topic;
		private String head;
		private String text;
		private int row;

		/**
		 * 
		 * @param name
		 *            имя автора объявления
		 * @param date
		 *            дата публикация объявления
		 * @param topic
		 *            рубрика объявления
		 * @param head
		 *            заголовок объявления
		 * @param text
		 *            содержимое объявления
		 * @param row
		 *            номер строки таблицы, которая редактируется
		 * @param title
		 *            заголовок диалогового окна
		 */
		public InputDeclaration(String name, Date date, String topic,
				String head, String text, int row, String title) {
			this.name = name;
			this.date = date;
			this.topic = topic;
			this.head = head;
			this.text = text;
			this.row = row;
			setTitle(title);
			setModalityType(ModalityType.TOOLKIT_MODAL);
			setDefaultCloseOperation(HIDE_ON_CLOSE);
			Aligner.align(50, 50, 511, 350, this);
			// setMinimumSize(new java.awt.Dimension(530, 350));
			// Запрет изменения размера
			setResizable(false);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			GridBagLayout gbl_contentPane = new GridBagLayout();
			gbl_contentPane.columnWidths = new int[] { 0, 170, 200, 0 };
			gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 70, 1, 0, 100,
					0 };
			gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, 1.0,
					Double.MIN_VALUE };
			gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
					0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
			contentPane.setLayout(gbl_contentPane);
			GridBagConstraints gbc_labelName = new GridBagConstraints();
			gbc_labelName.anchor = GridBagConstraints.EAST;
			gbc_labelName.insets = new Insets(0, 0, 5, 5);
			gbc_labelName.gridx = 0;
			gbc_labelName.gridy = 0;
			contentPane.add(getLabelName(), gbc_labelName);
			GridBagConstraints gbc_textFieldName = new GridBagConstraints();
			gbc_textFieldName.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldName.gridx = 1;
			gbc_textFieldName.gridy = 0;
			contentPane.add(getTextFieldName(), gbc_textFieldName);
			GridBagConstraints gbc_labelText = new GridBagConstraints();
			gbc_labelText.insets = new Insets(0, 0, 5, 0);
			gbc_labelText.gridx = 2;
			gbc_labelText.gridy = 0;
			contentPane.add(getLabelText(), gbc_labelText);
			GridBagConstraints gbc_labelDate = new GridBagConstraints();
			gbc_labelDate.anchor = GridBagConstraints.EAST;
			gbc_labelDate.insets = new Insets(0, 0, 5, 5);
			gbc_labelDate.gridx = 0;
			gbc_labelDate.gridy = 1;
			contentPane.add(getLabelDate(), gbc_labelDate);
			GridBagConstraints gbc_textFiedDate = new GridBagConstraints();
			gbc_textFiedDate.insets = new Insets(0, 0, 5, 5);
			gbc_textFiedDate.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFiedDate.gridx = 1;
			gbc_textFiedDate.gridy = 1;
			contentPane.add(getTextFieldDate(), gbc_textFiedDate);
			GridBagConstraints gbc_scrollPaneText = new GridBagConstraints();
			gbc_scrollPaneText.gridheight = 5;
			gbc_scrollPaneText.insets = new Insets(0, 0, 5, 5);
			gbc_scrollPaneText.fill = GridBagConstraints.BOTH;
			gbc_scrollPaneText.gridx = 2;
			gbc_scrollPaneText.gridy = 1;
			contentPane.add(getScrollPaneText(), gbc_scrollPaneText);
			GridBagConstraints gbc_labelTopic = new GridBagConstraints();
			gbc_labelTopic.anchor = GridBagConstraints.EAST;
			gbc_labelTopic.insets = new Insets(0, 0, 5, 5);
			gbc_labelTopic.gridx = 0;
			gbc_labelTopic.gridy = 2;
			contentPane.add(getLabelTopic(), gbc_labelTopic);
			GridBagConstraints gbc_comboBoxTopic = new GridBagConstraints();
			gbc_comboBoxTopic.insets = new Insets(0, 0, 5, 5);
			gbc_comboBoxTopic.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBoxTopic.gridx = 1;
			gbc_comboBoxTopic.gridy = 2;
			contentPane.add(getComboBoxTopic(), gbc_comboBoxTopic);
			GridBagConstraints gbc_labelHead = new GridBagConstraints();
			gbc_labelHead.gridwidth = 2;
			gbc_labelHead.insets = new Insets(0, 0, 5, 5);
			gbc_labelHead.gridx = 0;
			gbc_labelHead.gridy = 3;
			contentPane.add(getLabelHead(), gbc_labelHead);
			GridBagConstraints gbc_scrollPaneHead = new GridBagConstraints();
			gbc_scrollPaneHead.gridwidth = 2;
			gbc_scrollPaneHead.insets = new Insets(0, 5, 5, 5);
			gbc_scrollPaneHead.fill = GridBagConstraints.BOTH;
			gbc_scrollPaneHead.gridx = 0;
			gbc_scrollPaneHead.gridy = 4;
			contentPane.add(getScrollPaneHead(), gbc_scrollPaneHead);
			GridBagConstraints gbc_panelOk = new GridBagConstraints();
			gbc_panelOk.insets = new Insets(0, 5, 5, 5);
			gbc_panelOk.gridwidth = 3;
			gbc_panelOk.fill = GridBagConstraints.HORIZONTAL;
			gbc_panelOk.gridx = 0;
			gbc_panelOk.gridy = 6;
			contentPane.add(getPanelOk(), gbc_panelOk);
			GridBagConstraints gbc_scrollPaneError = new GridBagConstraints();
			gbc_scrollPaneError.gridwidth = 3;
			gbc_scrollPaneError.insets = new Insets(0, 5, 5, 5);
			gbc_scrollPaneError.fill = GridBagConstraints.BOTH;
			gbc_scrollPaneError.gridx = 0;
			gbc_scrollPaneError.gridy = 7;
			contentPane.add(getScrollError(), gbc_scrollPaneError);

			setVisible(true);
		}

		private JLabel getLabelName() {
			if (labelName == null) {
				labelName = new JLabel("Ваше имя");
			}
			return labelName;
		}

		private JLabel getLabelDate() {
			if (labelDate == null) {
				labelDate = new JLabel("Дата");
			}
			return labelDate;
		}

		private JLabel getLabelTopic() {
			if (labelTopic == null) {
				labelTopic = new JLabel("Рубрика");
			}
			return labelTopic;
		}

		private JTextField getTextFieldName() {
			if (textFieldName == null) {
				textFieldName = new JTextField();
				textFieldName.setColumns(10);
				textFieldName.setEditable(false);
				textFieldName.setText(name);
			}
			return textFieldName;
		}

		private JTextField getTextFieldDate() {
			if (textFieldDate == null) {
				textFieldDate = new JTextField();
				textFieldDate.setColumns(10);
				textFieldDate.setEditable(false);
				textFieldDate.setText(DateFormat.format(date));
			}
			return textFieldDate;
		}

		private JComboBox<String> getComboBoxTopic() {
			if (comboBoxTopic == null) {
				comboBoxTopic = new JComboBox<String>();
				int position = 0;
				for (int i = 0; i < topics.length; i++) {
					if (topic.equals(topics[i])) {
						position = i;
					}
					comboBoxTopic.addItem(topics[i]);
				}
				comboBoxTopic.setSelectedIndex(position);

			}
			return comboBoxTopic;
		}

		private JLabel getLabelHead() {
			if (labelHead == null) {
				labelHead = new JLabel("Заголовок");
			}
			return labelHead;
		}

		private JLabel getLabelText() {
			if (labelText == null) {
				labelText = new JLabel("Текст объявления");
			}
			return labelText;
		}

		private JScrollPane getScrollPaneText() {
			if (scrollPaneText == null) {
				scrollPaneText = new JScrollPane();
				scrollPaneText.setViewportView(getTextAreaText());
			}
			return scrollPaneText;
		}

		private JTextArea getTextAreaText() {
			if (textAreaText == null) {
				textAreaText = new JTextArea();
				textAreaText.setLineWrap(true);
				textAreaText.setWrapStyleWord(true);
				textAreaText.setText(text);
			}
			return textAreaText;
		}

		private JScrollPane getScrollPaneHead() {
			if (scrollPaneHead == null) {
				scrollPaneHead = new JScrollPane();
				scrollPaneHead.setViewportView(getTextAreaHead());
			}
			return scrollPaneHead;
		}

		private JTextArea getTextAreaHead() {
			if (textAreaHead == null) {
				textAreaHead = new JTextArea();
				textAreaHead.setLineWrap(true);
				textAreaHead.setWrapStyleWord(true);
				textAreaHead.setText(head);
			}
			return textAreaHead;
		}

		private JPanel getPanelOk() {
			if (panelOk == null) {
				panelOk = new JPanel();
				panelOk.add(getBtnOk());
			}
			return panelOk;
		}

		private JButton getBtnOk() {
			if (btnOk == null) {
				btnOk = new JButton("Ок");
				btnOk.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						String headString = getTextAreaHead().getText().trim();
						String textString = getTextAreaText().getText().trim();
						boolean head = Validator.checkHead(headString);
						boolean text = Validator.checkText(textString);
						if (head && text) {
							getTable().setValueAt(getTextFieldName().getText(),
									row, 0);
							getTable().setValueAt(date.toString(), row, 1);
							getTable().setValueAt(
									getComboBoxTopic().getSelectedItem(), row,
									2);
							getTable().setValueAt(headString, row, 3);
							getTable().setValueAt(textString, row, 4);
							getTextAreaTextDec().setText(textString);
							setVisible(false);
						} else {
							getTextAreaError().setText("");
							getTextAreaError().repaint();

							if (!head) {
								Formatter f = new Formatter();
								int lengthHead = headString.length();
								if (lengthHead <= 9) {
									getTextAreaError()
											.setText(
													f.format(HEAD_ERROR_MIN,
															lengthHead)
															.toString());
								} else if (lengthHead >= 31) {
									getTextAreaError()
											.setText(
													f.format(HEAD_ERROR_MAX,
															lengthHead)
															.toString());
								} else {
									getTextAreaError().setText(HEAD_ERROR);
								}
							}
							if (!text) {
								Formatter f = new Formatter();
								int lengthText = textString.length();
								if (lengthText <= 19) {
									getTextAreaError()
											.append(f.format(TEXT_ERROR_MIN,
													lengthText).toString());
								} else if (lengthText >= 401) {
									getTextAreaError()
											.append(f.format(TEXT_ERROR_MAX,
													lengthText).toString());
								} else {
									getTextAreaError().append(TEXT_ERROR);
								}
							}
						}
					}
				});
			}
			return btnOk;
		}

		private JScrollPane getScrollError() {
			if (scrollPaneError == null) {
				scrollPaneError = new JScrollPane();
				scrollPaneError.setViewportView(getTextAreaError());
			}
			return scrollPaneError;
		}

		private JTextArea getTextAreaError() {
			if (textAreaError == null) {
				textAreaError = new JTextArea();
				// textAreaHead.setLineWrap(true);
				// textAreaHead.setWrapStyleWord(true);
				textAreaError.setEditable(false);
				textAreaError.setForeground(Color.RED);
			}
			return textAreaError;
		}
	}
}
