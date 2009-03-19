package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.emory.mathcs.backport.java.util.Collections;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

/**
 * The Class ParticipantDao.
 * 
 * @author Priyatam, kulasekaran
 */
public class ParticipantDao extends GridIdentifiableDao<Participant> implements
                MutableDomainObjectDao<Participant> {

    /** The SUBSTRING_match_properties. */
    private List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("lastName");

    /** The EXACT_ match_properties. */
    private List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

    /** The log. */
    private static Log log = LogFactory.getLog(ParticipantDao.class);

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    public Class<Participant> domainClass() {
        return Participant.class;
    }

    /**
     * /* Searches based on an example object. Typical usage from your service class: - If you want
     * to search based on diseaseCode, monitorCode,
     * <li><code>Participant participant = new Participant();</li></code>
     * <li>code>participant.setLastName("last_namee");</li>
     * </code>
     * <li>code>participantDao.searchByExample(study)</li>
     * </code>
     * 
     * @param participant the participant
     * @param isWildCard the is wild card
     * 
     * @return list of matching participant objects based on your sample participant object
     */
    
    public List<Participant> searchByExample(Participant participant, boolean isWildCard) {
        Example example = Example.create(participant).excludeZeroes().ignoreCase();
        Criteria participantCriteria = getSession().createCriteria(Participant.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) ;
        example.excludeProperty("doNotUse").enableLike(MatchMode.ANYWHERE);
        if (isWildCard) {
            participantCriteria.add(example);
            
            if (participant.getIdentifiers().size() > 1) {
                participantCriteria.createCriteria("identifiers").add(
                                Restrictions.ilike("value", "%"
                                                + participant.getIdentifiers().get(0).getValue()
                                                + "%")).add(
                                Restrictions.ilike("value", "%"
                                                + participant.getIdentifiers().get(1).getValue()
                                                + "%"));
            }
            else if (participant.getIdentifiers().size() > 0) {
                participantCriteria.createCriteria("identifiers").add(
                                Restrictions.ilike("value", "%"
                                                + participant.getIdentifiers().get(0).getValue()
                                                + "%"));
            }
            return participantCriteria.list();
        }
        return participantCriteria.add(example).list();
    }
    
    /**
     * Default Search without a Wildchar.
     * 
     * @param participant the participant
     * 
     * @return Search results
     * 
     * @see edu.duke.cabig.c3pr.dao.searchByExample(Participant participant, boolean isWildCard)
     */
    public List<Participant> searchByExample(Participant participant) {
        return searchByExample(participant, true);
    }

    /**
     * Search by org identifier.
     * 
     * @param identifier the id
     * 
     * @return the list< participant>
     */
    @SuppressWarnings("unchecked")
    public List<Participant> searchByOrgIdentifier(OrganizationAssignedIdentifier identifier) {
    	
    	// Doing this check to prevent a SQL GRAMMER Exception when one of the parameters is null or 
    	// we have to use criteria API or branch the query if there are null values
    	if(identifier.getType()!=null && identifier.getHealthcareSite()!=null && identifier.getValue()!=null){
    		if(identifier.getHealthcareSite().getId() != null){
    			return (List<Participant>) getHibernateTemplate()
                .find("select P from Participant P, Identifier I where I.healthcareSite.id=?" + " and I.value=? and I.type=? and I=any elements(P.identifiers)",
                                new Object[] { identifier.getHealthcareSite().getId(),identifier.getValue(), identifier.getType() });
    		}else{
    			return (List<Participant>) getHibernateTemplate()
                .find("select P from Participant P, Identifier I where I.healthcareSite.nciInstituteCode=?" + " and I.value=? and I.type=? and I=any elements(P.identifiers)",
                                new Object[] { identifier.getHealthcareSite().getNciInstituteCode(),identifier.getValue(), identifier.getType() });
    		}
        
    	} 
    	return new ArrayList<Participant>();
    }
    
    /**
     * Search by system assigned identifier.
     * 
     * @param id the id
     * 
     * @return the list< participant>
     */
    @SuppressWarnings("unchecked")
    public List<Participant> searchBySystemAssignedIdentifier(SystemAssignedIdentifier id) {
    	
    	// Doing this check to prevent a SQL GRAMMER Exception when one of the parameters is null or 
    	// we have to use criteria API or branch the query if there are null values
    	if(id.getType()!=null && id.getSystemName()!=null && id.getValue()!=null){
        return (List<Participant>) getHibernateTemplate()
                        .find(
                                        "select P from Participant P, Identifier I where I.systemName=?"
                                                        + " and I.value=? and I.type=? and I=any elements(P.identifiers)",
                                        new Object[] { id.getSystemName(),
                                                id.getValue(), id.getType() });
    	} 
    	return new ArrayList<Participant>();
    }

    
    /**
     * Search by identifier.
     * 
     * @param id the id
     * 
     * @return the list< participant>
     */
    @SuppressWarnings("unchecked")
    public List<Participant> searchByIdentifier(int id) {
        return (List<Participant>) getHibernateTemplate().find(
                                        "select P from Participant P, Identifier I where I.id=? and I=any elements(P.identifiers)",
                                        new Object[] {id});
    }

    /**
     * Gets all Participants.
     * 
     * @return the Participants
     * 
     * @throws DataAccessException the data access exception
     */
    public List<Participant> getAll() throws DataAccessException {
        return getHibernateTemplate().find("from Participant");
    }

    /**
     * Gets the by first name.
     * 
     * @param name the name
     * 
     * @return the by first name
     * 
     * @throws DataAccessException the data access exception
     */
    public List<Participant> getByFirstName(String name) throws DataAccessException {
        return getHibernateTemplate().find("from Participant p where p.firstName = ?", name);
    }

    /**
     * Gets the subject identifiers with mrn.
     * 
     * @param MRN the mRN
     * @param site the site
     * 
     * @return the subject identifiers with mrn
     * 
     * @throws DataAccessException the data access exception
     */
    public List<OrganizationAssignedIdentifier> getSubjectIdentifiersWithMRN(String MRN,
                    HealthcareSite site) throws DataAccessException {
        List<OrganizationAssignedIdentifier> orgAssignedIdentifiers = (List<OrganizationAssignedIdentifier>) getHibernateTemplate()
                        .find("from Identifier I where I.type='MRN' and I.healthcareSite = ?", site);
        List<OrganizationAssignedIdentifier> subIdentifiers = new ArrayList<OrganizationAssignedIdentifier>();
        for (OrganizationAssignedIdentifier subIdent : orgAssignedIdentifiers) {
            if (subIdent.getValue().equalsIgnoreCase(MRN)) {
                subIdentifiers.add(subIdent);
            }
        }
        return subIdentifiers;
    }

    /**
     * An overloaded method to return Participant Object along with the collection of associated
     * identifiers.
     * 
     * @param id the id
     * @param withIdentifiers the with identifiers
     * 
     * @return Participant Object based on the id
     * 
     * @throws DataAccessException      */
    public Participant getById(int id, boolean withIdentifiers) {

        Participant participant = (Participant) getHibernateTemplate().get(domainClass(), id);
        if (withIdentifiers) {
            List<Identifier> identifiers = participant.getIdentifiers();
            int size = identifiers.size();
        }
        List<ContactMechanism> contactMechanisms = participant.getContactMechanisms();
        contactMechanisms.size();

        return participant;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.cabig.ctms.dao.AbstractDomainObjectDao#getById(int)
     */
    @Override
    public Participant getById(int id) {
        return super.getById(id);
    }

    /**
     * Gets by the subnames.
     * 
     * @param subnames the subnames
     * @param criterionSelector the criterion selector
     * 
     * @return the by subnames
     */
    public List<Participant> getBySubnames(String[] subnames, int criterionSelector) {

        switch (criterionSelector) {
            case 0:
                SUBSTRING_MATCH_PROPERTIES = Arrays.asList("lastName");
                break;
            case 1:
                SUBSTRING_MATCH_PROPERTIES = Arrays.asList("identifiers");
                break;
            case 6:
                SUBSTRING_MATCH_PROPERTIES = Arrays.asList("firstName");
        }
        return findBySubname(subnames, SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    }

    /**
     * Initialize.
     * 
     * @param participant the participant
     * 
     * @throws DataAccessException the data access exception
     */
    @Transactional(readOnly = false)
    public void initialize(Participant participant) throws DataAccessException {
        getHibernateTemplate().initialize(participant.getIdentifiers());
        getHibernateTemplate().initialize(participant.getContactMechanisms());
        getHibernateTemplate().initialize(participant.getRaceCodes());
    }
    
    /**
     * Initialize study subjects.
     * 
     * @param participant the participant
     * 
     * @throws DataAccessException the data access exception
     */
    @Transactional(readOnly = false)
    public void initializeStudySubjects(Participant participant) throws DataAccessException {
        getHibernateTemplate().initialize(participant.getStudySubjects());
    }
    
    /**
     * Reassociate.
     * 
     * @param p the participant
     */
    @Transactional(readOnly = false)
    public void reassociate(Participant p) {
        getHibernateTemplate().update(p);
        
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao#save(gov.nih.nci.cabig.ctms.domain.MutableDomainObject)
     */
    @Transactional(readOnly = false)
    public void save(Participant obj) {
        getHibernateTemplate().saveOrUpdate(obj);
    }

    /**
     * Merge.
     * 
     * @param participant the participant
     * 
     * @return the participant
     */
    @Transactional(readOnly = false)
    public Participant merge(Participant participant) {
        return (Participant) getHibernateTemplate().merge(participant);
    }
    
    /**
	 * Gets participants by identifiers.
	 * 
	 * @param participantIdentifiers
	 *            the participant identifiers
	 * 
	 * @return the by identifiers
	 */
    public List<Participant> getByIdentifiers(List<Identifier> identifiers) {
        List<Participant> participants = new ArrayList<Participant>();
        for (Identifier identifier : identifiers) {
            if (identifier instanceof SystemAssignedIdentifier){ 
            	participants.addAll(searchBySysIdentifier((SystemAssignedIdentifier) identifier));
            }
            else if (identifier instanceof OrganizationAssignedIdentifier) {
            	participants.addAll(searchByOrgIdentifier((OrganizationAssignedIdentifier) identifier));
            }
        }
        Set<Participant> set = new HashSet<Participant>();
        set.addAll(participants);
        return new ArrayList<Participant>(set);
    }
    
    /**
	 * Search by sys identifier.
	 * 
	 * @param id
	 *            the id
	 * 
	 * @return the list< study subject>
	 */
    @SuppressWarnings("unchecked")
    public List<Participant> searchBySysIdentifier(SystemAssignedIdentifier id) {
        return (List<Participant>) getHibernateTemplate()
                        .find(
                                        "select S from Participant S, SystemAssignedIdentifier I where I.systemName=?"
                                                        + " and I.value=? and I.type=? and I=any elements(S.identifiers)",
                                        new Object[] { id.getSystemName(),
                                                id.getValue(), id.getType() });
    }
}
