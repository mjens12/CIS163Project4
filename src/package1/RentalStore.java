package package1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**********************************************************************
 * RentalStore class that extends AbstractListModel, manages the list of
 * rented items
 * 
 * @author Max Jensen and Monica Klosin
 * @version 1.0
 *********************************************************************/
public class RentalStore extends AbstractTableModel {

	private MyDoubleLinkedList<DVD> linkedListDVDs;

	private MyDoubleLinkedList<DVD> undoStack;

	// true is add, false is remove
	private MyDoubleLinkedList<Boolean> addOrRemove;

	private String[] tableColumnNames = { "Renter Name", "Title",
			"Date Rented", "Due Date", "Player Type" };

	/** Array to hold game console options */
	private String[] playerOptions = { "Xbox360", "XBox1", "PS4",
			"WiiU", "NintendoSwitch" };

	/******************************************************************
	 * Default constructor that calls the AbstractListModel constructor
	 * and creates the arraylist of DVDs
	 *****************************************************************/
	public RentalStore() {
		super();
		undoStack = new MyDoubleLinkedList<DVD>();
		addOrRemove = new MyDoubleLinkedList<Boolean>();
		linkedListDVDs = new MyDoubleLinkedList<DVD>();
	}

	/******************************************************************
	 * Method for adding a DVD to the arraylist, also triggers a GUI
	 * update to show new list contents
	 * 
	 * @param a
	 *            DVD to be added
	 *****************************************************************/
	public void add(DVD a) {
		linkedListDVDs.add(a);
		undoStack.add(a);
		addOrRemove.add(true);
		fireTableDataChanged();
	}

	/******************************************************************
	 * Method for removing a DVD from the arraylist, also triggers a GUI
	 * update to show new list contents
	 * 
	 * @param a
	 *            DVD to be added
	 *****************************************************************/
	public void remove(int a) {
		undoStack.add(linkedListDVDs.get(a));
		addOrRemove.add(false);
		linkedListDVDs.remove(a);
		fireTableDataChanged();
	}

	public void undo() {
		if (addOrRemove.size() == 0)
			return;
		else {
			if (addOrRemove.get(addOrRemove.size() - 1) == true) {
				linkedListDVDs.remove(linkedListDVDs.size() - 1);
				addOrRemove.remove(addOrRemove.size() - 1);
				undoStack.remove(undoStack.size() - 1);
				fireTableDataChanged();
				return;
			}
			if (addOrRemove.get(addOrRemove.size() - 1) == false) {
				linkedListDVDs.add(undoStack.get(undoStack.size() - 1));
				addOrRemove.remove(addOrRemove.size() - 1);
				undoStack.remove(undoStack.size() - 1);
				fireTableDataChanged();
				return;
			}
		}
	}

	/******************************************************************
	 * Method for saving the list as a serializable object
	 * 
	 * @param filename
	 *            Filename of saved file
	 *****************************************************************/
	public void saveAsSerializable(String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(linkedListDVDs);
			os.close();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Error in saving db");
			ex.printStackTrace();
		}
	}

	/******************************************************************
	 * Method for loading the list as a serializable object
	 * 
	 * @param filename
	 *            Filename of loaded file
	 *****************************************************************/
	public void loadFromSerializable(String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream is = new ObjectInputStream(fis);

			linkedListDVDs = (MyDoubleLinkedList<DVD>) is.readObject();
			fireTableDataChanged();
			is.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error in loading db");
		}
	}

	public void saveAsText(String filename) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(
					new BufferedWriter(new FileWriter(filename)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < linkedListDVDs.size(); i++) {
			if (linkedListDVDs.get(i).getClass() == Game.class) {
				out.println("game");
			}
			if (linkedListDVDs.get(i).getClass() == DVD.class) {
				out.println("dvd");
			}
			out.println(linkedListDVDs.get(i).getNameOfRenter());
			out.println(linkedListDVDs.get(i).getTitle());
			String rentedOnDateStr = DateFormat
					.getDateInstance(DateFormat.SHORT)
					.format(linkedListDVDs.get(i).getBought()
							.getTime());
			out.println(rentedOnDateStr);
			String dueBackOnDateStr = DateFormat
					.getDateInstance(DateFormat.SHORT)
					.format(linkedListDVDs.get(i).getDueBack()
							.getTime());
			out.println(dueBackOnDateStr);
			if (linkedListDVDs.get(i).getClass() == Game.class) {
				out.println(((Game) linkedListDVDs.get(i)).getPlayer());
			}
		}
		out.close();

	}

	public void loadFromText(String filename) {

		try {
			Scanner fileReader = new Scanner(new File(filename));

			MyDoubleLinkedList<DVD> newDLL = new MyDoubleLinkedList<DVD>();

			SimpleDateFormat format = new SimpleDateFormat(
					"MM/dd/yyyy");

			// Creates two calendars to manage the entered dates
			GregorianCalendar cal1 = new GregorianCalendar();
			GregorianCalendar cal2 = new GregorianCalendar();

			// Sets leniency of the two calendars
			cal1.setLenient(false);
			cal2.setLenient(false);

			while (fileReader.hasNext()) {

				if (fileReader.nextLine().equals("game")) {
					Game gToAdd = new Game();
					gToAdd.setNameOfRenter(fileReader.nextLine());
					gToAdd.setTitle(fileReader.nextLine());
					cal1.setTime(format.parse(fileReader.nextLine()));
					gToAdd.setBought(cal1);
					cal2.setTime(format.parse(fileReader.nextLine()));
					gToAdd.setDueBack(cal2);
					gToAdd.setPlayer(
							PlayerType.valueOf(fileReader.nextLine()));
					newDLL.add(gToAdd);

				} else {
					DVD toAdd = new DVD();
					toAdd.setNameOfRenter(fileReader.nextLine());
					toAdd.setTitle(fileReader.nextLine());
					cal1.setTime(format.parse(fileReader.nextLine()));
					toAdd.setBought(cal1);
					cal2.setTime(format.parse(fileReader.nextLine()));
					toAdd.setDueBack(cal2);
					newDLL.add(toAdd);
				}

			}
			linkedListDVDs = newDLL;
			fireTableDataChanged();
			fileReader.close();
		}

		// problem reading the file
		catch (Exception error) {
			System.out.println("Oops! Reading the file went wrong");
			error.printStackTrace();
		}

	}

	/******************************************************************
	 * Method for checking if any items will be considered late after a
	 * user provided date. Returns a string of "" if no items will be
	 * late, returns items, their information, and their number of days
	 * late on sepparate lines if there are late items
	 * 
	 * @param lateDate
	 *            The date to test
	 *****************************************************************/
	public String getLate(GregorianCalendar lateDate) {
		// Default string
		String lateThings = "";

		// Runs through the list of DVDs and checks to see if any of
		// them will be late based on the passed date
		for (int i = 0; i < linkedListDVDs.size(); i++) {
			if (linkedListDVDs.get(i).getDueBack()
					.compareTo(lateDate) <= 0) {

				// If the item is late, pulls its info out and adds it
				// to the string
				try {
					DVD unit = linkedListDVDs.get(i);
					String rentedOnDateStr = DateFormat
							.getDateInstance(DateFormat.SHORT)
							.format(unit.getBought().getTime());

					String dueBackOnDateStr = DateFormat
							.getDateInstance(DateFormat.SHORT)
							.format(unit.getDueBack().getTime());

					String line = "Name: "
							+ linkedListDVDs.get(i).getNameOfRenter()
							+ " ";

					line += "Title: " + unit.getTitle() + ", ";
					line += "Rented On: " + rentedOnDateStr + ", ";
					line += "Due Back: " + dueBackOnDateStr;

					// If the late item is a game, adds the type of
					// console to the string
					if (unit instanceof Game)
						line += ", Player: "
								+ ((Game) unit).getPlayer();

					// Adds the number of days late to the string
					line += (", "
							+ daysBetween(lateDate, unit.getDueBack())
							+ " days late");
					lateThings += (line + "\n");

				}

				// Catches exceptions
				catch (Exception ex) {

				}
			}
		}

		// Returns the completed string
		return lateThings;
	}

	/******************************************************************
	 * Method for computing the number of days between two dates. Used
	 * to compute how many days late an item will be
	 * 
	 * @param d1
	 *            First date to be compared
	 * @param d2
	 *            Second date to be compared
	 *****************************************************************/
	private int daysBetween(GregorianCalendar d1,
			GregorianCalendar d2) {
		return (int) ((d1.getTimeInMillis() - d2.getTimeInMillis())
				/ (1000 * 60 * 60 * 24));
	}

	public int getRowCount() {

		return linkedListDVDs.size();
	}

	public int getColumnCount() {
		return 5;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		DVD temp = linkedListDVDs.get(rowIndex);
		try {
			if (columnIndex == 0) {
				return temp.getNameOfRenter();
			}

			if (columnIndex == 1) {
				return temp.getTitle();
			}

			if (columnIndex == 2) {

				String rentedOnDateStr = DateFormat
						.getDateInstance(DateFormat.SHORT)
						.format(temp.getBought().getTime());
				return rentedOnDateStr;
			}

			if (columnIndex == 3) {

				String dueBackDateStr = DateFormat
						.getDateInstance(DateFormat.SHORT)
						.format(temp.getBought().getTime());
				return dueBackDateStr;
			}

			if (columnIndex == 4 && temp.getClass() == Game.class) {
				return ((Game) temp).getPlayer();
			} else
				return "";
		} catch (Exception e) {
			return null;
		}

	}

	public DVD get(int index) {
		return linkedListDVDs.get(index);
	}

	public String getColumnName(int column) {
		return tableColumnNames[column];
	}
}
