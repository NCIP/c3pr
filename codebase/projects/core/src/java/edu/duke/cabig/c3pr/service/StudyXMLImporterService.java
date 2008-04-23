package edu.duke.cabig.c3pr.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;

/**
 * User: kherm
 * 
 * @author kherm manav.kher@semanticbits.com
 */
@Transactional(readOnly = false, rollbackFor = C3PRCodedException.class, noRollbackFor = C3PRBaseRuntimeException.class)
public interface StudyXMLImporterService {
    List<Study> importStudies(InputStream xmlStream,File importXMLResult) throws C3PRCodedException;

//    public void importStudy(Study study) throws Exception;
//    void validate(Study study) throws StudyValidationException;

}
