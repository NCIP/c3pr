package edu.duke.cabig.c3pr.utils.web.navigation;

// TODO: Auto-generated Javadoc
/**
 * User: IO Date: Jun 3, 2008 5:22:55 PM.
 */

public class SubTask extends Task {
	
	/** The display. This controls whether a subtask should be displayed in the UI or not. Used in header.tag */
	private boolean display = true;

	
	/**
	 * Gets the display.
	 * 
	 * @return the display
	 */
	public boolean getDisplay() {
		return display;
	}

	/**
	 * Sets the display.
	 * 
	 * @param display the new display
	 */
	public void setDisplay(boolean display) {
		this.display = display;
	}

}
