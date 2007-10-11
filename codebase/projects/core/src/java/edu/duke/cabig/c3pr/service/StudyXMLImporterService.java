package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.StudyValidationException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;

import java.util.List;
import java.io.InputStream;
import java.io.Reader;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public interface StudyXMLImporterService {
    List<Study> importStudies(InputStream xmlStream) throws C3PRBaseRuntimeException;

    public void importStudy(Study study) throws Exception;

    void validate(Study study) throws StudyValidationException;

}
