package edu.duke.cabig.c3pr.domain.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.domain.Investigator;

public class InvestigatorValidator implements Validator {
	
	private InvestigatorDao investigatorDao ;

    public boolean supports(Class clazz) {
        return  Investigator.class.isAssignableFrom(clazz);
    }
    public void validate(Object target, Errors errors) {
    	isExistingInvestigator(target, errors) ;
    }
    
    public void isExistingInvestigator(Object target, Errors errors) {
    	
    	Investigator inv = (Investigator)target;
    	
    	Investigator investigatorsbyAssignedIdentifier = null;
    	investigatorsbyAssignedIdentifier =  investigatorDao.getByAssignedIdentifierFromLocal(inv.getAssignedIdentifier());
    	
    	if(investigatorsbyAssignedIdentifier != null){
    		
    		if(inv.getId()==null){
    			errors.reject("tempProperty","Investigator with this assigned identifier already exists");
        	} else if(inv.getEmail().equals(investigatorsbyAssignedIdentifier.getEmail()) && !inv.getId().equals(investigatorsbyAssignedIdentifier.getId())){
				errors.reject("tempProperty","Investigator with this assigned identifier already exists");
    		}
    	}
    }

	public InvestigatorDao getInvestigatorDao() {
		return investigatorDao;
	}

	public void setInvestigatorDao(InvestigatorDao investigatorDao) {
		this.investigatorDao = investigatorDao;
	}
}


