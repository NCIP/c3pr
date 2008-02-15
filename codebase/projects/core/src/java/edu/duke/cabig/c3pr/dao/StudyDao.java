package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.emory.mathcs.backport.java.util.Collections;
import edu.nwu.bioinformatics.commons.CollectionUtils;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

/**
 * Hibernate implementation of StudyDao
 * @author Priyatam
 */
@Transactional(readOnly = true)
public class StudyDao extends GridIdentifiableDao<Study>
        implements MutableDomainObjectDao<Study> {

    private static final List<String> SUBSTRING_MATCH_PROPERTIES
            = Arrays.asList("shortTitleText");
    private static final List<String> EXACT_MATCH_PROPERTIES
            = Collections.emptyList();
    private static Log log = LogFactory.getLog(StudyDao.class);
    private HealthcareSiteDao healthcareSiteDao;

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	@Override
    public Class<Study> domainClass() {
        return Study.class;
    }

    public Study getStudyDesignById(int id) {
        return (Study) getHibernateTemplate().get(domainClass(), id);
    }

    @SuppressWarnings("unchecked")
    public Study getByIdentifier(SystemAssignedIdentifier identifier) {
        Criteria criteria = getSession().createCriteria(domainClass());
        criteria = criteria.createCriteria("identifiers");

        if(identifier.getType() != null) {
            criteria.add(Restrictions.eq("type", identifier.getType()));
        }

        if(identifier.getSystemName() != null) {
            criteria.add(Restrictions.eq("systemName", identifier.getSystemName()));
        }

        if(identifier.getValue() != null) {
            criteria.add(Restrictions.eq("value", identifier.getValue()));
        }
        return (Study) CollectionUtils.firstElement(criteria.list());
    }
    
    @SuppressWarnings("unchecked")
    public List<Study> searchByOrgIdentifier(OrganizationAssignedIdentifier id) {
    	return (List<Study>) getHibernateTemplate().find("select S from Study S, Identifier I where I.healthcareSite.id=?" +
    			" and I.value=? and I.type=? and I=any elements(S.identifiers)",new Object[]{id.getHealthcareSite().getId(),id.getValue(),id.getType()});
    }
    @Transactional (readOnly = false)
    public Study merge(Study study) {
        return (Study)getHibernateTemplate().merge(study);
    }

    @Transactional (readOnly = false)
    public void save(Study study) {
        getHibernateTemplate().saveOrUpdate(study);
    }
    
    @Transactional (readOnly = false)
    public void initialize(Study study) throws DataAccessException {
    	getHibernateTemplate().initialize(study.getEpochs());
    	for(Epoch epoch: study.getEpochs()){
			if (epoch instanceof TreatmentEpoch) {
				TreatmentEpoch treatmentEpoch = (TreatmentEpoch)epoch;
				if (treatmentEpoch != null) {
					getHibernateTemplate().initialize(
							treatmentEpoch.getArmsInternal());
					getHibernateTemplate().initialize(
							treatmentEpoch
									.getExclusionEligibilityCriteriaInternal());
					getHibernateTemplate().initialize(
							treatmentEpoch
									.getInclusionEligibilityCriteriaInternal());
					getHibernateTemplate().initialize(
							treatmentEpoch.getStratificationCriteriaInternal());
					for (StratificationCriterion stratficationCriterion : treatmentEpoch
							.getStratificationCriteriaInternal()) {
						if (stratficationCriterion != null) {
							getHibernateTemplate().initialize(
									stratficationCriterion
											.getPermissibleAnswersInternal());
						}
					}
					getHibernateTemplate().initialize(
							treatmentEpoch.getStratumGroupsInternal());
					for (StratumGroup stratumGroup : treatmentEpoch
							.getStratumGroupsInternal()) {

						if (stratumGroup != null) {
							getHibernateTemplate()
									.initialize(
											stratumGroup
													.getBookRandomizationEntryInternal());
						}
					}
				}
			}    		
     	}
    	getHibernateTemplate().initialize(study.getStudyAmendmentsInternal());
    	getHibernateTemplate().initialize(study.getStudyDiseases());
    	getHibernateTemplate().initialize(study.getStudyOrganizations());
    	getHibernateTemplate().initialize(study.getIdentifiers());
    	getHibernateTemplate().initialize(study.getNotificationsInternal());
    	 for(StudyOrganization studyOrganization: study.getStudyOrganizations()){
			if (studyOrganization!=null) {
				getHibernateTemplate().initialize(
						studyOrganization.getStudyInvestigators());
				getHibernateTemplate().initialize(
						studyOrganization.getStudyPersonnel());
			}	    	
    	}
    }

    public List<Study> getBySubnames(String[] subnames) {
        return findBySubname(subnames,
                SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    }


    /*
      * Searches based on an example object. Typical usage from your service class: -
      * If you want to search based on diseaseCode, monitorCode,
      * <li><code>Study study = new Study();</li></code>
      * <li>code>study.setDiseaseCode("diseaseCode");</li></code>
      * <li>code>study.setDMonitorCode("monitorCode");</li></code>
      * <li>code>studyDao.searchByExample(study)</li></code>
      * @return list of matching study objects based on your sample study object
      */
  
    @Transactional(readOnly = true)
    public List<Study> searchByExample(Study study, boolean isWildCard) {
        List<Study> result = new ArrayList<Study>();

        Example example = Example.create(study).excludeZeroes().ignoreCase();
        try {
            Criteria studyCriteria = getSession().createCriteria(Study.class);
            studyCriteria.addOrder(Order.asc("shortTitleText"));
            studyCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            
            if (isWildCard)
            {
                example.excludeProperty("doNotUse").enableLike(MatchMode.ANYWHERE);
                studyCriteria.add(example);
                if (study.getIdentifiers().size() > 1) {
                    studyCriteria.createCriteria("identifiers")
                            .add(Restrictions.ilike("value", "%" + study.getIdentifiers().get(0)
                                    .getValue() + "%")) .add(Restrictions.ilike("value", "%" + study.getIdentifiers().get(1)
                                            .getValue() + "%"));
                } else  if (study.getIdentifiers().size() > 0) {
                    studyCriteria.createCriteria("identifiers")
                    .add(Restrictions.ilike("value", "%" + study.getIdentifiers().get(0)
                            .getValue() + "%"));
        }
                result =  studyCriteria.list();
            }
            result =  studyCriteria.add(example).list();
        } catch (DataAccessResourceFailureException e) {
            log.error(e.getMessage());
        } catch (IllegalStateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (HibernateException e) {
            log.error(e.getMessage());
        }
        return result;
    }
    
    @Transactional(readOnly = true)
    public List<Study> searchByExample(Study study,String searchText, boolean isWildCard) {
    	
        List<Study> result = new ArrayList<Study>();
        if(isWildCard){
        	result = (List<Study>)getHibernateTemplate().find("from Study where status like '%" +  searchText.toUpperCase() + "%'");
        }
        else {
        	result = (List<Study>)getHibernateTemplate().find("from Study where status like '?", searchText.toUpperCase());
        }
        return result;
    }
    
    @Transactional(readOnly = false)
    public List<OrganizationAssignedIdentifier> getCoordinatingCenterIdentifiersWithValue(String coordinatingCetnerIdentifierValue,  HealthcareSite site) throws DataAccessException {
		List<OrganizationAssignedIdentifier> orgAssignedIdentifiers = (List<OrganizationAssignedIdentifier>) getHibernateTemplate().
                find("from Identifier I where I.type='Coordinating Center Identifier' and I.healthcareSite = ?",site);
		List<OrganizationAssignedIdentifier> ccIdentifiers = new ArrayList<OrganizationAssignedIdentifier>();
		for(OrganizationAssignedIdentifier studyIdent:orgAssignedIdentifiers){
			if (studyIdent.getValue().equalsIgnoreCase(coordinatingCetnerIdentifierValue)){
				ccIdentifiers.add(studyIdent);
			}
		}
		return ccIdentifiers;
	}
	
	@Transactional(readOnly = false)
    public List<OrganizationAssignedIdentifier> getFundingSponsorIdentifiersWithValue(String fundingSponsorIdentifierValue,  HealthcareSite site) throws DataAccessException {
		List<OrganizationAssignedIdentifier> orgAssignedIdentifiers = (List<OrganizationAssignedIdentifier>) getHibernateTemplate().
                find("from Identifier I where I.type='Protocol Authority Identifier' and I.healthcareSite = ?",site);
		List<OrganizationAssignedIdentifier> fsIdentifiers = new ArrayList<OrganizationAssignedIdentifier>();
		for(OrganizationAssignedIdentifier subIdent:orgAssignedIdentifiers){
			if (subIdent.getValue().equalsIgnoreCase(fundingSponsorIdentifierValue)){
				fsIdentifiers.add(subIdent);
			}
		}
		return fsIdentifiers;
	}


    /**
     * Default Search without a Wildchar
     * @param study
     * @return Search Results
     */
    public List<Study> searchByExample(Study study) {
        return searchByExample(study, false);
    }

    /**
     * Returns all study objects
     * @return list of study objects
     */
    public List<Study> getAll(){
        return getHibernateTemplate().find("from Study");
    }

    /**
     * Get all Arms associated with all of this study's epochs
     * @param studyId the study id
     * @return list of Arm objects given a study id
     */
    public List<Arm> getArmsForStudy(Integer studyId) {
        return getHibernateTemplate().find("select a from Study s join s.epochs e join e.arms a " +
                "where s.id = ?", studyId);
    }

    /**
     * Get all Assignments associated with the given study
     * @param studyId the study id
     * @return list of StudySubjects
     */
    public List<StudySubject> getStudySubjectsForStudy(Integer studyId) {
        return getHibernateTemplate().find("select a from Study s join s.studyOrganizations so " +
                "join so.studySubjects a where s.id = ? ", studyId);
    }
    
    /*
     * Primarily created for Generating test reports.
     */
    public List<StudyDisease> getByDiseaseTermId(Integer dTermId){
    	return getHibernateTemplate().find("from StudyDisease sd where sd.diseaseTerm.id = ?", dTermId);
    }
    

    @Transactional (readOnly = false)
    public void reassociate(Study s) {
        getHibernateTemplate().update(s);
    }
    
    @Transactional (readOnly = false)
    public void refresh(Study s) {
        getHibernateTemplate().refresh(s);
    }
}
