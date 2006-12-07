/**
 * 
 */
package edu.duke.cabig.c3pr.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.duke.cabig.c3pr.dao.ProtocolDao;
import edu.duke.cabig.c3pr.domain.Amendment;
import edu.duke.cabig.c3pr.domain.Protocol;
import edu.duke.cabig.c3pr.domain.ProtocolArm;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.service.ProtocolService;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.SystemManager;
import edu.duke.cabig.c3pr.utils.security.User;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElement;


/**
 * @author Priyatam
 *
 */
public class ProtocolServiceImpl implements ProtocolService {
	ProtocolDao protocolDao;
	
	public List<Protocol> getAllProtocols() throws Exception
    {
    	return protocolDao.getAll();
    }
	
	/**
	  Saves a Study	
	*/
	public void saveStudy(Study study) throws Exception {
		protocolDao.saveStudy(study);		
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.service.ProtocolService#createProtocol(edu.duke.cabig.c3pr.domain.Protocol)
	 */
	public void saveProtocol(Protocol protocol) throws Exception {
		protocolDao.saveProtocol(protocol);		
	}
	
	public void addProtocol(Protocol protocol) throws Exception {

		ArrayList errors = null;

		// Validate protocol,Arm,Amendment details
		errors = validateProtocol(protocol);
		if (errors.size() > 0) {
			StringBuffer errormessage = new StringBuffer();
			for (int i = 0; i < errors.size(); i++) {
				errormessage.append(errors.get(i) + "\n");
			}
			//throw new InvalidProtocolException(errormessage.toString());
		}
		// Protocol is valid.

		// Attempt to save protocol
		protocolDao.saveProtocol(protocol);
		// protocol saved

		
		ProtectionElement protectionElement = new ProtectionElement();
		protectionElement.setProtectionElementName(protocol.getPrimaryProtocolIdentifierAsString());
		protectionElement.setProtectionElementDescription(User.PROTECTION_ELEMENT_TYPE_PROTOCOL);
		protectionElement.setObjectId(String.valueOf(protocol.getId()));
		SystemManager.getUserProvisioningManager().createProtectionElement(protectionElement);
		
		/* TODO 
		 * Complete this code if you want to add a protection group
		 * 
		Set pes = new HashSet();
		pes.add(protectionElement);
		
		ProtectionGroup pg = new ProtectionGroup();
		pg.setProtectionGroupName(protocol.getPrimaryProtocolIdentifierAsString());
		pg.setProtectionElements(pes);
		SystemManager.getUserProvisioningManager().createProtectionGroup(pg); */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.c3pr.protocol.ProtocolManager#amendProtocol(gov.nih.nci.c3pr.domain.protocol.Amendment)
	 */
	public void amendProtocol(Amendment amendment) throws Exception {
		
		ArrayList errors = null;
		// Validate Amendment details
		errors = validateProtocolAmendment(amendment,false);

		if (errors.size() > 0) {
			StringBuffer errormessage = new StringBuffer();
			for (int i = 0; i < errors.size(); i++) {
				errormessage.append(errors.get(i) + "\n");
			}
		//	throw new InvalidProtocolException(errormessage.toString());
		}
		// Protocol is valid.

		// Attempt to amend protocol
		protocolDao.amendProtocol(amendment);
		// protocol saved

	}

	

	/**
	 * validateProtocol method validates the presence of mandatory Protocol Information
	 * 
	 * @param protocol
	 * @return ArrayList
	 */
	@SuppressWarnings("unchecked")
	private ArrayList validateProtocol(Protocol protocol) throws Exception {
		/*
		 * Check if minimum required valid data for protocol,arm,amendment is
		 * available.
		 * 
		 * Protocol information - Accrual Ceiling - CC_Protocol - Amendment -
		 * Amendment Date - Branch Code - Institute Code - Sponsor Code -
		 * Randomized - Blinded - Design - Open Date - EC Required
		 */

		ArrayList errors = new ArrayList();
		
		Amendment currentAmendment = protocol.getCurrentAmendment();

		
		if (StringUtils.isBlank(protocol.getPrimaryProtocolIdentifierAsString())) {
			errors.add("CC_Protocol not available for Protocol");
		}
		/*if (protocol.getProtocolSponsor() instanceof ProtocolInstitution) {
			if (StringUtils.isBlank(protocol.getProtocolSponsor().getInstitution().getCode())) {
				errors.add("Sponsor code  not available for Protocol");
			}
		} else {
			errors.add("Sponsor code  not available for Protocol");
		}*/
		// Validate Amendment
		ArrayList amendmentErrors = validateProtocolAmendment(currentAmendment,true);
		if (amendmentErrors != null && amendmentErrors.size() > 0) {
			errors.addAll(amendmentErrors);
		}
		if (StringUtils.isBlank(protocol.getOpenDateAsString())) {
			errors.add("Open Date not available for Protocol");
		}
		// Branch Code, Description
		if(protocol.getProtocolBranch()!=null && protocol.getProtocolBranch().getInstitution()!=null){
			if (StringUtils.isBlank(protocol.getProtocolBranch().getInstitution().getCode())) {
				errors.add("Branch Code not available for Protocol");
			}
			if (StringUtils.isBlank(protocol.getProtocolBranch().getInstitution().getName())) {
				errors.add("Branch Name not available for Protocol");
			}			
		}else{
			errors.add("Branch Code not available for Protocol");
			errors.add("Branch Name not available for Protocol");
		}
		// Institute Code, Description
		if(protocol.getPrimaryProtocolIdentifier()!=null){
			if (StringUtils.isBlank(protocol.getPrimaryProtocolIdentifier().getProtocolIdentifierCode())) {
				errors.add("Institute code  not available for Protocol");
			}
		}else{
			errors.add("Institute code  not available for Protocol");
		}
		
		
		// Validate Arms.
		ArrayList armErrors = validateProtocolArm(protocol);
		if (armErrors != null && armErrors.size() > 0) {
			errors.addAll(armErrors);
		}
		
		

		return errors;
	}

	/**
	 * @param cmamendprotocol
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ArrayList validateProtocolAmendment(Amendment amendment, boolean allowBlankAmendment) {
		ArrayList errors = new ArrayList();


		
		if(amendment==null){
			 errors.add("Amendment information not available for Protocol Amendment");
		}else{
			 if (StringUtils.isBlank(amendment.getAccrualCeiling().toString())) {
					errors.add("Accrual_Ceiling not available for Protocol Amendment");
			 }
/* 
 Amendment field can be empty. Amendment date is used to identify amendments.
			 if (StringUtils.isBlank(amendment.getAmendment())) {
					errors.add("Amendment not available for Protocol Amendment");
				}
 */
			if(amendment.getDate()==null){
				errors.add("Amendment Date not available for Protocol Amendment");
			}else{
				 if(StringUtils.isBlank(amendment.getDate().toString())) {
						errors.add("Amendment Date not available for Protocol Amendment");
				}
			}
			if(StringUtils.isBlank(amendment.getEligibilityChecklistRequiredFlag())) {
			  errors.add("EC Required not available for Protocol Amendment"); 
			}
			if
			  (StringUtils.isBlank(amendment.getRandomized())) {
			  errors.add("Randomized not available for Protocol Amendment");
			 }
			if(StringUtils.isBlank(amendment.getBlinded())) {
				errors.add("Blinded not available for Protocol Amendment"); 
			}
			if(StringUtils.isBlank(amendment.getBlinded())) {
			  errors.add("Blinded not available for Protocol Amendment"); 
			 }
			if(StringUtils.isBlank(amendment.getShortTitle())) {
				errors.add("Short Title not available for Protocol Amendment"); 
			}
			if(StringUtils.isBlank(amendment.getDesign())) {
				errors.add("Design not available for Protocol Amendment"); 
			}
			if(StringUtils.isBlank(amendment.getPrecis())) {
				errors.add("Precis not available for Protocol Amendment"); 
			}
			if(StringUtils.isBlank(amendment.getLongTitle())) {
				errors.add("Long Title not available for Protocol Amendment"); 
			}
			if(StringUtils.isBlank(amendment.getShortTitle())) {
				errors.add("Short Title not available for Protocol Amendment"); 
			}
			
		}
		
	
		return errors;
	}
	
	/**
	 * validateProtocolArm
	 * 
	 * @param protocol
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ArrayList validateProtocolArm(Protocol protocol) {

		ArrayList errors = new ArrayList();

		/*
		 * Arm information
		 */
		Collection<ProtocolArm> protocolArmCollection = protocol.getProtocolArmCollection();
		if (protocolArmCollection != null) {
			for (ProtocolArm protocolarm : protocolArmCollection) {

				if (StringUtils.isBlank(protocolarm.getAccrualCeiling())) {
					errors.add("Accrual Ceiling not available for Protocol Arm");
				}
				if (StringUtils.isBlank(protocolarm.getCode())) {
					errors.add("Arm Code not available for Protocol Arm");
				}
				if (StringUtils.isBlank(protocolarm.getDescription())) {
					errors.add("Arm Description not available for Protocol Arm");
				}

			}
		}

		return errors;
	}
    
    public ProtocolDao getProtocolDao() {
		return protocolDao;
	}

	public void setProtocolDao(ProtocolDao protocolDao) {
		this.protocolDao = protocolDao;
	}

	

}
