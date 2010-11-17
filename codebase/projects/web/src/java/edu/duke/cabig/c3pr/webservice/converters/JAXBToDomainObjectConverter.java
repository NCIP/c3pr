package edu.duke.cabig.c3pr.webservice.converters;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.dao.RegistryStatusDao;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RegistryStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.ConversionException;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.common.Subject;

/**
 * Provides operations to convert JAXB objects, such as {@link Subject}, created
 * during a Web service invocation into C3PR domain objects, such as
 * {@link Participant}.
 * 
 * @author dkrylov
 * 
 */
public interface JAXBToDomainObjectConverter {

	/**
	 * Convert {@link Subject} to a new instance of {@link Participant}.
	 * 
	 * @param subject
	 * @param skipEmptyNameParts
	 *            use null instead of empty string for omitted name parts. Used
	 *            for search-by-example operations.
	 * @return
	 * @throws ConversionException
	 */
	Participant convert(Subject subject, boolean requireIdentifier,
			boolean skipEmptyNameParts) throws ConversionException;

	/**
	 * Updates the given instance of {@link Participant} with new values. Does
	 * not modify identifiers.
	 * 
	 * @param participant
	 * @param subject
	 * @param skipEmptyNameParts
	 *            use null instead of empty string for omitted name parts. Used
	 *            for search-by-example operations.
	 */
	void convert(Participant participant, Subject subject,
			boolean skipEmptyNameParts);

	/**
	 * Converts {@link BiologicEntityIdentifier} into
	 * {@link OrganizationAssignedIdentifier}, enforces validation checks.
	 * 
	 * @param bioId
	 * @return
	 * @throws ConversionException
	 */
	OrganizationAssignedIdentifier convert(edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier bioId)
			throws ConversionException;

	/**
	 * Converts {@link Participant} into its XML {@link Subject} representation.
	 * 
	 * @param p
	 * @return
	 */
	Subject convert(Participant p);

	/**
	 * @param param
	 * @return
	 */
	AdvancedSearchCriteriaParameter convert(
			AdvanceSearchCriterionParameter param);

	/**
	 * Will convert given JAXB study definition to a brand new {@link Study}
	 * domain object. JAXB study definition contains only a limited set of
	 * fields; therefore, other required fields in the {@link Study} object,
	 * which do not exist in the JAXB, will be set to reasonable defaults. See
	 * <b>\documentation\design\essn\StudyUtility\
	 * caBIG_Computational_Independent_Model_Subject_Registry.doc</b> for
	 * details.
	 * 
	 * @param xmlStudy
	 * @return
	 */
	Study convert(StudyProtocolVersion xmlStudy);

	/**
	 * Unlike {
	 * {@link #convert(edu.duke.cabig.c3pr.webservice.studyutility.Study)}
	 * method, this one will only update the given {@link Study} instance by
	 * transferring field values from the JAXB object. Identifiers won't be
	 * transferred.
	 * 
	 * @param study
	 * @param xmlStudy
	 */
	void convert(Study study, StudyProtocolVersion xmlStudy, boolean updateConsents);

	StudyProtocolVersion convert(Study study);

	void setRegistryStatusDao(RegistryStatusDao registryStatusDao);

	RegistryStatusDao getRegistryStatusDao();

	OrganizationAssignedIdentifier convert(DocumentIdentifier docId);

	Consent convertConsent(DocumentVersion doc);

	public abstract edu.duke.cabig.c3pr.webservice.common.Consent convertConsent(Consent c);

	public abstract Consent convertConsentForSearchByExample(edu.duke.cabig.c3pr.webservice.common.Consent xml);

	PermissibleStudySubjectRegistryStatus convert(edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus status);

	public abstract edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus convert(PermissibleStudySubjectRegistryStatus xml);

	public abstract edu.duke.cabig.c3pr.webservice.common.RegistryStatus convert(RegistryStatus domainObj);

}
