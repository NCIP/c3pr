/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.EpochType;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.dao.ReasonDao;
import edu.duke.cabig.c3pr.domain.OffEpochReason;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.web.WebUtils;

public class RegistrationAdvancedSearchController extends AdvancedSearchController{
	
	private ReasonDao reasonDao;
	
	public void setReasonDao(ReasonDao reasonDao) {
		this.reasonDao = reasonDao;
	}

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) {
        Map<String, Object> configMap = getConfigurationProperty().getMap();
        Map<String, Object> refdata = new HashMap<String, Object>();
        refdata.put("phaseCodeRefData", configMap.get("phaseCodeRefData"));
        refdata.put("typeRefData", configMap.get("typeRefData"));
        
        refdata.put("administrativeGenderCode", configMap.get("administrativeGenderCode"));
        refdata.put("ethnicGroupCodes", configMap.get("ethnicGroupCode"));
        refdata.put("raceCodes", configMap.get("raceCode"));

        refdata.put("registrationStatusRefData", WebUtils.collectOptions(RegistrationWorkFlowStatus.values(), ""));
        refdata.put("paymentMethods", configMap.get("paymentMethods"));
        
        refdata.put("offScreeningEpochReasons", reasonDao.getOffScreeningReasons());
        refdata.put("offReservingEpochReasons", reasonDao.getOffReservingReasons());
        refdata.put("offTreatmentEpochReasons", reasonDao.getOffTreatmentReasons());
        refdata.put("offFollowupEpochReasons", reasonDao.getOffFollowupReasons());
        
        Map<String,Object> studyStatusMap = new HashMap<String,Object>();
        for(CoordinatingCenterStudyStatus studyStatus : CoordinatingCenterStudyStatus.values()){
        	studyStatusMap.put(studyStatus.getName(), studyStatus.getCode());
        }
        refdata.put("statusRefData",studyStatusMap);
        
        Map<String,Object> scheduledEpochStatusMap = new HashMap<String,Object>();
        for(ScheduledEpochWorkFlowStatus scheduledEpochStatus : ScheduledEpochWorkFlowStatus.values()){
        	scheduledEpochStatusMap.put(scheduledEpochStatus.getName(), scheduledEpochStatus.getCode());
        }
        
        refdata.put("scheduledEpochStatusRefData",scheduledEpochStatusMap);

        
        return refdata;
    }
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		AdvancedSearchWrapper wrapper  = (AdvancedSearchWrapper) command ;
		List<AdvancedSearchCriteriaParameter> criteriaList = new ArrayList<AdvancedSearchCriteriaParameter>();
		
		for(AdvancedSearchCriteriaParameter searchCriteria : wrapper.getSearchCriteriaList()){
			searchCriteria.setValues(WebUtils.removeEmptyStrings(searchCriteria.getValues()));
			if(searchCriteria.getValues().size() != 0 ){
				criteriaList.add(searchCriteria);
			}
		}
		List<StudySubject> registrations = getAdvancedSearchRepository().searchRegistrations(criteriaList);
		Map map = errors.getModel();
		if(request.getParameter("includeAllScheduledEpochs-hidden") !=null && request.getParameter("includeAllScheduledEpochs-hidden").equals("false")){
			List<ScheduledEpochWorkFlowStatus> schEpochStatusList = new ArrayList<ScheduledEpochWorkFlowStatus>();
			List<String> offEpochReasonsList = new ArrayList<String>();
			List<EpochType> epochTypesList = new ArrayList<EpochType>();
			
			for(AdvancedSearchCriteriaParameter searchCriteria : wrapper.getSearchCriteriaList()){
				if(searchCriteria.getObjectName().equals("edu.duke.cabig.c3pr.domain.ScheduledEpoch") && 
						searchCriteria.getAttributeName().equals("scEpochWorkflowStatus.code")){
					for(String value : searchCriteria.getValues()){
						schEpochStatusList.add(ScheduledEpochWorkFlowStatus.valueOf(value));
					}
				}
				if(searchCriteria.getObjectName().equals("edu.duke.cabig.c3pr.domain.Reason") && 
						searchCriteria.getAttributeName().equals("code")){
					offEpochReasonsList.addAll(searchCriteria.getValues());
				}
				if(searchCriteria.getObjectName().equals("edu.duke.cabig.c3pr.domain.Epoch") && 
						searchCriteria.getAttributeName().equals("type.code")){
					for(String value : searchCriteria.getValues()){
						epochTypesList.add(EpochType.valueOf(value));
					}
				}
			}
			List<StudySubject> filteredRegistrations = filterRegistrationsRelatedToLatestScheduledEpoch(registrations, 	schEpochStatusList,
					epochTypesList, offEpochReasonsList);
			map.put("registrations", filteredRegistrations);
		} else{
			map.put("registrations", registrations);
		}
		
		request.getSession().setAttribute("resultsViewColumnList", getAdvancedStudySearchResultColumns());
		request.getSession().setAttribute("searchResultsRowList", getAdvancedStudySearchResultRows(registrations));
		
		ModelAndView modelAndView = new ModelAndView(getSuccessView(), map);
		return modelAndView;
	}
	

	private List<ViewColumn> getAdvancedStudySearchResultColumns(){
		
		List<ViewColumn> registrationSearchColumns = new ArrayList<ViewColumn>();
		
		ViewColumn viewColumn1 = new ViewColumn();
		viewColumn1.setColumnTitle("Study Identifier");
		registrationSearchColumns.add(viewColumn1);
		ViewColumn viewColumn2 = new ViewColumn();
		viewColumn2.setColumnTitle("Subject Name");
		registrationSearchColumns.add(viewColumn2);
		ViewColumn viewColumn3 = new ViewColumn();
		viewColumn3.setColumnTitle("Subject Identifier");
		registrationSearchColumns.add(viewColumn3);
		ViewColumn viewColumn4 = new ViewColumn();
		viewColumn4.setColumnTitle("Study Site");
		registrationSearchColumns.add(viewColumn4);
		ViewColumn viewColumn5 = new ViewColumn();
		viewColumn5.setColumnTitle("Registration Status");
		registrationSearchColumns.add(viewColumn5);
		ViewColumn viewColumn6 = new ViewColumn();
		viewColumn6.setColumnTitle("Registration Date");
		registrationSearchColumns.add(viewColumn6);
		ViewColumn viewColumn7 = new ViewColumn();
		viewColumn7.setColumnTitle("Treating Physician");
		registrationSearchColumns.add(viewColumn7);
		
		return registrationSearchColumns;
	}
	
	private List<AdvancedSearchRow> getAdvancedStudySearchResultRows(List<StudySubject> registrations){
		
		List<AdvancedSearchRow> advancedSearchRegistrations = new ArrayList<AdvancedSearchRow>();
		
		for(StudySubject registration:registrations){
			AdvancedSearchRow advancedSearchRow = new AdvancedSearchRow();
			
			List<AdvancedSearchColumn> columnList = new ArrayList<AdvancedSearchColumn>();
			
			AdvancedSearchColumn advancedSearchColumn1 = new AdvancedSearchColumn();
			advancedSearchColumn1.setColumnHeader("Study Identifier");
			advancedSearchColumn1.setValue(registration.getStudySite().getStudy().getPrimaryIdentifier());
			columnList.add(advancedSearchColumn1);

			AdvancedSearchColumn advancedSearchColumn2 = new AdvancedSearchColumn();
			advancedSearchColumn2.setColumnHeader("Subject Name");
			advancedSearchColumn2.setValue(registration.getStudySubjectDemographics().getFullName());
			columnList.add(advancedSearchColumn2);
			
			AdvancedSearchColumn advancedSearchColumn3 = new AdvancedSearchColumn();
			advancedSearchColumn3.setColumnHeader("Subject Identifier");
			advancedSearchColumn3.setValue(registration.getStudySubjectDemographics().getPrimaryIdentifierValue());
			columnList.add(advancedSearchColumn3);
			
			AdvancedSearchColumn advancedSearchColumn4 = new AdvancedSearchColumn();
			advancedSearchColumn4.setColumnHeader("Study Site");
			advancedSearchColumn4.setValue(registration.getStudySite().getHealthcareSite().getName());
			columnList.add(advancedSearchColumn4);
			
			AdvancedSearchColumn advancedSearchColumn5 = new AdvancedSearchColumn();
			advancedSearchColumn5.setColumnHeader("Registration Status");
			advancedSearchColumn5.setValue(registration.getRegWorkflowStatus().getDisplayName());
			columnList.add(advancedSearchColumn5);
			
			AdvancedSearchColumn advancedSearchColumn6 = new AdvancedSearchColumn();
			advancedSearchColumn6.setColumnHeader("Registration Date");
			advancedSearchColumn6.setValue(registration.getStartDateStr());
			columnList.add(advancedSearchColumn6);
			
			AdvancedSearchColumn advancedSearchColumn7 = new AdvancedSearchColumn();
			advancedSearchColumn7.setColumnHeader("Treating Physician");
			advancedSearchColumn7.setValue(registration.getTreatingPhysicianFullName());
			columnList.add(advancedSearchColumn7);
			
			advancedSearchRow.setColumnList(columnList);
			advancedSearchRegistrations.add(advancedSearchRow);
		}
		
		return advancedSearchRegistrations;
		
	}
	
	private List<StudySubject> filterRegistrationsRelatedToLatestScheduledEpoch(List<StudySubject> registrations, List<ScheduledEpochWorkFlowStatus>
	schEpochStatusList, List<EpochType> epochTypesList, List<String> offEpochReasonCodesList){
		List<StudySubject> filteredStudySubjects = new ArrayList<StudySubject>();
		
		for(StudySubject studySubject : registrations){
			ScheduledEpoch currentScheduledEpoch = studySubject.getScheduledEpoch();
			
			List<String> latestEpochOffEpochReasonCodes = new ArrayList<String>();
			for(OffEpochReason offEpochReason: currentScheduledEpoch.getOffEpochReasons()){
				latestEpochOffEpochReasonCodes.add(offEpochReason.getReason().getCode());
			}
			if(!offEpochReasonCodesList.isEmpty() && CollectionUtils.intersection(latestEpochOffEpochReasonCodes, offEpochReasonCodesList).isEmpty()){
				continue;
			}
		
			if(!schEpochStatusList.isEmpty() && !schEpochStatusList.contains(currentScheduledEpoch.getScEpochWorkflowStatus())){
				continue;
			}
		
			if(!epochTypesList.isEmpty() && !epochTypesList.contains(currentScheduledEpoch.getEpoch().getType())){
				continue;
			}
			
			filteredStudySubjects.add(studySubject);
		}
		
		return filteredStudySubjects;
	}
}
