package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.Study;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 13, 2007 Time: 7:27:09 PM To
 * change this template use File | Settings | File Templates.
 */
class StudyDetailsTab extends StudyTab {

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
			if (request.getSession().getAttribute(DISABLE_FORM_DETAILS) != null) {
				refdata.put("disableForm", request.getSession().getAttribute(
						DISABLE_FORM_DETAILS));
			} else {
				refdata.put("disableForm", new Boolean(false));
			}
		} else {
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
	}

}
