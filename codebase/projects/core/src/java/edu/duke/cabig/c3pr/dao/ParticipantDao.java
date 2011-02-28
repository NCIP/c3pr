package edu.duke.cabig.c3pr.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;
import com.semanticbits.querybuilder.QueryBuilder;
import com.semanticbits.querybuilder.QueryBuilderDao;
import com.semanticbits.querybuilder.QueryGenerator;
import com.semanticbits.querybuilder.TargetObject;

import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
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
    
    private QueryBuilderDao queryBuilderDao;

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    public Class<Participant> domainClass() {
        return Participant.class;
    }

    /**
     * Searches based on an example object. Typical usage from your service class: - If you want
     * to search based on diseaseCode, monitorCode,
     * <li><code>Participant participant = new Participant();</li></code>
     * <li>code>participant.setLastName("last_namee");</li>
     * </code>
     * <li>code>participantDao.searchByExample(study)</li>
     * </code>
     * 
     * @param participant the participant
     * @param isWildCard the is wild card
     * @param useAddress if set to true and {@link Address} is present, use it for the search.
     * @param useContactInfo if set to true and {@link ContactMechanism} is present, use it for the search.
     * @return list of matching participant objects based on your sample participant object
     */    
    public List<Participant> searchByExample(Participant participant, boolean isWildCard, boolean useAddress, boolean useContactInfo, boolean forceEager) {
        Example example = Example.create(participant).excludeZeroes().ignoreCase();
        Criteria participantCriteria = getSession().createCriteria(Participant.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) ;
        example.excludeProperty("doNotUse").enableLike(MatchMode.ANYWHERE);
        if (forceEager) {
        	// TODO: The following lines may cause Participant.identifiers to contain duplicate entries!
        	// In order to avoid that, Participant.identifiers will need to be changed from List to Set.
        	// Related to http://www.jroller.com/eyallupu/entry/hibernate_exception_simultaneously_fetch_multiple.
        	participantCriteria.setFetchMode("identifiers", FetchMode.JOIN);
        	participantCriteria.setFetchMode("raceCodeAssociations", FetchMode.JOIN);
        	participantCriteria.setFetchMode("contactMechanisms", FetchMode.JOIN);
        }
        if (isWildCard) {
            participantCriteria.add(example);
            
            if (participant.getIdentifiers().size() > 0) {
            	Criterion identifierCriterion = Restrictions.ilike("value", "%"
                        + participant.getIdentifiers().get(0).getValue()
                        + "%");
            	for(int i=1 ; i<participant.getIdentifiers().size() ; i++){
            		 identifierCriterion = Restrictions.or(identifierCriterion, Restrictions.ilike("value", "%"
                                                + participant.getIdentifiers().get(i).getValue()
                                                + "%"));
            	}
            	participantCriteria.createCriteria("identifiers").add(identifierCriterion);
            }
			final Address address = participant.getAddressInternal();
			if (useAddress && address != null) {
				final Criteria addrCrit = participantCriteria
						.createCriteria("addresses");
				if (StringUtils.isNotBlank(address.getStreetAddress()))
					addrCrit.add(Restrictions.ilike("streetAddress", "%"
							+ address.getStreetAddress() + "%"));
				if (StringUtils.isNotBlank(address.getCity()))
					addrCrit.add(Restrictions.ilike("city", "%"
							+ address.getCity() + "%"));
				if (StringUtils.isNotBlank(address.getStateCode()))
					addrCrit.add(Restrictions.ilike("stateCode", "%"
							+ address.getStateCode() + "%"));
				if (StringUtils.isNotBlank(address.getCountryCode()))
					addrCrit.add(Restrictions.ilike("countryCode", "%"
							+ address.getCountryCode() + "%"));
				if (StringUtils.isNotBlank(address.getPostalCode()))
					addrCrit.add(Restrictions.ilike("postalCode", "%"
							+ address.getPostalCode() + "%"));
			}
			if (useContactInfo) {				
				List<Criterion> criterions = new ArrayList<Criterion>();
				if (StringUtils.isNotBlank(participant.getEmail())) {
					criterions.add(Example.create(
							new ContactMechanism(ContactMechanismType.EMAIL,
									participant.getEmail())).enableLike()
							.ignoreCase());
				}
				if (StringUtils.isNotBlank(participant.getPhone())) {
					criterions.add(Example.create(
							new ContactMechanism(ContactMechanismType.PHONE,
									participant.getPhone())).enableLike()
							.ignoreCase());
				}
				if (StringUtils.isNotBlank(participant.getFax())) {
					criterions.add(Example.create(
							new ContactMechanism(ContactMechanismType.Fax,
									participant.getFax())).enableLike()
							.ignoreCase());
				}
				if (!criterions.isEmpty()) {
					Disjunction disjunction = Restrictions.disjunction();
					for (Criterion criterion : criterions) {
						disjunction.add(criterion);
					}
					final Criteria contactCrit = participantCriteria
							.createCriteria("contactMechanisms");
					contactCrit.add(disjunction);
				}
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
     * @see #searchByExample(Participant, boolean, boolean, boolean)
     * @param participant
     * @param isWildCard
     * @return
     */
    public List<Participant> searchByExample(Participant participant, boolean isWildCard) {
    	return searchByExample(participant, isWildCard, false, false,false);
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
    	
    	List <Participant> participantList = new ArrayList<Participant>();
    	Criteria participantCriteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(Participant.class);
    	Criteria organizationAssignedIdentifiersCriteria = participantCriteria.createCriteria("identifiers");
    	Criteria healthcareSiteCriteria = organizationAssignedIdentifiersCriteria.createCriteria("healthcareSite");
    	
    	if(identifier.getType()!=null && identifier.getHealthcareSite()!=null && identifier.getValue()!=null){
    		if(identifier.getHealthcareSite().getId() != null){
    			healthcareSiteCriteria.add(Expression.eq("id", identifier.getHealthcareSite().getId()));
    	    	organizationAssignedIdentifiersCriteria.add(Expression.eq("typeInternal", identifier.getTypeInternal()));
    	    	organizationAssignedIdentifiersCriteria.add(Expression.eq("value", identifier.getValue()));
    		}else{
    			Criteria identifiersAssignedToOrganizationCriteria = healthcareSiteCriteria.createCriteria("identifiersAssignedToOrganization");

    			identifiersAssignedToOrganizationCriteria.add(Expression.eq("value", identifier.getHealthcareSite().getPrimaryIdentifier()));
    			identifiersAssignedToOrganizationCriteria.add(Expression.eq("primaryIndicator", Boolean.TRUE));
    			organizationAssignedIdentifiersCriteria.add(Expression.eq("typeInternal", identifier.getType().getName()));
    	    	organizationAssignedIdentifiersCriteria.add(Expression.eq("value", identifier.getValue()));
    		}
    		return participantCriteria.list();
    	}
		return participantList;
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
                    .find("select P from Participant P, Identifier I where I.systemName=?"
                         + " and I.value=? and I.typeInternal=? and I=any elements(P.identifiers)",
                            new Object[] { id.getSystemName(),id.getValue(), id.getType() });
    	} 
    	return new ArrayList<Participant>();
    }

    
    /**
     * Search by identifier.
     * 
     * @param id the id
     * @return the list< participant>
     */
    @SuppressWarnings("unchecked")
    public List<Participant> searchByIdentifier(int id) {
        return (List<Participant>) getHibernateTemplate().find(
                                        "select P from Participant P, Identifier I where I.id=? and I=any elements(P.identifiers)",
                                        new Object[] {id});
    }

    /**TODO: Add the organization constraint to the hql query.
     * Search by PRIMARY identifier. Returns null if no participant is found.
     * 
     * @param id the id
     * @return the participant
    @SuppressWarnings("unchecked")
    public Participant searchByPrimaryIdentifier(String id) {
        return (Participant) getHibernateTemplate().find(
               "select P from Participant P, Identifier I where I.id=? and I.primaryIndicator='1' and I=any elements(P.identifiers)",
               new Object[] {id});
    } 
    */
    
    /**
     * Gets all Participants.
     * 
     * @return the Participants
     * @throws DataAccessException the data access exception
     */
    public List<Participant> getAll() throws DataAccessException {
        return getHibernateTemplate().find("from Participant");
    }

    /**
     * Gets the by first name.
     * 
     * @param name the name
     * @return the by first name
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
     * @return the subject identifiers with mrn
     * @throws DataAccessException the data access exception  
     */
    public List<OrganizationAssignedIdentifier> getSubjectIdentifiersWithMRN(String MRN,
                    HealthcareSite site) throws DataAccessException {
        return (List<OrganizationAssignedIdentifier>) getHibernateTemplate()
                        .find("Select I from Identifier I, Participant P where I=any elements(P.identifiers) and I.typeInternal='MRN' and I.healthcareSite = ? and lower(I.value) = ?", new Object[]{site,MRN});
    }


    /* (non-Javadoc)
     * @see gov.nih.nci.cabig.ctms.dao.AbstractDomainObjectDao#getById(int)
     */
    @Override
    public Participant getById(int id) {
        Participant participant = super.getById(id);
        initialize(participant);
        return participant;
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
        for(OrganizationAssignedIdentifier identifier : participant.getOrganizationAssignedIdentifiers()){
        	getHibernateTemplate().initialize(identifier.getHealthcareSite().getIdentifiersAssignedToOrganization());
        }
        getHibernateTemplate().initialize(participant.getContactMechanisms());
        for(ContactMechanism contactMechanism : participant.getContactMechanisms()){
        	getHibernateTemplate().initialize(contactMechanism.getContactMechanismUseAssociation());
        }
        getHibernateTemplate().initialize(participant.getRaceCodeAssociations());
        for(HealthcareSite  healthcareSite: participant.getHealthcareSites()){
        	getHibernateTemplate().initialize(healthcareSite.getIdentifiersAssignedToOrganization());
        }
        getHibernateTemplate().initialize(participant.getCustomFieldsInternal());
        getHibernateTemplate().initialize(participant.getStudySubjectDemographics());
        getHibernateTemplate().initialize(participant.getAddresses());
        for(Address address : participant.getAddresses()){
        	getHibernateTemplate().initialize(address.getAddressUseAssociation());
        }
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
                        .find("select S from Participant S, SystemAssignedIdentifier I where I.systemName=?"
                               + " and I.value=? and I.typeInternal=? and I=any elements(S.identifiers)",
                                   new Object[] { id.getSystemName(), id.getValue(), id.getTypeInternal() });
    }
    
    
	public List<Participant> search(List<AdvancedSearchCriteriaParameter> searchParameters){
		String hql = generateHQL(searchParameters);
		return search(hql);
	}
	
	public List<Participant> search(List<AdvancedSearchCriteriaParameter> searchParameters, String fileLocation){
		String hql = generateHQL(searchParameters, fileLocation);
		return search(hql);
	}
	
	public String generateHQL(List<AdvancedSearchCriteriaParameter> searchParameters){
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("subject-advanced-search.xml");
		return generateHQL(searchParameters, inputStream);
	}
	
	private String generateHQL(
			List<AdvancedSearchCriteriaParameter> searchParameters,
			InputStream inputStream) {
		try {
			QueryBuilder queryBuilder = new QueryBuilder();
			Unmarshaller unmarshaller = JAXBContext.newInstance(
					"com.semanticbits.querybuilder").createUnmarshaller();
			queryBuilder = (QueryBuilder) unmarshaller.unmarshal(inputStream);
			TargetObject targetObject = (TargetObject) queryBuilder
					.getTargetObject().get(0);
			return QueryGenerator.generateHQL(targetObject, searchParameters,
					true);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return "";
	}
	
	public String generateHQL(
			List<AdvancedSearchCriteriaParameter> searchParameters,
			String fileLocation) {
		try {
			return generateHQL(searchParameters, new FileInputStream(new File(
					fileLocation)));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Participant> search(String hql){
		return (List<Participant>)queryBuilderDao.search(hql);
	}

	public void setQueryBuilderDao(QueryBuilderDao queryBuilderDao) {
		this.queryBuilderDao = queryBuilderDao;
	}

	public QueryBuilderDao getQueryBuilderDao() {
		return queryBuilderDao;
	}
	
}
