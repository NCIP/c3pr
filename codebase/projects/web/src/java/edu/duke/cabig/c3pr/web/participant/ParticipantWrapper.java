/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.participant;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.apache.commons.collections15.list.LazyList;

import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.web.RaceCodeHolder;

public class ParticipantWrapper {

	private Participant participant;
	private List<RaceCodeHolder> raceCodeHolderList = LazyList.decorate(new ArrayList<RaceCodeHolder>(), new InstantiateFactory<RaceCodeHolder>(RaceCodeHolder.class));
	private List<Address> addresses = LazyList.decorate(new ArrayList<Address>(), new InstantiateFactory<Address>(Address.class));

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

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
		synchronizeAddresses();
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.getAddresses().clear();
		this.getAddresses().addAll(participant.getAddresses());
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
	
	private void synchronizeAddresses(){
		for(Address address : getAddresses()){
			// new address added to wrapper, add it to participant
			if(address.getId()== null){
				this.participant.getAddresses().add(address);
			} else {
				updateAddress(address,this.participant.getAddressById(address.getId()));
			}
		}
	}
	
	private void updateAddress(Address source, Address target){
		target.setStreetAddress(source.getStreetAddress());
		target.setCity(source.getCity());
		target.setStateCode(source.getStateCode());
		target.setPostalCode(source.getPostalCode());
		target.setCountryCode(source.getCountryCode());
		target.setStartDate(source.getStartDate());
		target.setEndDate(source.getEndDate());
	}
}
