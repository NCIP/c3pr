package edu.duke.cabig.c3pr.service;

import java.io.InputStream;
import java.util.List;

import org.jdom.Document;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;

/**
 * User: kherm
 * 
 * @author kherm manav.kher@semanticbits.com
 */
@Transactional(readOnly = false, rollbackFor = C3PRCodedException.class, noRollbackFor = C3PRBaseRuntimeException.class)
public interface StudyXMLImporterService {
	List<Study> importStudies(InputStream xmlStream, Errors errors)
			throws C3PRCodedException;

	public abstract List<Study> importStudies(Document document, Errors errors)
			throws C3PRCodedException;

	public abstract List<Study> importStudies(org.w3c.dom.Document doc, Errors errors)
			throws C3PRCodedException;

}
