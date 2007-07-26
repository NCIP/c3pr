package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.emory.mathcs.backport.java.util.Collections;
import edu.nwu.bioinformatics.commons.CollectionUtils;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.*;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

    public Study getStudyDesignById(int id) {
        return (Study) getHibernateTemplate().get(domainClass(), id);
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

    @Transactional (readOnly = false)
    public Study merge(Study study) {
        return (Study)getHibernateTemplate().merge(study);
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
            studyCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            
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
     * @return list of StudySubjects
     */
    public List<StudySubject> getStudySubjectsForStudy(Integer studyId) {
        return getHibernateTemplate().find("select a from Study s join s.studyOrganizations so " +
                "join so.studySubjects a where s.id = ? ", studyId);
    }

    @Transactional (readOnly = false)
    public void reassociate(Study s) {
        getHibernateTemplate().update(s);
    }
}
