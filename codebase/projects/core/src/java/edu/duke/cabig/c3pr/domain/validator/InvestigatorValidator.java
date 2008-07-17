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
    	List<Investigator> investigators = new ArrayList<Investigator>();
    	investigators =  investigatorDao.getInvestigatorsByNciInstituteCode(inv.getNciIdentifier());
    	if (investigators.size()>1){
    		errors.reject("tempProperty",
                            "Investigator already exists");
    	} else if(inv.getId()==null){
    		if(investigators.size()>0){
    			errors.reject("tempProperty",
                "Investigator already exists");
    		}
    	}else  if(investigators.size()==1){
    			if(inv.getNciIdentifier().equals(investigators.get(0).getNciIdentifier()) && !inv.getId().equals(investigators.get(0).getId())){
    				errors.reject("tempProperty","Investigator already exists");
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


