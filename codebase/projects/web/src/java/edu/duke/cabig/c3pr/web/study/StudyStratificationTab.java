package edu.duke.cabig.c3pr.web.study;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.StratificationCriterionAnswerCombination;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 3:28:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudyStratificationTab extends StudyTab {

	
    public StudyStratificationTab() {
        super("Study Stratification Factors", "Stratification", "study/study_stratifications");
    }

    @Override
	public Map referenceData(HttpServletRequest request, Study study) {
		Map<String, Object> refdata = super.referenceData(study);
		if(request.getAttribute("amendFlow") != null &&
    			request.getAttribute("amendFlow").toString().equals("true")) 
    	{
			if(request.getSession().getAttribute(DISABLE_FORM_STRATIFICATION) != null){
				refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_STRATIFICATION));
			} else {
				refdata.put("disableForm", new Boolean(false));
			}
    	}	
		return refdata;
	}

    @Override
    public void postProcess(HttpServletRequest httpServletRequest, Study study, Errors errors) {
    }
    
    public ModelAndView clearStratumGroups(HttpServletRequest request, Object commandObj, Errors error){
    	String message = "";
    	Study study = (Study)commandObj;
    	int epochCountIndex = Integer.parseInt(request.getParameter("epochCountIndex"));
    	TreatmentEpoch te = study.getTreatmentEpochs().get(epochCountIndex);
    	if(!te.getStratumGroups().isEmpty()){
    		te.getStratumGroups().clear();
    		message = "Generate stratum groups again.";
    	}    	   
    	
    	Map map=new HashMap();
    	map.put(getFreeTextModelName(), message);
    	return new ModelAndView("",map);
    }
    
    
    public ModelAndView generateStratumGroups(HttpServletRequest request, Object commandObj, Errors error){
    	int scSize;
    	TreatmentEpoch te;
    	ArrayList<StratumGroup> sgList;
    	Study study = (Study)commandObj;
    	int epochCountIndex = Integer.parseInt(request.getParameter("epochCountIndex"));
    	request.setAttribute("epochCountIndex", epochCountIndex);
    	
    	//creating groups from the questions & answers for the selected treatemtn epoch

		te = study.getTreatmentEpochs().get(epochCountIndex);
		//clear the existing groups first.(incase the user cilcks on generate twice)
		te.getStratumGroups().clear();
		
		scSize = te.getStratificationCriteria().size();
		if(scSize > 0){
			StratificationCriterionPermissibleAnswer doubleArr[][] = new StratificationCriterionPermissibleAnswer [scSize][];
			List <StratificationCriterionPermissibleAnswer> tempList;
			
			//creating a 2d array of answers for every treatment epoch
			for(int i =0; i < scSize; i++ ){
				tempList = te.getStratificationCriteria().get(i).getPermissibleAnswers();
				doubleArr[i] = new StratificationCriterionPermissibleAnswer[tempList.size()];
				for(int j =0; j < tempList.size(); j++){
					doubleArr[i][j] = tempList.get(j);
				}
			}
			
			sgList = comboGenerator(te, doubleArr, 0, new ArrayList<StratumGroup>(),new ArrayList<StratificationCriterionAnswerCombination>());
			te.getStratumGroups().addAll(sgList);
		}
    	Map map=new HashMap();
    	return new ModelAndView(getAjaxViewName(request),map);
    }
    
    //recursive method which computes all possible combinations
    //of sc and scpa and creates a list of stratum Groups for the same.
	public ArrayList<StratumGroup> comboGenerator(TreatmentEpoch te, StratificationCriterionPermissibleAnswer[][] myArr, int intRecurseLevel, 
			ArrayList<StratumGroup> sgList, ArrayList<StratificationCriterionAnswerCombination> strLine){
		StratificationCriterionAnswerCombination strPositionVal;// = new StratificationCriterionAnswerCombination();
    	ArrayList <StratificationCriterionAnswerCombination> strMyLine;// = new ArrayList<StratificationCriterionAnswerCombination>();
    	int numOfSG = 0;
    	for(int i= 0; i< myArr[intRecurseLevel].length ; i++){
    		strPositionVal = new StratificationCriterionAnswerCombination();
    		strMyLine = new ArrayList<StratificationCriterionAnswerCombination>();
    		strPositionVal.setStratificationCriterionPermissibleAnswer(myArr[intRecurseLevel][i]);
    		strPositionVal.setStratificationCriterion(te.getStratificationCriteria().get(intRecurseLevel)); 
    		
    		if(!strLine.isEmpty()){
    			strMyLine.addAll(strLine);
    		}
    		strMyLine.add(strPositionVal);
 		
    		if(intRecurseLevel < myArr.length-1){
    			//stepping into the next question
    			sgList = comboGenerator(te, myArr, intRecurseLevel + 1, sgList, strMyLine);    			
    		} else {
    			//ran out of questions  and hence now i have a combination of answers to save.
//    			StratumGroup sg = new StratumGroup();
//    			sg.getStratificationCriterionAnswerCombination().addAll(strMyLine);    			
//    			sgList.add(sg);
    			
    			numOfSG = sgList.size();
    			sgList.add(numOfSG, new StratumGroup());
    			sgList.get(numOfSG).getStratificationCriterionAnswerCombination().addAll(cloneScac(strMyLine));
    			sgList.get(numOfSG).setStratumGroupNumber(numOfSG);
    		}    	
    	}
    	return sgList;
    }  
	
	public List<StratificationCriterionAnswerCombination> cloneScac(List<StratificationCriterionAnswerCombination> scacList){
		
		List <StratificationCriterionAnswerCombination>clonedList = new ArrayList<StratificationCriterionAnswerCombination>();		
		Iterator <StratificationCriterionAnswerCombination>iter = scacList.iterator();
		while(iter.hasNext()){
			clonedList.add(new StratificationCriterionAnswerCombination(iter.next()));
		}
		return clonedList;
	}
    
        
}
