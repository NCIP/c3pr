package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 13, 2007 Time: 7:27:09 PM To
 * change this template use File | Settings | File Templates.
 */
class StudyDetailsTab extends StudyTab {
	
	private StudyValidator studyValidator;

	public StudyDetailsTab() {
		super("Study Details", "Details", "study/study_details");
	}

	@Override
	public Map<String, Object> referenceData(HttpServletRequest request,
			Study study) {
		Map<String, Object> refdata = super.referenceData();
		addConfigMapToRefdata(refdata, "studySearchType");
		addConfigMapToRefdata(refdata, "diseaseCodeRefData");
		addConfigMapToRefdata(refdata, "monitorCodeRefData");
		addConfigMapToRefdata(refdata, "phaseCodeRefData");
		addConfigMapToRefdata(refdata, "sponsorCodeRefData");
		addConfigMapToRefdata(refdata, "statusRefData");
		addConfigMapToRefdata(refdata, "typeRefData");
		addConfigMapToRefdata(refdata, "coordinatingCenters");
		addConfigMapToRefdata(refdata, "yesNo");

		if (request.getAttribute("amendFlow") != null
				&& request.getAttribute("amendFlow").toString().equalsIgnoreCase("true")) {
			//amend-flow: set the disableForm refData for the amend flow.
			if (request.getSession().getAttribute(DISABLE_FORM_DETAILS) != null) {
				refdata.put("disableForm", request.getSession().getAttribute(
						DISABLE_FORM_DETAILS));
			} else {
				refdata.put("disableForm", new Boolean(false));
				refdata.put("mandatory", "true");
			}
		} else if (request.getAttribute("editFlow") != null
				&& request.getAttribute("editFlow").toString().equalsIgnoreCase("true")){
			//edit-flow: disable all unless in PENDING STATE.
			if(!(study.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.PENDING)){
				disableAll(request);
			} else { 
				//all states other than pending
				enableAll(request);
				refdata.put("mandatory", "true");
			}
			//set the disableForm refData for the edit flow.
			if (request.getSession().getAttribute(DISABLE_FORM_DETAILS) != null) {
				refdata.put("disableForm", request.getSession().getAttribute(
						DISABLE_FORM_DETAILS));
			} else {
				refdata.put("disableForm", new Boolean(false));
				refdata.put("mandatory", "true");
			}
		} else {
			//this must be the create flow
			enableAll(request);
			refdata.put("mandatory", "true");
		}
		
		return refdata;
	}

	@Override
	public void postProcess(HttpServletRequest request, Study study,
			Errors errors) {
		// TODO Auto-generated method stub
		super.postProcess(request, study, errors);
		if (request.getParameter("deletedSponsor") != null) {
			if (study.getFundingSponsorIdentifierIndex() != -1) {
				study.getOrganizationAssignedIdentifiers().remove(
						study.getFundingSponsorIdentifierIndex());
			}
			if ((study.getStudyFundingSponsors().size() > 0)) {
				study.getStudyFundingSponsors().remove(0);
			}
		} else if (request.getParameter("deletedSponsorIdentifier") != null) {
			if (study.getFundingSponsorIdentifierIndex() != -1) {
				study.getOrganizationAssignedIdentifiers().remove(
						study.getFundingSponsorIdentifierIndex());
			}

		}		
		updateRandomization(study);		
	}
	
	@Override
	public void validate(Study study, Errors errors) {
		// TODO Auto-generated method stub
		super.validate(study, errors);
	//	studyValidator.validateStudyCoordinatingCetnterIdentifier(study,errors);
	//	studyValidator.validateStudyFundingSponsorIdentifier(study,errors);
		
	}

	public StudyValidator getStudyValidator() {
		return studyValidator;
	}

	public void setStudyValidator(StudyValidator studyValidator) {
		this.studyValidator = studyValidator;
	}


}
