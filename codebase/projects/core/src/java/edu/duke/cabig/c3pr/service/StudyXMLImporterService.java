package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.StudyValidationException;

import java.util.List;
import java.io.InputStream;
import java.io.Reader;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public interface StudyXMLImporterService {
    List<Study> importStudies(InputStream xmlStream) throws Exception;

    void validate(Study study) throws StudyValidationException;

}
