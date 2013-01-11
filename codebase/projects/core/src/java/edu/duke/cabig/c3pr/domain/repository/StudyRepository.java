/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain.repository;

import java.util.Date;
import java.util.List;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.RegistryStatusReason;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.StudyValidationException;

public interface StudyRepository {

    public void buildAndSave(Study study) throws C3PRCodedException;

    public void validate(Study study) throws StudyValidationException, C3PRCodedException;

    public List<Study> searchByCoOrdinatingCenterId(OrganizationAssignedIdentifier identifier)
                    throws C3PRCodedException;

    public void save(Study study) throws C3PRCodedException;

    public Study merge(Study study);

    public void refresh(Study study);

    public void reassociate(Study study);

    public void clear();

    public Study createStudy(Study study);

    public Study createStudy(List<Identifier> studyIdentifiers);

    public Study createAndOpenStudy(Study study);

    public Study openStudy(List<Identifier> studyIdentifiers);

    public StudySite activateStudySite(List<Identifier> studyIdentifiers, String nciInstituteCode, Date effectiveDate);

    public StudySite activateStudySite(List<Identifier> studyIdentifiers, StudySite studySite, Date effectiveDate);

    public Study amendStudy(List<Identifier> studyIdentifiers, Study study);

    public StudySite updateStudySiteProtocolVersion(List<Identifier> studyIdentifiers,
                    String nciInstituteCode, String version);

    public Study closeStudyToAccrual(List<Identifier> studyIdentifiers);

    public Study closeStudyToAccrualAndTreatment(List<Identifier> studyIdentifiers);

    public Study temporarilyCloseStudy(List<Identifier> studyIdentifiers);

    public Study temporarilyCloseStudyToAccrualAndTreatment(List<Identifier> studyIdentifiers);

    public Study updateStudyStatus(List<Identifier> studyIdentifiers,
                    CoordinatingCenterStudyStatus status);

    public StudySite closeStudySiteToAccrual(List<Identifier> studyIdentifiers, String nciInstituteCode, Date effectiveDate);

    public StudySite closeStudySiteToAccrualAndTreatment(List<Identifier> studyIdentifiers, String nciInstituteCode, Date effectiveDate);

    public StudySite temporarilyCloseStudySiteToAccrual(List<Identifier> studyIdentifiers, String nciInstituteCode, Date effectiveDate);

    public StudySite temporarilyCloseStudySiteToAccrualAndTreatment(List<Identifier> studyIdentifiers, String nciInstituteCode, Date effectiveDate);

    public List<StudySite> closeStudySites(List<Identifier> studyIdentifiers, Date effectiveDate);

    public Study getUniqueStudy(List<Identifier> studyIdentifiers);

    public Study createAmendment(List<Identifier> identifiers);

	public Study applyAmendment(List<Identifier> identifiers);

	public Study applyAmendment(List<Identifier> identifiers, StudyVersion studyVersion);
	
	public List<Study> getByIdentifiers(List<Identifier> studyIdentifiers);
	
	public List<Study> search(List<AdvancedSearchCriteriaParameter> searchParameters);
	
	public Study getByPrimaryIdentifier(Identifier identifier);

	public boolean isSecondaryReasonAssociatedToExistingStudySubjects(RegistryStatusReason registryStatusReason);

}
