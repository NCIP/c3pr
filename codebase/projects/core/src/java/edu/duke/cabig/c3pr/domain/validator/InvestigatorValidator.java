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
    	
    	List<Investigator> investigatorsbyNCICode = new ArrayList<Investigator>();
    	investigatorsbyNCICode =  investigatorDao.getInvestigatorsByNciInstituteCode(inv.getNciIdentifier());
    	if (investigatorsbyNCICode.size()>1){
    		errors.reject("tempProperty",
                            "Investigator with this NCI Identifier already exists");
    	} else if(inv.getId()==null){
    		if(investigatorsbyNCICode.size()>0){
    			errors.reject("tempProperty",
                "Investigator with this NCI Identifier already exists");
    		}
    	}else  if(investigatorsbyNCICode.size()==1){
    			if(inv.getNciIdentifier().equals(investigatorsbyNCICode.get(0).getNciIdentifier()) && !inv.getId().equals(investigatorsbyNCICode.get(0).getId())){
    				errors.reject("tempProperty","Investigator with this NCI Identifier already exists");
    			}
    		}
    	
    	List<Investigator> investigatorsbyEmail = new ArrayList<Investigator>();
    	investigatorsbyEmail =  investigatorDao.getByEmailAddress(inv.getContactMechanisms().get(0).getValue());
    	if (investigatorsbyEmail.size()>1){
    		errors.reject("tempProperty",
                            "Investigator with this Email already exists");
    	} else if(inv.getId()==null){
    		if(investigatorsbyEmail.size()>0){
    			errors.reject("tempProperty",
                "Investigator with this Email already exists");
    		}
    	}else  if(investigatorsbyEmail.size()==1){
    			if(inv.getContactMechanisms().get(0).getValue().equals(investigatorsbyEmail.get(0).getContactMechanisms().get(0).getValue()) && !inv.getId().equals(investigatorsbyEmail.get(0).getId())){
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


