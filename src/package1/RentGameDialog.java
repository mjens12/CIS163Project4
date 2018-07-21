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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**********************************************************************
 * Class that handles the GUI for renting a Game
 * 
 * @author Max Jensen and Monica Klosin
 * @version 1.0
 *********************************************************************/
public class RentGameDialog extends JDialog implements ActionListener {

	/** Title of Game Text Field */
	private JTextField titleTxt;

	/** Renter's name Text Field */
	private JTextField renterTxt;

	/** Text Field for date game was rented */
	private JTextField rentedOnTxt;

	/** Text Field for due back date */
	private JTextField dueBackTxt;

	/** Text Field of console */
	private JTextField playerTxt;

	/** JButton to confirm game rental */
	private JButton okButton;

	/** JButton to Cancel game rental */
	private JButton cancelButton;

	/** Boolean to check to close Game rent textBox or not */
	private boolean closeStatus;

	/** Game that is being added */
	private Game unit;

	private JComboBox playerList;

	/** Array to hold game console options */
	private String[] playerOptions = { "Xbox360", "XBox1", "PS4", "WiiU", "NintendoSwitch" };

	/******************************************************************
	 * Default Constructor creates JFrame box as well as its GUI elements
	 * 
	 * @param parent
	 *            the parent JFrame
	 * @param d
	 *            The Game to be created
	 *****************************************************************/
	public RentGameDialog(JFrame parent, Game d) {

		// Calls parent and create a 'modal' dialog
		super(parent, true);

		// Sets the title and size of the frame as well as close status
		setTitle("Rent a Game:");
		closeStatus = false;
		setSize(400, 200);

		// Sets the passed DVD to unit
		unit = d;

		// Prevents user from closing window
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		// Instantiates and displays JLabels and text fields (with
		// default text values) for each input area
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(6, 2));

		textPanel.add(new JLabel("Your Name:"));
		renterTxt = new JTextField("John Doe", 30);
		textPanel.add(renterTxt);

		textPanel.add(new JLabel("Title of Game:"));
		titleTxt = new JTextField("Katamari Damacy", 30);
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

		textPanel.add(new JLabel("Due Back:"));
		dueBackTxt = new JTextField(df.format(date), 15);
		textPanel.add(dueBackTxt);

		playerList = new JComboBox(playerOptions);
		playerList.setSelectedIndex(0);
		playerList.addActionListener(this);

		textPanel.add(new JLabel("Player Type:"));
		textPanel.add(playerList);

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
	 * ActionPreformed class that handles button presses and other actions
	 * 
	 * @throws Exception
	 *             If the entered dates are invaid
	 *****************************************************************/

	public void actionPerformed(ActionEvent e) {

		// If OK is clicked, fill the object with entered info
		if (e.getSource() == okButton) {

			// Allows the frame to be closed
			closeStatus = true;

			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

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
				if (rentedOnTxt.getText().length() != 10 || rentedOnTxt.getText().charAt(2) != '/'
						|| rentedOnTxt.getText().charAt(5) != '/' || dueBackTxt.getText().length() != 10
						|| dueBackTxt.getText().charAt(2) != '/' || dueBackTxt.getText().charAt(5) != '/')
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
					}

					// If the due back date is earlier than the rented
					// on date displays a dialog box and doesn't add the
					// info
					else
						JOptionPane.showMessageDialog(null,
								"Please enter a due date that is later than the rented on date");
				}
			}

			// Catches exceptions
			catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Please enter valid rented on and due back dates");
			}

			// Attempts to parse the playertype field into one of the
			// enum playertype options, if correct, adds that
			// information to the game, if not, throws an exception that
			// is caught below and that displays a message for the user
			// telling them to enter a proper player
			try {
				PlayerType p = PlayerType.valueOf((String) playerList.getSelectedItem());
				unit.setPlayer(p);
				dispose();
			} catch (Exception exc) {

			}

			// If cancel is clicked, allows the frame to be closed and
			// closes the frame
			if (e.getSource() == cancelButton) {
				closeStatus = true;
				dispose();
			}
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
