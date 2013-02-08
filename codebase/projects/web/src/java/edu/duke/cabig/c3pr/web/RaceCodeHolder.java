/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web;

import edu.duke.cabig.c3pr.constants.RaceCodeEnum;

public class RaceCodeHolder {
	private RaceCodeEnum raceCode ;

	/**
	 * @param raceCode the raceCode to set
	 */
	public void setRaceCode(RaceCodeEnum raceCode) {
		this.raceCode = raceCode;
	}

	/**
	 * @return the raceCode
	 */
	public RaceCodeEnum getRaceCode() {
		return raceCode;
	}
}
