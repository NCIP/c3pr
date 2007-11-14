package edu.duke.cabig.c3pr.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.StudyValidationException;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
@Transactional(readOnly = false, rollbackFor = C3PRCodedException.class,
        noRollbackFor = C3PRBaseRuntimeException.class)
public interface StudySubjectXMLImporterService {
    List<StudySubject> importStudySubjects(InputStream xmlStream) throws C3PRCodedException;

    public StudySubject importStudySubject(StudySubject studySubject) throws C3PRCodedException;
    
    public StudySubject importStudySubject(String registrationXml) throws C3PRCodedException;

    void validate(StudySubject studySubject) throws C3PRCodedException;

}
