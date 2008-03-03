package edu.duke.cabig.c3pr.web.study;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import edu.duke.cabig.c3pr.domain.ExclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Study;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 3:24:28 PM
 * To change this template use File | Settings | File Templates.
 */
class StudyEligibilityChecklistTab extends StudyTab {

	public static final String INCLUSION = "Inclusion";
	public static final String EXCLUSION = "Exclusion";
	public static final String NOT_APPLICABLE = "N/A";	
	
    public StudyEligibilityChecklistTab() {
        super("Eligibility Checklist", "Eligibility", "study/study_eligibility_checklist");
    }
      

    @Override
	public Map referenceData(HttpServletRequest request, Study study) {
		Map<String, Object> refdata = super.referenceData(study);
		boolean isAdmin = isAdmin();
		if( (request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow").toString().equals("true")) ||
			    (request.getAttribute("editFlow") != null && request.getAttribute("editFlow").toString().equals("true")) ) 
    	{
			if(request.getSession().getAttribute(DISABLE_FORM_ELIGIBILITY) != null && !isAdmin){
				refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_ELIGIBILITY));
			} else {
				refdata.put("disableForm", new Boolean(false));
			}
    	}
		return refdata;
	}
    
    @Override
    public void postProcessOnValidation(HttpServletRequest httpServletRequest, Study study, Errors errors) {
    	Object obj = httpServletRequest.getParameter("name"); 
    	if( obj != null){    		
    		try {
                POIFSFileSystem pfs = new POIFSFileSystem(study.getCriteriaInputStream());                
                parseCadsrFile(study, pfs, obj.toString());
                
            } catch (IOException e1) {
                errors.reject("Could not import Studies", e1.getMessage());
            }
    	}
    	
    }
    
    /*
     * This method parses the uploaded cadsr file for the inc/exc criteria and 
     * extracts the question and possible answers "yes/no/na" and populates the 
     * command object with it.
     */
    public void parseCadsrFile(Study study, POIFSFileSystem pfs, String name){
    	
    	List <InclusionEligibilityCriteria>incList = new ArrayList<InclusionEligibilityCriteria>();
    	List <ExclusionEligibilityCriteria>excList= new ArrayList<ExclusionEligibilityCriteria>();
    	try{
    		HSSFWorkbook wb = new HSSFWorkbook(pfs);
            HSSFSheet sheet = wb.getSheetAt(0);
            
            HSSFRow currentRow;
            HSSFCell currentCell;
            InclusionEligibilityCriteria inc = null;
            ExclusionEligibilityCriteria exc = null;
            
    	    Iterator rowIter = sheet.rowIterator();
    	    //iterating over the entire file
    	    while(rowIter.hasNext()){    	    	
    	    	currentRow = (HSSFRow)rowIter.next();
    	    	
    	    	//inclusion section
    	    	if(currentRow.getCell((short)0) != null && currentRow.getCell((short)0).getCellType() == HSSFCell.CELL_TYPE_STRING && 
    	    			currentRow.getCell((short)0).getRichStringCellValue().toString().startsWith(INCLUSION)){
    	    		//iterating over the inclusion rows
    	    		HSSFRow innerCurrentRow;
    	    		while(rowIter.hasNext()){
    	    			innerCurrentRow = (HSSFRow)rowIter.next();
    	    			if(innerCurrentRow.getCell((short)0) != null && innerCurrentRow.getCell((short)0).getCellType() == HSSFCell.CELL_TYPE_STRING && 
    	    					innerCurrentRow.getCell((short)0).getRichStringCellValue().toString().startsWith(EXCLUSION)){
    	    				currentRow = innerCurrentRow;    	    				
    	    				break;
    	    			}
    	    			currentCell = innerCurrentRow.getCell((short)3);
    	    			if(currentCell != null && currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING &&
    	    					currentCell.getRichStringCellValue().toString().length() > 0){
    	    				//create the new inc and populate the list
    	    				if(inc != null){
    	    					incList.add(inc);
    	    				}
    	    				inc = new InclusionEligibilityCriteria();
    	    				inc.setQuestionText(currentCell.getRichStringCellValue().toString());
    	    			}else {
    	    				//get the answers
    	    				currentCell = innerCurrentRow.getCell((short)15);
    	    				if(currentCell != null && currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING &&
        	    					currentCell.getRichStringCellValue().toString().equalsIgnoreCase(NOT_APPLICABLE)){
    	    					if(inc != null){
    	    						inc.setNotApplicableIndicator(true);
    	    					}    	    					
    	    				}    	    				
    	    			}    	    	    	    	    			
    	    		} 
    	    		//adding the last criteria to the list
    				incList.add(inc);
    	    	}//end of inclusion if
    	    	
    	    	//exclusion section
    	    	if(currentRow.getCell((short)0) != null && currentRow.getCell((short)0).getCellType() == HSSFCell.CELL_TYPE_STRING && 
    	    			currentRow.getCell((short)0).getRichStringCellValue().toString().startsWith(EXCLUSION)){
    	    		//iterating over the exclusion rows
    	    		HSSFRow innerCurrentRow;
    	    		while(rowIter.hasNext()){
    	    			innerCurrentRow = (HSSFRow)rowIter.next();
    	    			//This if loop is not needed as we only have 1 inc section and 1 exc section
    	    			//however leaving it in place as it is harmless and will be useful if we ever have repeated inc/exc sections in the input file
    	    			if(innerCurrentRow.getCell((short)0) != null && innerCurrentRow.getCell((short)0).getCellType() == HSSFCell.CELL_TYPE_STRING && 
    	    					innerCurrentRow.getCell((short)0).getRichStringCellValue().toString().startsWith(INCLUSION)){
    	    				currentRow = innerCurrentRow;
    	    				break;
    	    			}
    	    			currentCell = innerCurrentRow.getCell((short)3);
    	    			if(currentCell != null && currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING &&
    	    					currentCell.getRichStringCellValue().toString().length() > 0){
    	    				//create the new exc and populate the list
    	    				if(exc != null){
    	    					excList.add(exc);
    	    				}
    	    				exc = new ExclusionEligibilityCriteria();
    	    				exc.setQuestionText(currentCell.getRichStringCellValue().toString());
    	    			}else {
    	    				//get the answers
    	    				currentCell = innerCurrentRow.getCell((short)15);
    	    				if(currentCell != null && currentCell.getRichStringCellValue() != null && currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING && 
        	    					currentCell.getRichStringCellValue().toString().equalsIgnoreCase(NOT_APPLICABLE)){
    	    					if(exc != null){
    	    						exc.setNotApplicableIndicator(true);
    	    					}    	    					
    	    				}    	    				
    	    			}    	    	    	    	    			
    	    		}
    	    		//adding the last criteria to the list
    	    		excList.add(exc);
    	    	}//end of exclusion if   	    	
    	    	 	    	
    	    }//end if while loop that iterates over the entire file.
    	    if(study.getTreatmentEpochByName(name) != null){
    	    	study.getTreatmentEpochByName(name).getInclusionEligibilityCriteria().addAll(incList);
    	    	 study.getTreatmentEpochByName(name).getExclusionEligibilityCriteria().addAll(excList);
    	    }    	    
    	   
    	}catch(IOException ioe){
    		log.error(ioe.getMessage());
    	}
    	
    }

}
