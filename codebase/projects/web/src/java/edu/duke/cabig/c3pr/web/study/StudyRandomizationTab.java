package edu.duke.cabig.c3pr.web.study;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.BookRandomizationEntry;
import edu.duke.cabig.c3pr.domain.Randomization;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * Author: gangoliV
 * Date: July 30, 2007
 */

public class StudyRandomizationTab extends StudyTab {
	
	 private static Log log = LogFactory.getLog(StudyRandomizationTab.class);
		
	    public StudyRandomizationTab() {
	        super("Study Book Randomizations", "Randomizations", "study/study_randomizations");
	    }


	    @Override
	    public void postProcess(HttpServletRequest req, Study study, Errors errors) {
	        	        
	    	if(study.getRandomizationType().equals(RandomizationType.CALL_OUT)){
	    		return;
	    	}
	    	
	    	if(study.getRandomizationType().equals(RandomizationType.BOOK)){
		    	ArrayList <String>reqParamArr = new ArrayList<String>();
		        Enumeration e = req.getParameterNames();
		        String temp;
		        while(e.hasMoreElements()){
		        	temp = e.nextElement().toString(); 
		        	if(temp.startsWith("bookRandomizations")){
		        		reqParamArr.add(temp);
		        	}
		        }
		        String epochIndex, bookRandomizations;
		        int selectedEpoch;
		        for(String param : reqParamArr){
		        	bookRandomizations = StringUtils.getBlankIfNull(req.getParameter(param));
		        	if(!StringUtils.isEmpty(bookRandomizations)){
				        epochIndex = param.substring(param.indexOf("-")+1);
				        selectedEpoch = StringUtils.getBlankIfNull(epochIndex).equals("")?-1:Integer.parseInt(epochIndex);
				        TreatmentEpoch tEpoch = study.getTreatmentEpochs().get(selectedEpoch);		        
				        if(study.getRandomizationType().getName().equals("BOOK")){
				        	parseBookRandomization(bookRandomizations, tEpoch);
				        }
		        	}
		        }
	    	}
	    }

	    /*
	     * This method accepts the comma seperated string which is the formatted content for book randomization 
	     * and populated the domain object with the corresponding data.
	     * the format is "Stratum Group, Position, Arm Assignment"
	     * e.g: 0, 0, A
	     * 		0, 1, C
	     * 		1, 0, A
	     * 		2, 1, B
	     */
	    private void parseBookRandomization(String bookRandomizations, TreatmentEpoch tEpoch){	    	
	    		    	
	    	try{
	    		//we do not create a new instance of bookRandomization, we use the existing instance which was created in StudyDesignTab.java
	    		//based on the randomizationType selected on the study_details page.
	    		Randomization randomization = tEpoch.getRandomization();
	    		BookRandomization bRandomization;
	    		if(randomization == null){
	    			bRandomization = new BookRandomization();
	    			tEpoch.setRandomization(bRandomization);
	    		} else {
	    			bRandomization = (BookRandomization)randomization;
	    		}
	    		
	    		BookRandomizationEntry bookRandomizationEntry = new BookRandomizationEntry();
	    		ArrayList <BookRandomizationEntry>breList = new ArrayList<BookRandomizationEntry>();
	    		
		    	StringTokenizer outer = new StringTokenizer(bookRandomizations, "\n");
		    	String entry;
		    	String[] entries;
		    	Arm arm;
		    	StratumGroup sGroup;
		    	while(outer.hasMoreElements()){			    			    		
		    		entry = outer.nextToken();
		    		entries = entry.split(",");
	    			
		    		//find the stratum group with this id and set it here
	    			sGroup = getStratumGroupByNumber(tEpoch, entries[0].trim());
	    			bookRandomizationEntry.setStratumGroup(sGroup);
		    		//set the position
	    			bookRandomizationEntry.setPosition(Integer.parseInt(entries[1].trim()));		    		
	    			//find the arm with this id and set it here
		    		arm = getArmByName(tEpoch, entries[2].trim());
		    		bookRandomizationEntry.setArm(arm);
	    			if(bookRandomizationEntry.getArm() != null && bookRandomizationEntry.getStratumGroup()!= null){	
	    				breList.add(bookRandomizationEntry);	    					    				
	    			}
	    			bookRandomizationEntry = new BookRandomizationEntry();
		    	}
		    	bRandomization.getBookRandomizationEntry().addAll(breList);
		    	tEpoch.setRandomization(bRandomization);
	    	} catch(Exception e){
	    		log.error("parseBookRandomizatrion Failed");
	    		log.error(e.getMessage());
	    	}
	    }

	    /*
	     * takes the treatementEpoch and arm.name
	     * and returns the arm (from that epoch)which has that name.
	     */
	    public Arm getArmByName(TreatmentEpoch tEpoch, String armName){
	    	Arm selectedArm = null;
	    	if(tEpoch != null){	    	
		    	List<Arm> armList = tEpoch.getArms();
		    	for(Arm individualArm : armList){
		    		if(individualArm.getName().equals(armName.trim())){      //("Arm "+armName.trim())){
		    			selectedArm = individualArm;
		    		}
		    	}
	    	}
	    	return selectedArm;
	    }
	    
	    /*
	     * takes the treatementEpoch and groupNumber
	     * and returns the Stratum Group(from that epoch)which has that group number.
	     */
	    public StratumGroup getStratumGroupByNumber(TreatmentEpoch tEpoch, String sgPos){
	    	StratumGroup selectedStratumGroup = null;
	    	int sgNumber = Integer.parseInt(sgPos);
	    	if(tEpoch != null){	    	
		    	List<StratumGroup> sgList = tEpoch.getStratumGroups();
		    	for(StratumGroup individualStratumGroup : sgList){
		    		if(individualStratumGroup.getStratumGroupNumber() == sgNumber){
		    			selectedStratumGroup = individualStratumGroup;
		    		}
		    	}
	    	}
	    	return selectedStratumGroup;
	    }
	    
}
