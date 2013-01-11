/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;

/**
 * User: kherm
 * 
 * @author kherm manav.kher@semanticbits.com
 */
@Transactional(readOnly = false, rollbackFor = C3PRCodedException.class, noRollbackFor = C3PRBaseRuntimeException.class)
public interface StudySubjectXMLImporterService {
    public List<StudySubject> importStudySubjects(InputStream xmlStream, File importXMLResult)
                    throws C3PRCodedException;
    public StudySubject importStudySubject(String registrationXml) throws C3PRCodedException;
    

}
