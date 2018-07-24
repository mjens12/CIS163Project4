package package1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**********************************************************************
 * Class that handles the GUI of the rentalstore
 * 
 * @author Max Jensen and Monica Klosin
 * @version 1.0
 **********************************************************************/

public class RentalStoreGUI extends JFrame implements ActionListener {

	/** Holds menu bar */
	private JMenuBar menus;

	/** File menu */
	private JMenu fileMenu;

	/** Action menu */
	private JMenu actionMenu;

	/** Menu item for opening serialized object */
	private JMenuItem openSerItem;

	/** Menu item for exit */
	private JMenuItem exitItem;

	/** Menu item for undo */
	private JMenuItem undoItem;

	/** Menu item for saving serialized object */
	private JMenuItem saveSerItem;

	/**
	 * Menu item for opening text object (not used in proj3, for proj4)
	 */
	private JMenuItem openTextItem;

	/**
	 * Menu item for saving text object (not used in proj3, for proj4)
	 */
	private JMenuItem saveTextItem;

	/** Menu item for renting a DVD */
	private JMenuItem rentDVD;

	/** Menu item for renting a game */
	private JMenuItem rentGame;

	/** Menu item for returning an item */
	private JMenuItem returnItem;

	/**
	 * Menu item for checking the lateness of an item based on user
	 * entered date
	 */
	private JMenuItem lateItem;

	/** Holds the RentalStore list with items */
	private RentalStore linkedList;

	/** Holds the JList */
	private JTable JTableArea;

	/** Scroll pane */
	private JScrollPane scrollList;

	private Object[][] data;

	/******************************************************************
	 * Default constructor that creates and arranges all the GUI
	 * elements
	 *****************************************************************/
	public RentalStoreGUI() {

		// Adds menu bar and menu items
		menus = new JMenuBar();
		fileMenu = new JMenu("File");
		actionMenu = new JMenu("Action");
		openSerItem = new JMenuItem("Open File From Serializable");
		exitItem = new JMenuItem("Exit");
		saveSerItem = new JMenuItem("Save File As Serializable");
		openTextItem = new JMenuItem("Open File From Text");
		saveTextItem = new JMenuItem("Save File As Text");
		rentDVD = new JMenuItem("Rent DVD");
		rentGame = new JMenuItem("Rent Game");
		returnItem = new JMenuItem("Return");
		lateItem = new JMenuItem("Check Days Late");
		undoItem = new JMenuItem("Undo");

		// Adds items menus
		fileMenu.add(openSerItem);
		fileMenu.add(saveSerItem);
		fileMenu.add(saveTextItem);
		fileMenu.add(openTextItem);
		fileMenu.add(exitItem);
		actionMenu.add(rentDVD);
		actionMenu.add(rentGame);
		actionMenu.add(returnItem);
		actionMenu.add(lateItem);
		actionMenu.add(undoItem);

		// Adds menus to bar
		menus.add(fileMenu);
		menus.add(actionMenu);

		// Adds actionListeners
		openSerItem.addActionListener(this);
		saveSerItem.addActionListener(this);
		exitItem.addActionListener(this);
		rentDVD.addActionListener(this);
		rentGame.addActionListener(this);
		returnItem.addActionListener(this);
		lateItem.addActionListener(this);
		undoItem.addActionListener(this);
		saveTextItem.addActionListener(this);
		openTextItem.addActionListener(this);

		// Sets the menus
		setJMenuBar(menus);

		// DefaultTableModel model = new DefaultTableModel(
		// tableColumnNames, 0);

		// Sets close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Update

		linkedList = new RentalStore();
		JTableArea = new JTable(linkedList);
		JTableArea
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollList = new JScrollPane(JTableArea);

		add(scrollList);
		pack();

		// Sets visibility and size
		setVisible(true);
		setSize(600, 500);
	}

	/******************************************************************
	 * ActionPerformed class that handles button presses and dependent
	 * actions
	 * 
	 * @throws Exception
	 *             If the entered dates are formatted incorrectly
	 *****************************************************************/
	public void actionPerformed(ActionEvent e) {

		Object comp = e.getSource();

		// If open button is pressed, handles opening a serialized save
		if (openSerItem == comp) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showOpenDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile()
						.getAbsolutePath();
				if (openSerItem == comp)
					linkedList.loadFromSerializable(filename);
			}
		}

		if (openTextItem == comp) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showOpenDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile()
						.getAbsolutePath();
				if (openTextItem == comp)
					linkedList.loadFromText(filename);
			}
		}

		if (saveTextItem == comp) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showSaveDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile()
						.getAbsolutePath();
				if (saveTextItem == e.getSource())
					linkedList.saveAsText(filename);
			}
		}

		// If save button is pressed, handles saving a serialized object
		if (saveSerItem == comp) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showSaveDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile()
						.getAbsolutePath();
				if (saveSerItem == e.getSource())
					linkedList.saveAsSerializable(filename);
			}
		}

		if (e.getSource() == undoItem) {
			linkedList.undo();
		}

		// If exit is pressed, exits
		if (e.getSource() == exitItem) {
			System.exit(1);
		}

		// If rentDVD is pressed, creates a new DVD, runs the
		// RentDVDDialog, and adds the DVD to the list
		if (e.getSource() == rentDVD) {
			DVD dvd = new DVD();
			RentDVDDialog dialog = new RentDVDDialog(this, dvd);
			if (dvd.getTitle() == null)
				return;
			else
				linkedList.add(dvd);
		}

		// If rentGame is pressed, creates a new game, runs the
		// RentGameDialog, and adds the game to the list
		if (e.getSource() == rentGame) {
			Game game = new Game();
			RentGameDialog dialog = new RentGameDialog(this, game);
			if (game.getTitle() == null)
				return;
			else
				linkedList.add(game);
		}

		// If return is pressed, prompts for return date, and handles
		// removing the returned item from the list if everything is
		// entered correctly
		if (returnItem == e.getSource()) {

			// Gets the index of the selected item
			int index = JTableArea.getSelectedRow();
			// rentallist.GetSelectedRow;
			//

			// Lets the user know if they haven't selected an item
			if (index < 0) {
				JOptionPane.showMessageDialog(null,
						"Please select the DVD or game you are returning");
			}

			// Once item is selected, prompts the user for a return date
			else {
				GregorianCalendar date = new GregorianCalendar();
				date.setLenient(false);
				String inputDate = JOptionPane
						.showInputDialog("Enter return date: ");
				DVD unit = linkedList.get(index);

				// If there is something entered in the return date
				// input, continues with the process of returning
				if (inputDate != null) {
					SimpleDateFormat df = new SimpleDateFormat(
							"MM/dd/yyyy");

					// Attempts to parse the return date
					try {
						Date newDate = df.parse(inputDate);
						date.setTime(newDate);

						// Checks to see that the date is in the right
						// format
						if (inputDate.length() != 10
								|| inputDate.charAt(2) != '/'
								|| inputDate.charAt(5) != '/')
							throw new Exception();

						else {

							// If the return date is before the checked
							// out date, lets the user know and doesn't
							// continue
							if (date.compareTo(linkedList.get(index)
									.getBought()) < 0) {
								JOptionPane.showMessageDialog(null,
										"You can not return an item before it was checked out! Please try again");
							}

							// If the return date is valid, lets the
							// user know that the item was successfully
							// returned and how much money they owe, and
							// removes the returned item from the list
							else {
								JOptionPane.showMessageDialog(null, ""
										+ unit.getNameOfRenter()
										+ ", thank you for returning "
										+ unit.getTitle()
										+ ". You owe: "
										+ unit.getCost(date)
										+ " dollars");
								linkedList.remove(index);
							}
						}
					} catch (Exception pe) {
						JOptionPane.showMessageDialog(null,
								"Could not parse input date! Please try again");
					}

				}
			}

		}

		// If the lateItem button is pressed, prompts the user for a
		// date to check lateness of items, if the date is valid,
		// displays the late items in a new dialog box, as well as how
		// many days late they are
		if (e.getSource() == lateItem) {

			// Creates a new calendar to compare dates and sets its
			// leniency
			GregorianCalendar date = new GregorianCalendar();
			date.setLenient(false);

			// Prompts user for the date they want
			String inputDate = JOptionPane.showInputDialog(
					"Please enter the date you are interested in: ");

			// If there is something entered in the field continues as
			// normal
			if (inputDate != null) {
				SimpleDateFormat df = new SimpleDateFormat(
						"MM/dd/yyyy");

				// Attempts to parse the date entered, if it cannot be
				// parsed, throws a ParseException that notifies the
				// user of this
				try {
					Date newDate = df.parse(inputDate);
					date.setTime(newDate);

					// Checks to see that the date is in the right
					// format
					if (inputDate.length() != 10
							|| inputDate.charAt(2) != '/'
							|| inputDate.charAt(5) != '/')
						throw new Exception();

					else {

						// If there are no items late as of the date
						// entered, displays an appropriate message
						if (linkedList.getLate(date).equals(""))
							JOptionPane.showMessageDialog(null,
									"No rented items are late as of "
											+ DateFormat
													.getDateInstance(
															DateFormat.SHORT)
													.format(newDate));

						// If there are late items as of the date
						// entered, displays those items, as well as the
						// number of days late they will be
						else
							JOptionPane.showMessageDialog(null,
									"Below are the items that will be late as of "
											+ DateFormat
													.getDateInstance(
															DateFormat.SHORT)
													.format(newDate)
											+ ": \n"
											+ linkedList.getLate(date));
					}
				}

				// Catches exceptions
				catch (Exception pe) {
					JOptionPane.showMessageDialog(null,
							"Could not parse input date! Please try again");
				}
			}

		}

	}

	/******************************************************************
	 * Main class that runs the program
	 *****************************************************************/
	public static void main(String[] args) {
		new RentalStoreGUI();
	}

}
