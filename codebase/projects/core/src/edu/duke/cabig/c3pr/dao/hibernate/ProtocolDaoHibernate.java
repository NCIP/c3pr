package edu.duke.cabig.c3pr.dao.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.constants.ReferenceDataConstants;
import edu.duke.cabig.c3pr.dao.AbstractBaseDao;
import edu.duke.cabig.c3pr.dao.ProtocolDao;
import edu.duke.cabig.c3pr.domain.Amendment;
import edu.duke.cabig.c3pr.domain.Institution;
import edu.duke.cabig.c3pr.domain.Protocol;
import edu.duke.cabig.c3pr.domain.ProtocolArm;
import edu.duke.cabig.c3pr.domain.ProtocolInstitution;
import edu.duke.cabig.c3pr.domain.ProtocolParticipantRole;
import edu.duke.cabig.c3pr.domain.ProtocolRole;
import edu.duke.cabig.c3pr.domain.ProtocolStatus;
import edu.duke.cabig.c3pr.domain.UserCredentials;
import edu.duke.cabig.c3pr.dto.ProtocolSearchCriteria;
import edu.duke.cabig.c3pr.utils.Constants;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * @author Priyatam
 * 
 */
public class ProtocolDaoHibernate extends AbstractBaseDao<Protocol> implements ProtocolDao 
{
	
	 public Class<Protocol> domainClass() {
	        return Protocol.class;
	 }

	/**
	 * Dummy //TODO remove this
	 */
	public List<Protocol> getAll() throws DataAccessException {
		List<Protocol> sortedList;
        sortedList = getHibernateTemplate().find("from Protocol");
        Collections.sort(sortedList);
        return sortedList;
	}
	
	public void saveProtocol(Protocol protocol) throws Exception {
		// Save protocol.
		updateProtocol(protocol);
	}

	@SuppressWarnings("unchecked")
	public void amendProtocol(Amendment amendment) throws Exception {

		// Get Protocol object from the Amendment.
		Protocol amendmentProtocol = amendment.getProtocol();
		// Retrieve protocol from database.
		Protocol protocol = null;
		ProtocolSearchCriteria psc = new ProtocolSearchCriteria(amendmentProtocol.getPrimaryProtocolIdentifierAsString(), "", "", "", "", "", false,
				"");
		List<Protocol> protocolList = retreiveProtocols(psc);
		if (protocolList != null && protocolList.size() > 0) {
			for (Protocol p : protocolList) {
				if (p.getPrimaryProtocolIdentifierAsString().equalsIgnoreCase(amendment.getProtocol().getPrimaryProtocolIdentifierAsString())) {
					protocol = p;
				}
			}
		} else {
			//throw new DoesNotExistException("Protocol " + amendmentProtocol.getPrimaryProtocolIdentifierAsString()
				//	+ " does not exist in the system. Cannot amend a non-existent protocol.");
		}

		// Add Amendment to the Protocol
		Collection<Amendment> amendmentCollection = protocol.getAmendmentCollection();
		amendmentCollection.add(amendment);

		// Update Protocol
		updateProtocol(protocol);

	}

	/**
	 * checkVerifyProtocol method throws AlreadyExistsException if a particular
	 * protocol exists in the system.
	 * 
	 * @param protocol
	 * @throws AlreadyExistsException
	 * 
	 */
	@SuppressWarnings("unused")
	private void checkVerifyProtocol(Protocol protocol) throws Exception {
		ProtocolSearchCriteria psc = new ProtocolSearchCriteria();
		psc.setProtocolId(protocol.getPrimaryProtocolIdentifierAsString());
		List<Protocol> protocolList = retreiveProtocols(psc);
		if (protocolList != null && protocolList.size() > 0) {
			for (Protocol p : protocolList) {
				if (p.getPrimaryProtocolIdentifierAsString().equalsIgnoreCase(protocol.getPrimaryProtocolIdentifierAsString())) {
					// Protocol exists. Throw AlreadyExistsException.
				//	throw new AlreadyExistsException("Protocol " + protocol.getPrimaryProtocolIdentifierAsString() + " already exists in the system.");
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.c3pr.persistence.dao.ProtocolDAO#updateProtocol(gov.nih.nci.c3pr.domain.protocol.Protocol)
	 */
	public void updateProtocol(Protocol protocol) {
		getHibernateTemplate().saveOrUpdate(protocol);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.c3pr.persistence.dao.ProtocolDAO#retreiveProtocols(gov.nih.nci.c3pr.persistence.dao.ProtocolSearchCriteria)
	 */
	@SuppressWarnings("unchecked")
	public List<Protocol> retreiveProtocols(ProtocolSearchCriteria sc) {

		String queryStr = "Select distinct p from Protocol as p " + "left join p.protocolParticipantRoleCollection pprc "
				+ "left join p.protocolType pt " + "left join p.protocolStatusCollection ps " + "left join p.protocolInstitutionCollection pic ";

		ArrayList paramList = new ArrayList();
		StringBuffer whereClause = new StringBuffer();
		buildRetreiveProtocolsWhereClause(whereClause, paramList, sc);

		if (whereClause.length() > 0)
			queryStr = queryStr + " where " + whereClause;

		List<Protocol> tempColl = getHibernateTemplate().find(queryStr, paramList.toArray());
		List<Protocol> finalColl = new ArrayList();

		// Have to do this processing to only get the protocols whose latest
		// effective dates of their statuses = to the one specified
		// in the protocol search criteria. Limited time prevents this from
		// being done in a query. This can be revisited at another time
		// but is not a crucial item to be concerned with.
		if (sc.getProtocolStatusCd() != null && sc.getProtocolStatusCd().length() > 0
				&& !sc.getProtocolStatusCd().equalsIgnoreCase(ReferenceDataConstants.ALL)) {
			for (Protocol p : tempColl) {
				if (sc.getProtocolStatusCd().equalsIgnoreCase(p.getCurrentProtocolStatus().getProtocolStatusCode().getCode())) {
					finalColl.add(p);
				}
			}
		} else {
			finalColl = tempColl;
		}

		return finalColl;

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.c3pr.persistence.dao.ProtocolDAO#retrieveProtocol(java.lang.Integer)
	 */
	public Protocol retrieveProtocol(Integer protocolId) {
		if (protocolId != null)
			return (Protocol) getHibernateTemplate().load(Protocol.class, protocolId);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.c3pr.persistence.dao.ProtocolDAO#retrieveAmendment(java.lang.Integer)
	 */
	public Amendment retrieveAmendment(Integer amendmentId) {
		if (amendmentId != null)
			return (Amendment) getHibernateTemplate().load(Amendment.class, amendmentId);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.c3pr.persistence.dao.ProtocolDAO#retrieveAmendments(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public Collection<Amendment> retrieveAmendments(Integer protocolId) {
		String queryStr = "select a from Amendment as a, Protocol as p where a.protocol = p and p.id = ? ";
		return (Collection<Amendment>) getHibernateTemplate().find(queryStr, protocolId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.c3pr.persistence.dao.ProtocolDAO#retrieveCurrentAmendment(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ProtocolStatus> retrieveProtocolStatus(Integer protocolId) {

		String queryStr = "select ps from ProtocolStatus as ps, Protocol as p where ps.id = p and p.id = ? ";
		return (Collection<ProtocolStatus>) getHibernateTemplate().find(queryStr, protocolId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.c3pr.persistence.dao.ProtocolDAO#retrieveProtocolInstitution(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ProtocolInstitution> retrieveProtocolInstitution(Integer intProtocolId) {
		String queryStr = "select pi from ProtocolInstitution as pi, Protocol as p where pi.id = p and p.id = ? ";
		return (Collection<ProtocolInstitution>) getHibernateTemplate().find(queryStr, intProtocolId);
	}

	/**
	 * Method to build the where clause of the query to retrieve Participants
	 * based on the SearchCriteria.
	 * 
	 * @param whereClause
	 * @param paramList
	 * @param sc
	 */
	@SuppressWarnings("unchecked")
	private void buildRetreiveProtocolsWhereClause(StringBuffer whereClause, ArrayList paramList, ProtocolSearchCriteria sc) {

		// Add wildcard characters to the end of the name fields if specified
		String lastNameWithOrWithoutWildcard = "";
		String firstNameWithOrWithoutWildcard = "";

		 
		try {sc.setPiFirstName(sc.getPiFirstName().trim());} catch (NullPointerException ex){}
		try {sc.setPiLastName(sc.getPiLastName().trim());} catch (NullPointerException ex){}		
		try {sc.setProtocolId(sc.getProtocolId().trim());} catch (NullPointerException ex){}

		if (sc.isAppendWildCardsToNames()) {
			lastNameWithOrWithoutWildcard = wildCard(StringUtils.initString(sc.getPiLastName()));
			firstNameWithOrWithoutWildcard = wildCard(StringUtils.initString(sc.getPiFirstName()));
		} else {
			lastNameWithOrWithoutWildcard = StringUtils.initString(sc.getPiLastName());
			firstNameWithOrWithoutWildcard = StringUtils.initString(sc.getPiFirstName());
		}

		if (sc.getPiLastName() != null && !sc.getPiLastName().equalsIgnoreCase("")) {
			if (sc.isAppendWildCardsToNames()) {
				whereClause.append("(pprc.participant.lastName=(?) or pprc.participant.lastName like ?)");
				paramList.add(StringUtils.initString(sc.getPiLastName().toUpperCase()));
				paramList.add(lastNameWithOrWithoutWildcard.toUpperCase());
			} else {
				whereClause.append("pprc.participant.lastName=(?)");
				paramList.add(StringUtils.initString(sc.getPiLastName().toUpperCase()));
			}
		}

		if (sc.getPiFirstName() != null && !sc.getPiFirstName().equalsIgnoreCase("")) {
			String fNameWhere = "";
			if (sc.isAppendWildCardsToNames()) {
				fNameWhere = "(pprc.participant.firstName=(?) or pprc.participant.firstName like ?)";
				paramList.add(StringUtils.initString(sc.getPiFirstName().toUpperCase()));
				paramList.add(firstNameWithOrWithoutWildcard.toUpperCase());
			} else {
				fNameWhere = "pprc.participant.firstName=?";
				paramList.add(StringUtils.initString(sc.getPiFirstName().toUpperCase()));
			}
			if (whereClause.length() > 0)
				whereClause.append(" and ");
			whereClause.append(" " + fNameWhere);
		}

		if (sc.getProtocolId() != null && sc.getProtocolId().length() > 0 && !sc.getProtocolId().equalsIgnoreCase(ReferenceDataConstants.ALL)) {
			if (whereClause.length() != 0) {
				whereClause.append(" and ");
			}

			whereClause.append("pic.protocolIdentifier.protocolIdentifierCode like ?");
			paramList.add(wildCard(StringUtils.initString(sc.getProtocolId())));

		}

		if (sc.getProtocolTypeCd() != null && sc.getProtocolTypeCd().length() > 0
				&& !sc.getProtocolTypeCd().equalsIgnoreCase(ReferenceDataConstants.ALL)) {
			if (whereClause.length() != 0) {
				whereClause.append(" and ");
			}
			whereClause.append("pt.code=?");
			paramList.add(StringUtils.initString(sc.getProtocolTypeCd()));
		}

		if (sc.getProtocolStatusCd() != null && sc.getProtocolStatusCd().length() > 0
				&& !sc.getProtocolStatusCd().equalsIgnoreCase(ReferenceDataConstants.ALL)) {
			if (whereClause.length() != 0) {
				whereClause.append(" and ");
			}
			whereClause.append("ps.protocolStatusCode.code=?");
			paramList.add(StringUtils.initString(sc.getProtocolStatusCd()));
		}

		if (sc.getBranchCd() != null && sc.getBranchCd().length() > 0 && !sc.getBranchCd().equalsIgnoreCase(ReferenceDataConstants.ALL)) {
			if (whereClause.length() != 0) {
				whereClause.append(" and ");
			}
			whereClause.append("pic.institution.code=?");
			paramList.add(StringUtils.initString(sc.getBranchCd()));
		}
		
		if (sc.getProtocolFilter() == null || "".equals(sc.getProtocolFilter().trim())){
			//This query would be executed when the user is not associated with the protocols.			
			if (whereClause.length() != 0) {
				whereClause.append(" and ");
			}
			whereClause.append("p.id is null");	
		}else{
	    	//This query would be executed when the user is associated with the protocols.			
			if(!Constants.ALL_PROTOCOLS.equals(sc.getProtocolFilter().trim())){
				if (whereClause.length() != 0) {
					whereClause.append(" and ");
				}
				whereClause.append("p.id in (?)");
				paramList.add(StringUtils.initString(sc.getProtocolFilter()));
			}
		}		
	}

	/**
	 * Method that appends a wild card to the String passed in. The wildcards
	 * are SQL wildcards.
	 * 
	 * @param _name
	 * @return String A string with a wildcard appended to it.
	 */
	protected String wildCard(String _name) {
		String trimmedName = _name.toString().trim();

		if (trimmedName == null || trimmedName.equals("") || trimmedName.length() == 0) {
			return "";
		}

		if (trimmedName.length() > 0 && !trimmedName.endsWith("%")) {
			trimmedName = trimmedName + "%";
		}

		if (trimmedName.length() > 0 && !trimmedName.startsWith("%")) {
			trimmedName = "%" + trimmedName;
		}

		return trimmedName;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.c3pr.persistence.dao.ProtocolDAO#retrieveProtocolsAvailableForRegistration()
	 */
	public Collection<Protocol> retrieveProtocolsAvailableForRegistration(String protocolFilter) {
		return retrieveProtocolsAvailableForRegistration(null, protocolFilter);
	}

	@SuppressWarnings("unchecked")
	public Collection<Institution> retrieveInstitutionForProtocol(Protocol protocol) {
		if (protocol == null)
		{
			return getHibernateTemplate()
					.find(
							"select distinct inst from Institution as inst, ProtocolInstitution pi, Protocol p where pi.protocol = p and pi.institution = inst and inst.orgTypeCd = ?",ReferenceDataConstants.BRANCH);
		}
		else
		{
			String queryString = "select distinct inst from Institution as inst, ProtocolInstitution pi, Protocol p where pi.protocol = p and pi.institution = inst and p = ? and inst.orgTypeCd = ?";
			Object[] parameters = new Object[]{protocol,ReferenceDataConstants.BRANCH};
			return getHibernateTemplate().find(queryString,parameters);
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<Protocol> retrieveProtocolsAvailableForRegistration(Institution institution, String protocolFilter) {
		String queryString = "from Protocol as p where (p in " +
		// Select protocols which has status of Open as latest status and have
		// IRB approval
				" (select ps1.protocol from ProtocolStatus as ps1,ProtocolStatus as ps2" + " where ps1!=ps2 and" + " ps1.protocol = ps2.protocol and"
				+ " ps1.protocolStatusCode.code = 'O' and" + " ps2.protocolStatusCode.code = 'I' and" + " ps1.effectiveDate = "
				+ " (select max(ps3.effectiveDate) from ProtocolStatus ps3 where ps3.protocol=ps1.protocol)" + " and " + " ps1.protocol in" +
				// Selects protocols whose latest amendment has eligibility
				// checklist as required and approved
				" (select a1.protocol from Amendment a1	where"
				+ " a1.date = (select max(a2.date) from Amendment a2 where a2.protocol = a1.protocol) and"
				+ " a1.eligibilityChecklistRequiredFlag = 'Y' and" + " a1 in"
				+ " (select ed.amendment from EligibilityDefinition as ed where ed.status.code = 'A')" + " )" + " )" +

				"or p in " +
				// Select protocols which has status of Open as latest status
				// and have IRB approval
				" (select ps1.protocol from ProtocolStatus as ps1,ProtocolStatus as ps2" + " where ps1!=ps2 and" + " ps1.protocol = ps2.protocol and"
				+ " ps1.protocolStatusCode.code = 'O' and" + " ps2.protocolStatusCode.code = 'I' and" + " ps1.effectiveDate = "
				+ " (select max(ps3.effectiveDate) from ProtocolStatus ps3 where ps3.protocol=ps1.protocol)" + " and " + " ps1.protocol in" +
				// Selects protocols whose latest amendment has eligibility
				// checklist as not required
				" (select a1.protocol from Amendment a1	where"
				+ " a1.date = (select max(a2.date) from Amendment a2 where a2.protocol = a1.protocol) and"
				+ " a1.eligibilityChecklistRequiredFlag = 'N'" + " )" + " ))";
		// Filtering the protocols
		if (protocolFilter == null || protocolFilter.trim().equalsIgnoreCase("")) {
			// This query would be executed when the user is not associated with
			// the protocols.
			queryString = queryString + " and p.id is null";
		} else {
			// This query would be executed when the user is associated with the
			// protocols.
			if (!protocolFilter.trim().equals("All.Protocols")) {
				queryString = queryString + " and p.id in (" + protocolFilter + ")";
			}
		}

		if (institution == null)
			return getHibernateTemplate().find(queryString);

		String andClause = " and ( p in (select pi.protocol from ProtocolInstitution pi, Institution inst where pi.institution = inst and pi.protocol = p and inst = ?))";
		return getHibernateTemplate().find(queryString + andClause, institution);

	}

	public Institution retrieveInstitution(String primaryId) {
		if (primaryId != null)
			return (Institution) getHibernateTemplate().load(Institution.class, primaryId);
		return null;
	}

	public ProtocolArm retrieveProtocolArm(Integer primaryId) {
		if (primaryId != null)
			return (ProtocolArm) getHibernateTemplate().load(ProtocolArm.class, primaryId);
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ProtocolRole> retrieveAssociatableProtocolRole() {
		return (List<ProtocolRole>) getHibernateTemplate().loadAll(ProtocolRole.class);

	}

	
		
	@SuppressWarnings("unchecked")
	public List<UserCredentials> retrieveUserCredentialsByUser(String c3prUser) throws DataAccessException {
		
		String query = "select distinct uc from UserCredentials as uc where  uc.c3prUser = ?";
		
			
			List result = getHibernateTemplate().find(query,c3prUser);
			
			return result;
	}
		
	public UserCredentials retrieveUserCredentials(Integer primaryId) {
		if (primaryId != null)
			return (UserCredentials) getHibernateTemplate().load(UserCredentials.class, primaryId);
		return null;
	}

	public void saveUserCredentials(UserCredentials userCredentials) {
		getHibernateTemplate().saveOrUpdate(userCredentials);
	}
	
	public Collection<ProtocolParticipantRole> retrieveProtocolParticipantRole(Integer participantId){
		
		String query = "select ppr from ProtocolParticipantRole ppr where ppr.participant.id = ?";		
		Collection protocoltParticipantRoleCol = getHibernateTemplate().find(query,participantId);
		return protocoltParticipantRoleCol;
	}
}

