package package1;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**********************************************************************
 * Class that handles the GUI for renting a DVD
 * 
 * @author Max Jensen and Monica Klosin
 * @version 1.0
 **********************************************************************/

public class RentDVDDialog extends JDialog implements ActionListener {

	/** JTextField for title user input */
	private JTextField titleTxt;

	/** JTextField for renter name user input */
	private JTextField renterTxt;

	/** JTextField for date rented on user input */
	private JTextField rentedOnTxt;

	/** JTextField for date due back user input */
	private JTextField dueBackTxt;

	/** JButton for ok */
	private JButton okButton;

	/** JButton for cancel */
	private JButton cancelButton;

	/** Boolean for whether or not the program should be closed */
	private boolean closeStatus;

	/** DVD that is being added */
	private DVD unit;

	/******************************************************************
	 * Default constructor that creates the JFrame as well as all of
	 * its contained GUI elements
	 * 
	 * @param parent
	 *            the parent JFrame
	 * @param d
	 *            the DVD to be created
	 *****************************************************************/
	public RentDVDDialog(JFrame parent, DVD d) {

		// Calls parent and create a 'modal' dialog
		super(parent, true);

		// Sets the title and size of the frame as well as close status
		setTitle("Rent a DVD:");
		closeStatus = false;
		setSize(400, 200);

		// Sets the passed DVD to unit
		unit = d;

		// Instantiates and displays JLabels and text fields (with
		// default text values) for each input area
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(6, 2));

		textPanel.add(new JLabel("Your Name:"));
		renterTxt = new JTextField("John Doe", 30);
		textPanel.add(renterTxt);

		textPanel.add(new JLabel("Title of DVD:"));
		titleTxt = new JTextField("Avengers", 30);
		textPanel.add(titleTxt);

		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		textPanel.add(new JLabel("Rented on Date: "));
		rentedOnTxt = new JTextField(df.format(date), 30);
		textPanel.add(rentedOnTxt);

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		date = c.getTime();

		textPanel.add(new JLabel("Due Back: "));
		dueBackTxt = new JTextField(df.format(date), 15);
		textPanel.add(dueBackTxt);

		getContentPane().add(textPanel, BorderLayout.CENTER);

		// Instantiates and displays the two buttons
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		// Sets size and visibility
		setSize(300, 300);
		setVisible(true);
	}

	/******************************************************************
	 * ActionPerformed class that handles button presses and dependent
	 * actions
	 * 
	 * @throws Exception
	 *             If the entered dates are invalid
	 *****************************************************************/
	public void actionPerformed(ActionEvent e) {

		// If OK is clicked, fill the object with entered info
		if (e.getSource() == okButton) {

			// Allows the frame to be closed
			closeStatus = true;

			SimpleDateFormat format =
					new SimpleDateFormat("MM/dd/yyyy");

			// Creates two calendars to manage the entered dates
			GregorianCalendar cal1 = new GregorianCalendar();
			GregorianCalendar cal2 = new GregorianCalendar();

			// Sets leniency of the two calendars
			cal1.setLenient(false);
			cal2.setLenient(false);

			// Attempts to parse the entered dates
			try {
				cal1.setTime(format.parse(rentedOnTxt.getText()));
				cal2.setTime(format.parse(dueBackTxt.getText()));

				// Checks to see that the dates are in the right format
				if (rentedOnTxt.getText().length() != 10
						|| rentedOnTxt.getText().charAt(2) != '/'
						|| rentedOnTxt.getText().charAt(5) != '/'
						|| dueBackTxt.getText().length() != 10
						|| dueBackTxt.getText().charAt(2) != '/'
						|| dueBackTxt.getText().charAt(5) != '/')
					throw new Exception();
				else {

					// If the due back date is later than the rented on
					// date, fills the unit object with the entered
					// information
					if (cal1.compareTo(cal2) <= 0) {
						unit.setNameOfRenter(renterTxt.getText());
						unit.setTitle(titleTxt.getText());
						unit.setBought(cal1);
						unit.setDueBack(cal2);
						dispose();
					}

					// If the due back date is earlier than the rented
					// on date displays a dialog box and doesn't add
					// the
					// info
					else
						JOptionPane.showMessageDialog(null,
								"Please enter a due date that is later than the rented on date");
				}
			}

			// Catches exceptions
			catch (Exception e1) {
				JOptionPane.showMessageDialog(null,
						"Please enter valid rented on and due back dates");
			}
		}

		// If cancel is clicked, allows the frame to be closed and
		// closes the frame
		if (e.getSource() == cancelButton) {
			unit = null;
			closeStatus = true;
			dispose();
		}
	}

	/******************************************************************
	 * Method that returns whether or not it is ok to close the frame
	 * 
	 * @return boolean whether or not it is alright to close the frame
	 *****************************************************************/
	public boolean closeOK() {
		return closeStatus;
	}
}
