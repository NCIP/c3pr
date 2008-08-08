package edu.duke.cabig.c3pr.domain.repository;

import java.util.List;

import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.StudyValidationException;

public interface StudyRepository {
	
	int i =0;

	public void buildAndSave(Study study) throws C3PRCodedException;
	
	public void validate(Study study) throws StudyValidationException;
	
	public List<Study> searchByCoOrdinatingCenterId(OrganizationAssignedIdentifier identifier) throws C3PRCodedException;
	
	public void save(Study study) throws C3PRCodedException;
	
    public Study merge(Study study);

    public void refresh(Study study);

    public void reassociate(Study study);
    
    public void clear();
}
