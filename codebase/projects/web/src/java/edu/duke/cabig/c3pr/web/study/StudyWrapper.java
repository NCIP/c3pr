/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.study;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;

public class StudyWrapper {

	private Study study ;
	private String file;
	private boolean hasStratifiedEpoch ;
	private boolean hasEligibilityCritiria;
	
    /** The study investigator ids. */
    private String[] studyInvestigatorIds;
    
    /** The study personnel ids. */
    private String[] studyPersonnelIds;
	
	public boolean getHasEligibilityCritiria(){
		hasEligibilityCritiria= false;
		if (this.study != null){
			for (Epoch epoch : this.study.getEpochs()) {
				if(epoch.getEligibilityCriteria().size() > 0){
					return true;
				}
			}
		}
		return hasEligibilityCritiria;
	}

	public void setHasEligibilityCritiria(boolean hasEligibilityCritiria) {
		this.hasEligibilityCritiria = hasEligibilityCritiria;
	}

	public boolean getHasStratifiedEpoch() {
		hasStratifiedEpoch = false;
		if (this.study != null) {
			if (study.getStratificationIndicator()) {
				for (Epoch epoch : this.study.getEpochs()) {
					if (epoch.getStratificationIndicator()) {
						return true;
					}
				}
			}
		}
		return hasStratifiedEpoch;
	}

	public void setHasStratifiedEpoch(boolean hasStratifiedEpoch) {
		this.hasStratifiedEpoch = hasStratifiedEpoch;
	}

	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	@Transient
	public String getFile() {
		if (file != null) {
			return file;
		}
		return null;
	}

	public void setFile(String file) {
		this.file = file;
	}

     public boolean canAmendStudy(){
    	Study study = this.getStudy();
    	if(study.getIsEmbeddedCompanionStudy()){
    		// we do not support amendment of embedded companion studies.
    		return false ;
    	}
    	List<CoordinatingCenterStudyStatus> permissibleStatus = new ArrayList<CoordinatingCenterStudyStatus>();
        permissibleStatus.add(CoordinatingCenterStudyStatus.OPEN);
        permissibleStatus.add(CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL);
        permissibleStatus.add(CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT);

        return permissibleStatus.contains(study.getCoordinatingCenterStudyStatus()) && study.getStudyVersion() == study.getLatestStudyVersion();

    }

     public boolean resumeAmendment(){
    	 if(canAmendStudy() && getStudy().getCurrentStudyAmendment() != null ){
             return true;
         }
         return false;
     }

     public boolean applyAmendment(){
     	return resumeAmendment();
     }
     
     public boolean getHasCoordinatingCenterAsStudySite(){
    	 try {
			study.getStudySite(study.getStudyCoordinatingCenter().getHealthcareSite().getPrimaryIdentifier());
		} catch (C3PRCodedRuntimeException e) {
			return false;
		}
    	return true;
     }
     
     public boolean getHasFundingSponsorAsStudySite(){
    	 if(study.getStudyFundingSponsors().size()!=1){
    		 return false;
    	 }
    	 try {
    		 study.getStudySite(study.getStudyFundingSponsors().get(0).getHealthcareSite().getPrimaryIdentifier());
 		} catch (C3PRCodedRuntimeException e) {
 			return false;
 		}
     	return true;
     }

	public String[] getStudyInvestigatorIds() {
		return studyInvestigatorIds;
	}

	public void setStudyInvestigatorIds(String[] studyInvestigatorIds) {
		this.studyInvestigatorIds = studyInvestigatorIds;
	}

	public String[] getStudyPersonnelIds() {
		return studyPersonnelIds;
	}

	public void setStudyPersonnelIds(String[] studyPersonnelIds) {
		this.studyPersonnelIds = studyPersonnelIds;
	}
	
	public String getPrimaryIdentifierAssigningAuthority() {
		for (Identifier identifier : study.getIdentifiers()) {
			if (identifier.getPrimaryIndicator().booleanValue() == true) {
				if(identifier instanceof OrganizationAssignedIdentifier){
					return ((OrganizationAssignedIdentifier)identifier).getHealthcareSite().getName();
				}else if(identifier instanceof SystemAssignedIdentifier){
					return ((SystemAssignedIdentifier)identifier).getSystemName();
				}else{
					return null ;
				}
			}
		}
		return null;
	}

}
