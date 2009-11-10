package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.semanticbits.coppa.infrastructure.RemoteSession;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.ContactMechanismBasedRecipient;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import edu.duke.cabig.c3pr.domain.RemoteStudy;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.emory.mathcs.backport.java.util.Collections;
import edu.nwu.bioinformatics.commons.CollectionUtils;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

/**
 * Hibernate implementation of StudyDao.
 * 
 * @author Priyatam
 */

public class StudyDao extends GridIdentifiableDao<Study> implements MutableDomainObjectDao<Study> {

    /** The Constant SUBSTRING_MATCH_PROPERTIES. */
    private static final List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("shortTitleText");

    /** The Constant EXACT_MATCH_PROPERTIES. */
    private static final List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

	 /** The Constant extraParameters. */
    private static final List EXTRA_PARAMETERS = Arrays.asList("%center%");
    
    /** The log. */
    private static Log log = LogFactory.getLog(StudyDao.class);
    
	/** The remote session. */
	private RemoteSession remoteSession;
    
    private StudyVersionDao studyVersionDao ;
    
    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;

    public HealthcareSiteDao getHealthcareSiteDao() {
        return healthcareSiteDao;
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }

    private HealthcareSiteDao healthcareSiteDao ;
    
    private InvestigatorDao investigatorDao;

    public void setStudyVersionDao(StudyVersionDao studyVersionDao) {
		this.studyVersionDao = studyVersionDao;
	}

	/**
     * Clears the Hibernate Session.
     */
    public void clear() {
        getHibernateTemplate().clear();
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    @Override
    public Class<Study> domainClass() {
        return Study.class;
    }

    /**
     * Search by system identifier.
     * 
     * @param id the id
     * 
     * @return the list< study>
     */
    @SuppressWarnings("unchecked")
    public List<Study> searchBySysIdentifier(SystemAssignedIdentifier id) {
        return (List<Study>) getHibernateTemplate()
                        .find("select S from Study S, Identifier I where I.systemName=?"
                            + " and I.value=? and I.typeInternal=? and I=any elements(S.identifiers)",
                            new Object[] { id.getSystemName(), id.getValue(), id.getType()});
    }

    /**
     * Search by organization identifier.
     * 
     * @param id the id
     * 
     * @return the list< study>
     */
    @SuppressWarnings("unchecked")
    public List<Study> searchByOrgIdentifier(OrganizationAssignedIdentifier id) {
        return (List<Study>) getHibernateTemplate()
                .find("select S from Study S, Identifier I where I.healthcareSite.id in " + 
            		"(select h.id from HealthcareSite h, Identifier I where " +
            		"h.identifiersAssignedToOrganization.value=? and h.identifiersAssignedToOrganization.primaryIndicator = 'TRUE')"  +
                    " and I.value=? and I.typeInternal=? and I=any elements(S.identifiers)",
                    new Object[] {id.getHealthcareSite().getCtepCode(), id.getValue(), id.getType().getName()});
    }

    /**
     * Merges the study with the persistence store
     * 
     * @param study the study
     * 
     * @return the study
     */
    @Transactional(readOnly = false)
    public Study merge(Study study) {
    	return (Study) getHibernateTemplate().merge(study);
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao#save(gov.nih.nci.cabig.ctms.domain.MutableDomainObject)
     */
    @Transactional(readOnly = false)
    public void save(Study study) {
        getHibernateTemplate().saveOrUpdate(study);
    }
    

    /**
     * Initializes the study.
     * 
     * @param study the study
     * 
     */
    @Transactional(readOnly = false)
    public void initialize(Study study) 	{
        getHibernateTemplate().initialize(study.getStudyVersionsInternal());
    	for(StudyVersion studyVersion : study.getStudyVersions()){
			studyVersionDao.initialize(studyVersion);
		}
    	
    	getHibernateTemplate().initialize(study.getEndpoints());

		getHibernateTemplate().initialize(study.getIdentifiers());
		for(OrganizationAssignedIdentifier organizationAssignedIdentifier: study.getOrganizationAssignedIdentifiers()){
			getHealthcareSiteDao().initialize(organizationAssignedIdentifier.getHealthcareSite());
		}

		getHibernateTemplate().initialize(study.getParentStudyAssociations());
		for(CompanionStudyAssociation parentStudyAssociation : study.getParentStudyAssociations()){
			getHibernateTemplate().initialize(parentStudyAssociation.getStudySites());
			getHibernateTemplate().initialize(parentStudyAssociation.getParentStudy().getStudyOrganizations());
		}

		getHibernateTemplate().initialize(study.getPlannedNotificationsInternal());
		for (PlannedNotification plannedNotification : study.getPlannedNotificationsInternal()) {
			if (plannedNotification != null) {
				getHibernateTemplate().initialize(plannedNotification.getUserBasedRecipientInternal());
				getHibernateTemplate().initialize(plannedNotification.getRoleBasedRecipientInternal());
				getHibernateTemplate().initialize(plannedNotification.getContactMechanismBasedRecipientInternal());
			}
			for(ContactMechanismBasedRecipient cmbr: plannedNotification.getContactMechanismBasedRecipient()){
				getHibernateTemplate().initialize(cmbr.getContactMechanismsInternal());
			}
		}
		
        getHibernateTemplate().initialize(study.getStudyOrganizations());
		for (StudyOrganization studyOrganization : study.getStudyOrganizations()) {
            getHibernateTemplate().initialize(studyOrganization.getStudyInvestigatorsInternal());
            getHibernateTemplate().initialize(studyOrganization.getStudyPersonnelInternal());
            getHibernateTemplate().initialize(studyOrganization.getEndpoints());
            healthcareSiteDao.initialize(studyOrganization.getHealthcareSite());
            if(studyOrganization instanceof StudySite){
    			getHibernateTemplate().initialize(((StudySite)studyOrganization).getStudySiteStudyVersions());
    			getHibernateTemplate().initialize(((StudySite)studyOrganization).getSiteStatusHistoryInternal());
    		}
        }
	}
    
    
    /**
     * Initializes the study.
     * 
     * @param study the study
     * 
     */
    @Transactional(readOnly = false)
    public void initializeWithCompanion(Study study) 	{
        initialize(study);
        getHibernateTemplate().initialize(study.getCompanionStudyAssociations());
		for (CompanionStudyAssociation companionStudyAssociation : study.getCompanionStudyAssociations()) {
			this.initialize(companionStudyAssociation.getCompanionStudy());
		}
		for(CompanionStudyAssociation companionStudyAssociation : study.getCompanionStudyAssociations()){
			getHibernateTemplate().initialize(companionStudyAssociation.getStudySites());
		}
		
		for (PlannedNotification plannedNotification : study.getPlannedNotificationsInternal()) {
			if (plannedNotification != null) {
				getHibernateTemplate().initialize(plannedNotification.getUserBasedRecipientInternal());
				getHibernateTemplate().initialize(plannedNotification.getRoleBasedRecipientInternal());
				getHibernateTemplate().initialize(plannedNotification.getContactMechanismBasedRecipientInternal());
			}
			for(ContactMechanismBasedRecipient cmbr: plannedNotification.getContactMechanismBasedRecipient()){
				getHibernateTemplate().initialize(cmbr.getContactMechanismsInternal());
			}
		}
		
        getHibernateTemplate().initialize(study.getStudyOrganizations());
		for (StudyOrganization studyOrganization : study.getStudyOrganizations()) {
            getHibernateTemplate().initialize(studyOrganization.getStudyInvestigatorsInternal());
            getHibernateTemplate().initialize(studyOrganization.getStudyPersonnelInternal());
            getHibernateTemplate().initialize(studyOrganization.getEndpoints());
            healthcareSiteDao.initialize(studyOrganization.getHealthcareSite());
            if(studyOrganization instanceof StudySite){
    			getHibernateTemplate().initialize(((StudySite)studyOrganization).getStudySiteStudyVersions());
    		}
        }
		
	}
    /**
     * Gets the study by subnames.
     * 
     * @param subnames the subnames
     * 
     * @return List of studies
     */
    public List<Study> getBySubnames(String[] subnames) {
        return findBySubname(subnames, SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES, "StudyVersion", "studyVersionsInternal");
    }

    
    /**
     * Gets the study by comapring subnames against short title and coordinating center identifiers.
     * 
     * @param subnames the enetered string. used by comapnion study autocompleter
     * 
     * @return List of studies
     */
    public List<Study> getStudiesBySubnamesWithExtraConditionsForPrimaryIdentifier(String[] subnames) {
    	
    	List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("identifiers.value");
    	
    	List<Study> studies = findBySubname(subnames, "LOWER(o.identifiers.typeInternal) LIKE ? ", EXTRA_PARAMETERS, SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    	for(Study study: studies){
    		getHibernateTemplate().initialize(study.getIdentifiers());
    	}
    	//remove duplicates if any
    	Set setItems = new LinkedHashSet(studies);
    	studies.clear();
    	studies.addAll(setItems); 
    	
    	return studies;
    }
    
    
    /*
     * Searches based on an example object. Typical usage from your service class: - If you want to
     * search based on diseaseCode, monitorCode, <li><code>Study study = new Study();</li></code>
     * <li>code>study.setDiseaseCode("diseaseCode");</li></code> <li>code>study.setDMonitorCode("monitorCode");</li></code>
     * <li>code>studyDao.searchByExample(study)</li></code> @return list of matching study
     * objects based on your sample study object
     */

    /**                                       
     * Searches by example.
     * 
     * @param study the example study
     * @param isWildCard the is wild card
     * 
     * @return the list< study>
     */
    public List<Study> searchByExample(Study study, boolean isWildCard) {
			
    	//First get the matching studies from COPPA and update the db with it
		List<Study> remoteStudies = new ArrayList<Study>();
		remoteStudies.addAll(getExternalStudiesByExampleFromResolver(study));
		updateDatabaseWithRemoteStudies(remoteStudies);
			
		//now run the search
        return searchByExample(study, isWildCard, 0);
    }

	private List<Study> getExternalStudiesByExampleFromResolver(Study exampleStudy) {
		if(exampleStudy.getIdentifiers().size() > 0){
			List<Study> localStudyList = getHibernateTemplate().find("from Study s where s.identifiers.value = ?", exampleStudy.getIdentifiers().get(0).getValue());
			if(localStudyList.size() > 0){
				return localStudyList;
			}
		} 
		Study remoteStudy = new RemoteStudy();
    	//set the short-title/identifier/status in the example object as we support searches based on these 3 only.
		remoteStudy.setShortTitleText(StringUtils.getBlankIfNull(exampleStudy.getShortTitleText()));
		//NOTE: we dont support searches on long title in our UI but coppa does.
		remoteStudy.setLongTitleText(StringUtils.getBlankIfNull(exampleStudy.getLongTitleText()));
		remoteStudy.setCoordinatingCenterStudyStatus(exampleStudy.getCoordinatingCenterStudyStatus());
		//Note that our study search looks for all identifiers but coppa only searches by the Coordinating center identifier.
		if(exampleStudy.getIdentifiers().size() > 0){
			OrganizationAssignedIdentifier organizationAssignedIdentifier = new OrganizationAssignedIdentifier();
			organizationAssignedIdentifier.setValue(exampleStudy.getIdentifiers().get(0).getValue());
			organizationAssignedIdentifier.setType(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER);
			remoteStudy.getIdentifiers().add(organizationAssignedIdentifier);
		}
		List<Object> objectList = remoteSession.find(remoteStudy);
		List<Study> remoteStudyList = new ArrayList<Study>();
		for (Object object : objectList) {
			remoteStudyList.add((Study) object);
		}
		return remoteStudyList;
	}
	
	
    private void updateDatabaseWithRemoteStudies(List<Study> remoteStudies) {
		try {
			for (Study remoteStudy : remoteStudies) {
				if(remoteStudy != null){
					RemoteStudy remoteStudyTemp = (RemoteStudy)remoteStudy;
					for(OrganizationAssignedIdentifier organizationAssignedIdentifier: remoteStudyTemp.getOrganizationAssignedIdentifiers()){
						if(organizationAssignedIdentifier.getType().equals(OrganizationIdentifierTypeEnum.NCI)){
							organizationAssignedIdentifier.setHealthcareSite(healthcareSiteDao.getNCIOrganization());
						}
						if(organizationAssignedIdentifier.getType().equals(OrganizationIdentifierTypeEnum.CTEP)){
							organizationAssignedIdentifier.setHealthcareSite(healthcareSiteDao.getCTEPOrganization());
						}
					}
					
					Study studyFromDatabase = getByExternalIdentifier(remoteStudyTemp.getExternalId());
					//If studyFromDatabase is null then save else it already exists as a remoteStudy.
					if (studyFromDatabase == null) {
						//save the associated HealthcareSites first.
						List<HealthcareSite> healthcareSiteList = new ArrayList<HealthcareSite>();
						for(StudyOrganization studyOrganization: remoteStudyTemp.getStudyOrganizations()){
							if(studyOrganization.getHealthcareSite() instanceof RemoteHealthcareSite){
								healthcareSiteList.add(studyOrganization.getHealthcareSite());
							}
						}
						healthcareSiteDao.updateDatabaseWithRemoteHealthcareSites(healthcareSiteList);
						HealthcareSite healthcareSite = null;
						//Update the studyInv and the hcsi to point to the saved hcs.
						for(int i=0; i < remoteStudyTemp.getStudyOrganizations().size(); i++){
							healthcareSite = remoteStudyTemp.getStudyOrganizations().get(i).getHealthcareSite();
							if(healthcareSite instanceof RemoteHealthcareSite){
								remoteStudyTemp.getStudyOrganizations().get(i).setHealthcareSite(healthcareSiteList.get(i));
								for(StudyInvestigator studyInvestigator : remoteStudyTemp.getStudyOrganizations().get(i).getStudyInvestigators()){
									studyInvestigator.getHealthcareSiteInvestigator().setHealthcareSite(healthcareSiteList.get(i));
								}
							}
						}
							
						//save the associated Investigators next.
						Investigator investigator = null;
						Investigator savedInvestigator = null;
						for(StudyOrganization studyOrganization : remoteStudyTemp.getStudyOrganizations()){
							for(StudyInvestigator studyInvestigator : studyOrganization.getStudyInvestigators()){
								investigator = studyInvestigator.getHealthcareSiteInvestigator().getInvestigator();
								if(investigator instanceof RemoteInvestigator){
									//this does not save the associated hcsi. It returns the saved Inv with the unsaved hcsi attached to it
									savedInvestigator = investigatorDao.updateDatabaseWithRemoteContent((RemoteInvestigator)investigator);
									//Set the saved Investigator in the hcsi. So now the hcsi has the saved hcs and inv in it.
									studyInvestigator.getHealthcareSiteInvestigator().setInvestigator(savedInvestigator);
									HealthcareSiteInvestigator savedHealthcareSiteInvestigator = null;
									if(studyInvestigator.getHealthcareSiteInvestigator().getId() == null){
										savedHealthcareSiteInvestigator  = healthcareSiteInvestigatorDao.updateDatabaseWithRemoteContent(studyInvestigator.getHealthcareSiteInvestigator());
										if(savedHealthcareSiteInvestigator  != null){
											studyInvestigator.setHealthcareSiteInvestigator(savedHealthcareSiteInvestigator);
										}
									}
								}
							}
						}
						
						//TODO: Check to see if it exists as localStudy by using the searchByOrganizationAssignedIdentifier()
						save(remoteStudyTemp);
					}
					getHibernateTemplate().flush();
				} else {
					log.error("Null Remote Study in the list in updateDatabaseWithRemote Content");
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (C3PRBaseRuntimeException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
	}


	/**Gets by the unique Identifier
	 * @param externalId
	 * @return
	 */
	public Study getByExternalIdentifier(String externalId) {
		if(StringUtils.isEmpty(externalId)){
			return null;
		}
		return CollectionUtils.firstElement((List<Study>) getHibernateTemplate()
					.find("from RemoteStudy s where s.externalId = ?", externalId));
	}
	
	/**
     * Search by example.
     *                                                                                                                           
     * @param study the exmple study
     * @param isWildCard the wild card
     * @param maxResults the max results
     * @param order the order
     * @param orderBy the order by
     * 
     * @return the list< study>
     */
    public List<Study> searchByExample(Study study, boolean isWildCard, int maxResults,
                    String order, String orderBy) {
        List<Study> result = new ArrayList<Study>();

        Example example = Example.create(study).excludeZeroes().ignoreCase();
        Example subExample = Example.create(study.getStudyVersion()).excludeZeroes().ignoreCase();
        Criteria studyCriteria = getSession().createCriteria(Study.class);

        if ("ascending".equals(order)) {
            studyCriteria.addOrder(Order.asc(orderBy));
        }
        else if ("descending".equals(order)) {
            studyCriteria.addOrder(Order.desc(orderBy));
        }

        studyCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        if (maxResults > 0) studyCriteria.setMaxResults(maxResults);

        if (isWildCard) {
            example.excludeProperty("doNotUse").enableLike(MatchMode.ANYWHERE);
            subExample.excludeProperty("doNotUse").enableLike(MatchMode.ANYWHERE);
            //studyCriteria.add(example);
            //studyCriteria.createCriteria("studyVersionsInternal").add(subExample);
            if (study.getIdentifiers().size() > 1) {
                studyCriteria.createCriteria("identifiers").add(
                                Restrictions.ilike("value", "%"
                                                + study.getIdentifiers().get(0).getValue()
                                                + "%")).add(
                                Restrictions.ilike("value", "%"
                                                + study.getIdentifiers().get(1).getValue()
                                                + "%"));
            }
            else if (study.getIdentifiers().size() > 0) {
                studyCriteria.createCriteria("identifiers").add(
                                Restrictions.ilike("value", "%"
                                                + study.getIdentifiers().get(0).getValue()
                                                + "%"));
            }
            //result = studyCriteria.list();
        }
        studyCriteria.createCriteria("studyVersionsInternal").add(subExample);
        result = studyCriteria.add(example).list();
        return result;

    }

    /**
     * Search by example.
     * 
     * @param study the study
     * @param isWildCard the wild card
     * @param maxResults the max results
     * 
     * @return the list< study>
     */
    public List<Study> searchByExample(Study study, boolean isWildCard, int maxResults) {
        return searchByExample(study, isWildCard, maxResults, "ascending", "id");
    }

    /**
     * Search by example.
     * 
     * @param study the study
     * @param searchText the search text
     * @param isWildCard the wild card
     * 
     * @return the list< study>
     */
    public List<Study> searchByStatus(Study study, String searchText, boolean isWildCard) {

        List<Study> result = new ArrayList<Study>();
        	searchText = searchText.replace(" ", "_");
        if (isWildCard) {
            result = (List<Study>) getHibernateTemplate().find(
                            "from Study where status like '%" + searchText.toUpperCase() + "%'");
        }
        else {
            result = (List<Study>) getHibernateTemplate().find("from Study where status like '?",
                            searchText.toUpperCase());
        }
        return result;
    }

    /**
     * Gets the coordinating center identifiers with the given value.
     * 
     * @param coordinatingCetnerIdentifierValue the coordinating center identifier value
     * @param site the site
     * 
     * @return the coordinating center identifiers
     * 
     */
    public List<OrganizationAssignedIdentifier> getCoordinatingCenterIdentifiersWithValue(
                    String coordinatingCetnerIdentifierValue, HealthcareSite site) {
        List<OrganizationAssignedIdentifier> orgAssignedIdentifiers = (List<OrganizationAssignedIdentifier>) getHibernateTemplate()
                  .find("from Identifier I where I.typeInternal='COORDINATING_CENTER_IDENTIFIER' and I.healthcareSite = ?",site);
        List<OrganizationAssignedIdentifier> ccIdentifiers = new ArrayList<OrganizationAssignedIdentifier>();
        for (OrganizationAssignedIdentifier studyIdent : orgAssignedIdentifiers) {
            if (studyIdent.getValue().equalsIgnoreCase(coordinatingCetnerIdentifierValue)) {
                ccIdentifiers.add(studyIdent);
            }
        }
        return ccIdentifiers;
    }

    /**
     * Gets the funding sponsor identifiers with given value.
     * 
     * @param fundingSponsorIdentifierValue the funding sponsor identifier value
     * @param site the site
     * 
     * @return the funding sponsor identifiers
     * 
     */
    public List<OrganizationAssignedIdentifier> getFundingSponsorIdentifiersWithValue(
                    String fundingSponsorIdentifierValue, HealthcareSite site) {
        List<OrganizationAssignedIdentifier> orgAssignedIdentifiers = (List<OrganizationAssignedIdentifier>) getHibernateTemplate()
                  .find("from Identifier I where I.typeInternal='PROTOCOL_AUTHORITY_IDENTIFIER' and I.healthcareSite = ?", site);
        List<OrganizationAssignedIdentifier> fsIdentifiers = new ArrayList<OrganizationAssignedIdentifier>();
        for (OrganizationAssignedIdentifier subIdent : orgAssignedIdentifiers) {
            if (subIdent.getValue().equalsIgnoreCase(fundingSponsorIdentifierValue)) {
                fsIdentifiers.add(subIdent);
            }
        }
        return fsIdentifiers;
    }

    /**
     * Default Search without a Wildchar.
     * 
     * @param study the study
     * 
     * @return Search Results
     */
    public List<Study> searchByExample(Study study) {
        return searchByExample(study, false, 0);
    }

    /**
     * Counts acrruals by date.
     * 
     * @param study the study
     * @param startDate the start date
     * @param endDate the end date
     * 
     * @return the accural
     */
    public int countAcrrualsByDate(Study study, Date startDate, Date endDate) {
//        Criteria regCriteria = getHibernateTemplate().getSessionFactory().getCurrentSession()
//                        .createCriteria(StudySubject.class);
//        Criteria studySiteCriteria = regCriteria.createCriteria("studySite");
//        Criteria studyCriteria = studySiteCriteria.createCriteria("study");
//
//        regCriteria.add(Expression.between("startDate", startDate, endDate));
//        studyCriteria.add(Restrictions.eq("id", study.getId()));
//
//        return regCriteria.list().size();
        return getHibernateTemplate().find(
                "select ssub from Study study join study.studyOrganizations so join so.studySiteStudyVersions ssisv " +
                "join ssisv.studySubjectStudyVersions ssbsv join ssbsv.studySubject ssub " +
                "where so.class=StudySite and ssub.startDate between ? and ? and study.id = ? ", new Object[]{startDate, endDate, study.getId()}).size();
    }

    public List<Study> getStudiesByStatus(int maxResultSize, CoordinatingCenterStudyStatus coordinatingCenterStudyStatus){
    	int storedMaxResultValue =getHibernateTemplate().getMaxResults();
    	getHibernateTemplate().setMaxResults(maxResultSize);
    	List<Study> studies =  getHibernateTemplate().find(
                "select s from Study s where s.coordinatingCenterStudyStatusInternal=? order by s.id desc", new Object[]{coordinatingCenterStudyStatus});
    	getHibernateTemplate().setMaxResults(storedMaxResultValue);
    	return studies;
    }
    
    /**
     * Returns all study objects.
     * 
     * @return list of study objects
     */
    public List<Study> getAll() {
        return getHibernateTemplate().find("from Study");
    }

    /**
     * Get all Assignments associated with the given study.
     * 
     * @param studyId the study id
     * 
     * @return list of StudySubjects
     */
    public List<StudySubject> getStudySubjectsForStudy(Integer studyId) {
        return getHibernateTemplate().find(
                        "select ssub from Study study join study.studyOrganizations so join so.studySiteStudyVersions ssisv " +
                        "join ssisv.studySubjectStudyVersions ssbsv join ssbsv.studySubject ssub where so.class=StudySite and study.id = ? ", studyId);
    }

    /*
     * Primarily created for Generating test reports.
     */
    /**
     * Gets the study diseases by disease term id.
     * 
     * @param dTermId the disease term id
     * 
     * @return list study diseases
     */
    public List<StudyDisease> getByDiseaseTermId(Integer dTermId) {
        return getHibernateTemplate().find("from StudyDisease sd where sd.diseaseTerm.id = ?",
                        dTermId);
    }

    /**
     * Reassociate.
     * 
     * @param study the study
     */
    @Transactional(readOnly = false)
    public void reassociate(Study study) {
        getHibernateTemplate().update(study);
    }

    /**
     * Refresh.
     * 
     * @param study the study
     */
    @Transactional(readOnly = false)
    public void refresh(Study study) {
        getHibernateTemplate().refresh(study);
    }

    /**
     * Gets the studies by identifiers.
     * 
     * @param studyIdentifiers the study identifiers
     * 
     * @return List of studies
     */
    public List<Study> getByIdentifiers(List<Identifier> studyIdentifiers) {
        List<Study> studies = new ArrayList<Study>();
        for (Identifier identifier : studyIdentifiers) {
            if (identifier instanceof SystemAssignedIdentifier) studies
                            .addAll(searchBySysIdentifier((SystemAssignedIdentifier) identifier));
            else if (identifier instanceof OrganizationAssignedIdentifier){ 
            	getHibernateTemplate().initialize(((OrganizationAssignedIdentifier)identifier).getHealthcareSite());	
            	studies.addAll(searchByOrgIdentifier((OrganizationAssignedIdentifier) identifier));
            }
        }
        Set<Study> set = new LinkedHashSet<Study>(studies);
        studies.clear();
        studies.addAll(set);
        return studies;
    }
    
    /**
     * Search by identifier.
     * 
     * @param id the identifier
     * 
     * @return the list< study>
     */
    @SuppressWarnings("unchecked")
    public List<Study> searchByIdentifier(int id) {
        return (List<Study>) getHibernateTemplate().find(
                                        "select S from Study S, Identifier I where I.id=? and I=any elements(S.identifiers)",
                                        new Object[] {id});
    }

    @Transactional(readOnly = false)
    public void flush() {
		getHibernateTemplate().flush();
	}
    
    
	/**
	 * Sets the remote session.
	 * @param remoteSession the new remote session
	 */
	public void setRemoteSession(RemoteSession remoteSession) {
		this.remoteSession = remoteSession;
	}

	public InvestigatorDao getInvestigatorDao() {
		return investigatorDao;
	}

	public void setInvestigatorDao(InvestigatorDao investigatorDao) {
		this.investigatorDao = investigatorDao;
	}

	public void setHealthcareSiteInvestigatorDao(
			HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
		this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
	}

}
