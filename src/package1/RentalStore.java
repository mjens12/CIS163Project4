package package1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;

/**********************************************************************
 * RentalStore class that extends AbstractListModel, manages the list of
 * rented items
 * 
 * @author Max Jensen and Monica Klosin
 * @version 1.0
 *********************************************************************/
public class RentalStore extends AbstractListModel {

	/** ArrayList of DVDs that holds items in the store */
	private ArrayList<DVD> listDVDs;

	/******************************************************************
	 * Default constructor that calls the AbstractListModel constructor
	 * and creates the arraylist of DVDs
	 *****************************************************************/
	public RentalStore() {
		super();
		listDVDs = new ArrayList<DVD>();
	}

	/******************************************************************
	 * Method for adding a DVD to the arraylist, also triggers a GUI
	 * update to show new list contents
	 * 
	 * @param a
	 *            DVD to be added
	 *****************************************************************/
	public void add(DVD a) {
		listDVDs.add(a);
		fireIntervalAdded(this, 0, listDVDs.size());
	}

	/******************************************************************
	 * Method for removing a DVD from the arraylist, also triggers a GUI
	 * update to show new list contents
	 * 
	 * @param a
	 *            DVD to be added
	 *****************************************************************/
	public void remove(int a) {
		listDVDs.remove(a);
		fireIntervalAdded(this, 0, listDVDs.size());
	}

	/******************************************************************
	 * Method for getting a DVD from the list
	 * 
	 * @param i
	 *            Index of item to be grabbed
	 *****************************************************************/
	public DVD get(int i) {
		return listDVDs.get(i);
	}

	/******************************************************************
	 * Method for getting a DVD from the list and parsing its
	 * information into a string to be displayed
	 * 
	 * @param arg0
	 *            Index of item to be grabbed
	 * 
	 * @return Object a string with all the item's information
	 *****************************************************************/
	public Object getElementAt(int arg0) {

		DVD unit = listDVDs.get(arg0);

		// Formats the rented on and due back dates
		try {
			String rentedOnDateStr = DateFormat
					.getDateInstance(DateFormat.SHORT)
					.format(unit.getBought().getTime());

			String dueBackOnDateStr = DateFormat
					.getDateInstance(DateFormat.SHORT)
					.format(unit.getDueBack().getTime());

			// Creates the String for the item's info
			String line = "Name: "
					+ listDVDs.get(arg0).getNameOfRenter() + " ";

			// Adds info to the string
			line += "Title: " + unit.getTitle() + ", ";
			line += "Rented On: " + rentedOnDateStr + ", ";
			line += "Due Back: " + dueBackOnDateStr;

			// Adds additional info to the string if the item is a game
			if (unit instanceof Game)
				line += ", Player: " + ((Game) unit).getPlayer();

			// Returns the completed string
			return line;
		}

		// Catches exceptions
		catch (Exception ex) {
			return null;
		}
	}

	/******************************************************************
	 * Method for getting the size of the list of items
	 * 
	 * @return int Size of DVD list
	 *****************************************************************/
	public int getSize() {
		// return 5;
		return listDVDs.size();
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
			os.writeObject(listDVDs);
			os.close();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Error in saving db");

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

			listDVDs = (ArrayList<DVD>) is.readObject();
			fireIntervalAdded(this, 0, listDVDs.size() - 1);
			is.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error in loading db");
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
		for (int i = 0; i < listDVDs.size(); i++) {
			if (listDVDs.get(i).getDueBack().compareTo(lateDate) <= 0) {

				// If the item is late, pulls its info out and adds it
				// to the string
				try {
					DVD unit = listDVDs.get(i);
					String rentedOnDateStr = DateFormat
							.getDateInstance(DateFormat.SHORT)
							.format(unit.getBought().getTime());

					String dueBackOnDateStr = DateFormat
							.getDateInstance(DateFormat.SHORT)
							.format(unit.getDueBack().getTime());

					String line = "Name: "
							+ listDVDs.get(i).getNameOfRenter() + " ";

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
}
