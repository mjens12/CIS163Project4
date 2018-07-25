package package1;

import java.util.GregorianCalendar;

/**********************************************************************
 * Game class that inherits the DVD class and handles extra game
 * functionality
 * 
 * @author Max Jensen and Monica Klosin
 * @version 1.0
 *********************************************************************/
public class Game extends DVD {

	/** PlayerType of game */
	private PlayerType player;

	/******************************************************************
	 * Default constructor
	 *****************************************************************/
	public Game() {
	}

	/******************************************************************
	 * Getter for player type
	 * 
	 * @return PlayerType the player for the game being rented
	 *****************************************************************/
	public PlayerType getPlayer() {
		return player;
	}

	/******************************************************************
	 * Setter for player type
	 * 
	 * @param player
	 *            Player for the game being rented
	 *****************************************************************/
	public void setPlayer(PlayerType player) {
		this.player = player;
	}

	/******************************************************************
	 * Used when game is returned. This method checks to see if game
	 * was returned late or not. If game was returned before the
	 * dueBack date, the fee is $5. If the game was returned after the
	 * dueBack date, the fee is $15.
	 * 
	 * @param date
	 *            Date of when game was returned
	 *****************************************************************/
	public double getCost(GregorianCalendar date) {
		if (date.compareTo(dueBack) <= 0)
			return 5.0;
		else
			return 15.0;
	}
}
