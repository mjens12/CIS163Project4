package package1;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**********************************************************************
 * DVD class, implements Serializable and is inherited by the Game
 * class. Handles storing information about a DVD object
 * 
 * @author Max Jensen and Monica Klosin
 * @version 1.0
 *********************************************************************/
public class DVD implements Serializable {

	/** UID for serialization */
	private static final long serialVersionUID = 1L;

	/** GregorianCalendar for rented on date */
	protected GregorianCalendar bought;

	/** GregorianCalendar for due back date */
	protected GregorianCalendar dueBack;

	/** String for title of item */
	protected String title;

	/** String for name of renter */
	protected String nameOfRenter;

	/******************************************************************
	 * Default constructor
	 *****************************************************************/
	public DVD() {
	}

	/******************************************************************
	 * Constructor that accepts a rented on date, due back date, name of
	 * renter, and title of item and creates a DVD with all that
	 * information
	 * 
	 * @param bought
	 *            GregorianCalendar for the rented on date
	 * 
	 * @param dueBack
	 *            GregorianCalendar for the due back date
	 * 
	 * @param title
	 *            String for title of item
	 * 
	 * @param name
	 *            String for name of renter
	 * 
	 *****************************************************************/
	public DVD(GregorianCalendar bought, GregorianCalendar dueBack,
			String title, String name) {
		super();
		this.bought = bought;
		this.dueBack = dueBack;
		this.title = title;
		this.nameOfRenter = name;
	}

	/******************************************************************
	 * Getter for rented on date
	 * 
	 * @return GregorianCalendar Rented on date
	 *****************************************************************/
	public GregorianCalendar getBought() {
		return bought;
	}

	/******************************************************************
	 * Setter for rented on date
	 * 
	 * @param bought
	 *            GregorianCalendar Rented on date
	 *****************************************************************/
	public void setBought(GregorianCalendar bought) {
		this.bought = bought;
	}

	/******************************************************************
	 * Getter for due back date
	 * 
	 * @return GregorianCalendar Due back date
	 *****************************************************************/
	public GregorianCalendar getDueBack() {
		return dueBack;
	}

	/******************************************************************
	 * Setter for due back date
	 * 
	 * @param dueBack
	 *            Due back date
	 *****************************************************************/
	public void setDueBack(GregorianCalendar dueBack) {
		this.dueBack = dueBack;
	}

	/******************************************************************
	 * Getter for item title
	 * 
	 * @return String title of item
	 *****************************************************************/
	public String getTitle() {
		return title;
	}

	/******************************************************************
	 * Setter for item title
	 * 
	 * @param title
	 *            String title of item
	 *****************************************************************/
	public void setTitle(String title) {
		this.title = title;
	}

	/******************************************************************
	 * Getter for name of renter
	 * 
	 * @return String Name of renter
	 *****************************************************************/
	public String getNameOfRenter() {
		return nameOfRenter;
	}

	/******************************************************************
	 * Setter for name of renter
	 * 
	 * @param nameOfRenter
	 *            String Name of renter
	 *****************************************************************/
	public void setNameOfRenter(String nameOfRenter) {
		this.nameOfRenter = nameOfRenter;
	}

	/******************************************************************
	 * Used when DVD is returned. This method checks to see if DVD was
	 * returned late or not. If DVD was returned before the dueBack
	 * date, the fee is $1.20. If the DVD was returned after the dueBack
	 * date, the fee is $3.20.
	 * 
	 * @param date
	 *            Date of when game was returned
	 *****************************************************************/
	public double getCost(GregorianCalendar date) {
		if (date.compareTo(dueBack) <= 0)
			return 1.20;
		else
			return 3.20;
	}
}
