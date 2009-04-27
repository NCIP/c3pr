package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Randomization;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.StratificationCriterionAnswerCombination;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
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

	@Override
	public Map referenceData(HttpServletRequest request, StudyWrapper wrapper) {
		Map<String, Object> refdata = super.referenceData(wrapper);
		boolean isAdmin = isAdmin();
		if ((request.getAttribute("amendFlow") != null && request.getAttribute(
				"amendFlow").toString().equals("true"))
				|| (request.getAttribute("editFlow") != null && request
						.getAttribute("editFlow").toString().equals("true"))) {
			if (request.getSession().getAttribute(DISABLE_FORM_STRATIFICATION) != null
					&& !isAdmin) {
				refdata.put("disableForm", request.getSession().getAttribute(
						DISABLE_FORM_STRATIFICATION));
			} else {
				refdata.put("disableForm", new Boolean(false));
				refdata.put("mandatory", "true");
			}
		}
		if(wrapper.getStudy().getRandomizedIndicator() && wrapper.getStudy().getRandomizationType().equals(RandomizationType.BOOK)){
			refdata.put("isBookRandomized", "true");
		}else{
			refdata.put("isBookRandomized", "false");
		}
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
			Collections.sort(tEpoch.getStratumGroups());
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
		map.put(AjaxableUtils.getFreeTextModelName(),map);
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

		// clear the existing groups first.(incase the user cilcks on generate twice)
		epoch.getStratumGroups().clear();

		int stratificationCriterionSize = epoch.getStratificationCriteria().size();
		if (stratificationCriterionSize > 0) {
			StratificationCriterionPermissibleAnswer permissibleAnswersArray[][] = new StratificationCriterionPermissibleAnswer[stratificationCriterionSize][];
			List<StratificationCriterionPermissibleAnswer> tempAnswersList;

			// creating a 2d array of answers for every treatment epoch
			for (int i = 0; i < stratificationCriterionSize; i++) {
				tempAnswersList = epoch.getStratificationCriteria().get(i)
						.getPermissibleAnswers();
				permissibleAnswersArray[i] = new StratificationCriterionPermissibleAnswer[tempAnswersList
						.size()];
				for (int j = 0; j < tempAnswersList.size(); j++) {
					permissibleAnswersArray[i][j] = tempAnswersList.get(j);
				}
			}

			stratumGroupList = stratumGroupCombinationGenerator(epoch, permissibleAnswersArray, 0, new ArrayList<StratumGroup>(),
					new ArrayList<StratificationCriterionAnswerCombination>());
			epoch.getStratumGroups().addAll(stratumGroupList);
		}
	}


	/**
	 * Stratum group combination generator.
	 * recursive method which computes all possible combinations
	 * of sc and scpa and creates a list of stratum Groups for the same.
	 * 
	 * @param epoch the epoch
	 * @param permissibleAnswersArray the permissible answers array
	 * @param intRecurseLevel the int recurse level
	 * @param stratumGroupList the stratum group list
	 * @param generatedAnswerCombinationList the generated answer combination list
	 * 
	 * @return the array list< stratum group>
	 */
	private ArrayList<StratumGroup> stratumGroupCombinationGenerator(Epoch epoch,
			StratificationCriterionPermissibleAnswer[][] permissibleAnswersArray,
			int intRecurseLevel, ArrayList<StratumGroup> stratumGroupList,
			ArrayList<StratificationCriterionAnswerCombination> generatedAnswerCombinationList) {
		StratificationCriterionAnswerCombination stratificationCriterionAnswerCombination ;
		ArrayList<StratificationCriterionAnswerCombination> stratificationCriterionAnswerCombinationList;
		int numberOfStratumGroups = 0;
		
		for (int i = 0; i < permissibleAnswersArray[intRecurseLevel].length; i++) {
			stratificationCriterionAnswerCombination = new StratificationCriterionAnswerCombination();
			stratificationCriterionAnswerCombinationList = new ArrayList<StratificationCriterionAnswerCombination>();
			stratificationCriterionAnswerCombination
					.setStratificationCriterionPermissibleAnswer(permissibleAnswersArray[intRecurseLevel][i]);
			stratificationCriterionAnswerCombination.setStratificationCriterion(epoch
					.getStratificationCriteria().get(intRecurseLevel));

			if (!generatedAnswerCombinationList.isEmpty()) {
				stratificationCriterionAnswerCombinationList.addAll(generatedAnswerCombinationList);
			}
			stratificationCriterionAnswerCombinationList.add(stratificationCriterionAnswerCombination);

			if (intRecurseLevel < permissibleAnswersArray.length - 1) {
				// stepping into the next question
				stratumGroupList = stratumGroupCombinationGenerator(epoch, permissibleAnswersArray, intRecurseLevel + 1, stratumGroupList,
						stratificationCriterionAnswerCombinationList);
			} else {
				// ran out of questions and hence now i have a combination of answers to save.
				numberOfStratumGroups = stratumGroupList.size();
				stratumGroupList.add(numberOfStratumGroups, new StratumGroup());
				stratumGroupList.get(numberOfStratumGroups)
						.getStratificationCriterionAnswerCombination().addAll(
								cloneStratificationCriterionAnswerCombination(stratificationCriterionAnswerCombinationList));
				stratumGroupList.get(numberOfStratumGroups).setStratumGroupNumber(numberOfStratumGroups);
			}
		}
		return stratumGroupList;
	}


	/**
	 * Clones the StratificationCriterionAnswerCombination for the comboGenerator.
	 * 
	 * @param stratificationCriterionAnswerCombinationList the stratification criterion answer combination list
	 * 
	 * @return the list< stratification criterion answer combination>
	 */
	private List<StratificationCriterionAnswerCombination> cloneStratificationCriterionAnswerCombination(
			List<StratificationCriterionAnswerCombination> stratificationCriterionAnswerCombinationList) {

		List<StratificationCriterionAnswerCombination> clonedList = new ArrayList<StratificationCriterionAnswerCombination>();
		Iterator<StratificationCriterionAnswerCombination> iter = stratificationCriterionAnswerCombinationList
				.iterator();
		while (iter.hasNext()) {
			clonedList.add(new StratificationCriterionAnswerCombination(iter
					.next()));
		}
		return clonedList;
	}

}
