/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.participant;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.apache.commons.collections15.list.LazyList;

import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.web.RaceCodeHolder;

public class ParticipantWrapper {

	private Participant participant;
	private List<RaceCodeHolder> raceCodeHolderList = LazyList.decorate(new ArrayList<RaceCodeHolder>(), new InstantiateFactory<RaceCodeHolder>(RaceCodeHolder.class));
	

	/**
	 * @return the raceCodeHolderList
	 */
	public List<RaceCodeHolder> getRaceCodeHolderList() {
		return raceCodeHolderList;
	}

	/**
	 * @param raceCodeHolderList the raceCodeHolderList to set
	 */
	public void setRaceCodeHolderList(List<RaceCodeHolder> raceCodeHolderList) {
		this.raceCodeHolderList = raceCodeHolderList;
	}

	public void addRaceCodeHolder(RaceCodeHolder raceCodeHolder) {
		getRaceCodeHolderList().add(raceCodeHolder);
	}
	
	public ParticipantWrapper() {
	}

	public ParticipantWrapper(Participant participant) {
		super();
		this.participant = participant;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}
	
	@Transient
	public List<RaceCodeEnum> getRaceCodesFromHolder(){
		List<RaceCodeEnum> raceCodeList = new ArrayList<RaceCodeEnum>();
		for(RaceCodeHolder raceCodeHolder : raceCodeHolderList){
			if(raceCodeHolder != null && raceCodeHolder.getRaceCode() != null){
				raceCodeList.add(raceCodeHolder.getRaceCode());
			}
		}
		return raceCodeList ;
	}
}
