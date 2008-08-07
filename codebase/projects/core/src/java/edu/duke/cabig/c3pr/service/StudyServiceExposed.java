package edu.duke.cabig.c3pr.service;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;

public interface StudyServiceExposed {

    public void createStudy(Study study);
    
    public void openStudy(List<Identifier> studyIdentifiers);
    
    public void approveStudySiteForActivation(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
    public void activateStudySite(List<Identifier> studyIdentifiers, String nciInstituteCode);

    public void amendStudy(List<Identifier> studyIdentifiers, Object amendedStudyDetails);
    
    public void updateStudySiteProtocolVersion(List<Identifier> studyIdentifiers, String nciInstituteCode, Object version);

    public void closeStudy(List<Identifier> studyIdentifiers);
    
    public void closeStudySite(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
    public void closeStudySites(List<Identifier> studyIdentifiers);
}
