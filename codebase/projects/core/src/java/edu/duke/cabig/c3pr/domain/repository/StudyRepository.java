package edu.duke.cabig.c3pr.domain.repository;

import java.util.List;

import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.StudyValidationException;

public interface StudyRepository {

	public void buildAndSave(Study study) throws C3PRCodedException;
	
	public void validate(Study study) throws StudyValidationException, C3PRCodedException;
	
	public List<Study> searchByCoOrdinatingCenterId(OrganizationAssignedIdentifier identifier) throws C3PRCodedException;
	
	public void save(Study study) throws C3PRCodedException;
	
    public Study merge(Study study);

    public void refresh(Study study);

    public void reassociate(Study study);
    
    public void open(Study study) throws C3PRCodedRuntimeException;
    
    public void closeToAccrual(Study study) throws C3PRCodedRuntimeException;
    
    public void closeToAccrualAndTreatment(Study study) throws C3PRCodedRuntimeException;
    
    public void putInPending(Study study) throws C3PRCodedRuntimeException;
    
    public void temporarilyCloseToAccrualAndTreatment(Study study) throws C3PRCodedRuntimeException;
    
    public void temporarilyCloseToAccrual(Study study) throws C3PRCodedRuntimeException;
    
    public void putInAmendmentPending(Study study) throws C3PRCodedRuntimeException;
    
    public void clear();
}
