package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.StudySite;
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

    @Override
    public Class<Study> domainClass() {
        return Study.class;
    }

    /**
     * This is a hack to load all collection objects in memory. Useful
     * for editing a Study when you know you will be needing all collections
     * To avoid Lazy loading Exception by Hibernate, a call to .size() is done
     * for each collection
     * @param id
     * @return
     */
    public Study getStudyDesignById(int id) {
        Study study =  (Study) getHibernateTemplate().get(domainClass(), id);
        study.getIdentifiers().size();
        study.getStudySites().size();
        for (StudySite studySite : study.getStudySites()) {
            studySite.getStudyInvestigators().size();
            studySite.getStudyPersonnels().size();
        }
        study.getExcCriterias().size();
        study.getIncCriterias().size();
        study.getStudyDiseases().size();
        study.getStratificationCriteria().size();
        for (StratificationCriterion criterion : study.getStratificationCriteria()) {
            criterion.getPermissibleAnswers().size();
        }

        List<TreatmentEpoch> epochs = study.getTreatmentEpochs();
        epochs.size();
        for (TreatmentEpoch epoch : epochs) {
            epoch.getArms().size();
        }
        return study;
    }

    @SuppressWarnings("unchecked")
    public Study getByIdentifier(Identifier identifier) {
        Criteria criteria = getSession().createCriteria(domainClass());
        criteria = criteria.createCriteria("identifiers");

        if(identifier.getType() != null) {
            criteria.add(Restrictions.eq("type", identifier.getType()));
        }

        if(identifier.getSource() != null) {
            criteria.add(Restrictions.eq("source", identifier.getSource()));
        }

        if(identifier.getValue() != null) {
            criteria.add(Restrictions.eq("value", identifier.getValue()));
        }
        return (Study) CollectionUtils.firstElement(criteria.list());
    }

    public void merge(Study study) {
        getHibernateTemplate().merge(study);
    }

    @Transactional (readOnly = false)
    public void save(Study study) {
        getHibernateTemplate().saveOrUpdate(study);
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
            
            if (isWildCard)
            {
                example.excludeProperty("doNotUse").enableLike(MatchMode.ANYWHERE);
                studyCriteria.add(example);
                if (study.getIdentifiers().size() > 0) {
                    studyCriteria.createCriteria("identifiersInternal")
                            .add(Restrictions.like("value", study.getIdentifiersInternal().get(0)
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
     * @return list of StudyParticipantAssignments
     */
    public List<StudyParticipantAssignment> getStudyParticipantAssignmentsForStudy(Integer studyId) {
        return getHibernateTemplate().find("select a from Study s join s.studySitesInternal ss " +
                "join ss.studyParticipantAssignments a where s.id = ?", studyId);
    }

    @Transactional (readOnly = false)
    public void reassociate(Study s) {
        getHibernateTemplate().update(s);
    }
}
