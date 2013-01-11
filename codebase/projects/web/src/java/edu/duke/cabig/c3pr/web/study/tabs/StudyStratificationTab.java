/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Randomization;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:28:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudyStratificationTab extends StudyTab {

	public StudyStratificationTab() {
		super("Stratification Factors", "Stratification",
				"study/study_stratifications");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map referenceDataForTab(HttpServletRequest request, StudyWrapper wrapper) {
		Map<String, Object> refdata = super.referenceDataForTab(request, wrapper);
		
		if(wrapper.getStudy().getRandomizedIndicator() && wrapper.getStudy().getRandomizationType().equals(RandomizationType.BOOK)){
			refdata.put("isBookRandomized", "true");
		}else{
			refdata.put("isBookRandomized", "false");
		}
		refdata.put("epochId",request.getParameter("epochId"));
		return refdata;
	}
	

	/**
	 * This method gets the serialized string that has the order of the re-ordered Stratum Groups.
	 * Use this to update the str grp number of the str groups in the collection. We chose to do
	 * this instead of deleting and creating new ones as this would cascade and delete the book
	 * randomization entries.
	 */
	public ModelAndView reorderStratumGroups(HttpServletRequest request,
			Object commandObj, Errors error) {

		Study study = ((StudyWrapper) commandObj).getStudy();
		String serializedString = request.getParameter("serializedString")
				.toString();
		int indexOfEpochNumber = Integer.parseInt(request.getParameter("epochCountIndex").toString());
		if(StringUtils.isBlank(serializedString) || StringUtils.isEmpty(serializedString) ){
			//no legal serialized string....dont do anything just reload the page.
			//used for reverting the drag drop if cancel is selected on confirm
		} else {
			String[] serStrArr = serializedString.split("&");
			//int indexOfEpochNumber = Integer.parseInt(serStrArr[0].substring(serStrArr[0].indexOf("_") + 1, serStrArr[0].indexOf("[")));
			Epoch tEpoch = study.getEpochs().get(indexOfEpochNumber);
	
			List<Integer> positionList = new ArrayList<Integer>();
			for (int i = 0; i < serStrArr.length; i++) {
				positionList.add(i, new Integer(serStrArr[i].substring(serStrArr[i].indexOf("-") + 1)));
			}
			// so if the order initially was 0123 and was changed to 1023 then the position list
			// will contain 1023.
			
			for (int i = 0; i < positionList.size(); i++) {
				// get each group in serial order and update their stratum groups using the position list.
				tEpoch.getStratumGroups().get(i).setStratumGroupNumber(positionList.indexOf(new Integer(i)));
			}
			//clear the book entries for the epoch and sort the groups
			clearBookEntriesForEpoch(tEpoch);
			ArrayList<StratumGroup> stratumGrps = new ArrayList<StratumGroup>();
			stratumGrps.addAll(tEpoch.getStratumGroups());
			Collections.sort(stratumGrps);
		}
		request.setAttribute("epochCountIndex", indexOfEpochNumber);
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request));
	}

	/**
	 * Clear the stratum groups and the book entries (if any)corresponding to the tratemtn epoch.
	 * called by the rowInserter from the row manager
	 */
	public ModelAndView clearBookEntriesAndStratumGroups(HttpServletRequest request,
			Object commandObj, Errors error) {
		String message = "";
		Study study = ((StudyWrapper) commandObj).getStudy();
		int epochCountIndex = Integer.parseInt(request
				.getParameter("epochCountIndex"));
		Epoch epoch = study.getEpochs().get(epochCountIndex);
		
		clearBookEntriesForEpoch(epoch);
		clearStratumGroupsForEpoch(epoch);
		
		Map <String, String> map = new HashMap<String, String>();
		map.put(AjaxableUtils.getFreeTextModelName(), message);
		return new ModelAndView("", map);
	}
	
	/**
	 * Clear book entries for epoch.
	 * 
	 * @param epoch the te
	 */
	private void clearBookEntriesForEpoch(Epoch epoch){
		if (!epoch.getStratumGroups().isEmpty()) {
			// clearing the bre's
			Randomization randomization = epoch.getRandomization();
			if (randomization instanceof BookRandomization) {
				BookRandomization bRandomization = (BookRandomization) randomization;
				bRandomization.getBookRandomizationEntry().clear();
			}
		}
		for(StratumGroup stratumGroup: epoch.getStratumGroups()){
			stratumGroup.getBookRandomizationEntry().clear();
		}
	}
	
	/**
	 * Clear stratum groups for epoch.
	 * 
	 * @param epoch the te
	 */
	private void clearStratumGroupsForEpoch(Epoch epoch){
		if(epoch != null){
			// clearing the stratum groups.
			epoch.getStratumGroups().clear();
		}
	}

	@Override
	public void postProcessOnValidation(HttpServletRequest req,
			StudyWrapper wrapper, Errors errors) {
		int epochCountIndex = -1;
		super.postProcessOnValidation(req, wrapper, errors);
		if (req.getParameter("epochCountIndex") != null
				&& !req.getParameter("generateGroups").toString()
						.equalsIgnoreCase("false")) {
			epochCountIndex = Integer.parseInt(req
					.getParameter("generateGroups"));
			generateStratumGroups(req, wrapper, errors, epochCountIndex);
		}
		String epochIdString= req.getParameter("epochId");
		String stratumGroupIdString= req.getParameter("stratumGroupId");
		if (!StringUtils.getBlankIfNull(epochIdString).equals("") &&
				!StringUtils.getBlankIfNull(stratumGroupIdString).equals("")) {
			int epochId = Integer.parseInt(epochIdString);
			int stratumGroupId = Integer.parseInt(stratumGroupIdString);
			Epoch epoch= null;
			for(Epoch tempEpoch: wrapper.getStudy().getEpochs()){
				if(tempEpoch.getId() == epochId){
					epoch=tempEpoch;
					break;
				}
			}
			epoch.getStratumGroups().remove(getIndexBasedOnID(epoch.getStratumGroups(), stratumGroupId).intValue());
			clearBookEntriesForEpoch(epoch);
		}
	}


	/**
	 * Generate stratum groups.
	 * 
	 * @param request the request
	 * @param commandObj the command obj
	 * @param error the error
	 * @param epochCountIndex the epoch count index
	 */
	private void generateStratumGroups(HttpServletRequest request,
			Object commandObj, Errors error, int epochCountIndex) {
		ArrayList<StratumGroup> stratumGroupList;
		Study study = ((StudyWrapper) commandObj).getStudy();
		request.setAttribute("epochCountIndex", epochCountIndex);

		// creating groups from the questions & answers for the selected treatemtn epoch
		Epoch epoch = study.getEpochs().get(epochCountIndex);

		epoch.generateStratumGroups();
	}
}
