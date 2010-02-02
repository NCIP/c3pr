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
    	
    	Investigator investigatorsbyEmail = null;
    	//investigatorsbyEmail =  investigatorDao.getByEmailAddressFromLocal(inv.getContactMechanisms().get(0).getValue());
    	investigatorsbyEmail =  investigatorDao.getByAssignedIdentifierFromLocal(inv.getAssignedIdentifier());
    	
    	if(investigatorsbyEmail != null){
    		if(inv.getId()==null){
    			errors.reject("tempProperty","Investigator with this Email already exists");
        	} else if(inv.getEmail().equals(investigatorsbyEmail.getEmail()) && !inv.getId().equals(investigatorsbyEmail.getId())){
        		//else if(inv.getContactMechanisms().get(0).getValue().equals(investigatorsbyEmail.getContactMechanisms().get(0).getValue()) && !inv.getId().equals(investigatorsbyEmail.getId())){
				errors.reject("tempProperty","Investigator with this Email already exists");
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


