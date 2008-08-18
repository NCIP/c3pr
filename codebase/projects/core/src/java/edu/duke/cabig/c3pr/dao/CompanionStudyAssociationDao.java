package edu.duke.cabig.c3pr.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

public class CompanionStudyAssociationDao extends GridIdentifiableDao<CompanionStudyAssociation>
                implements MutableDomainObjectDao<CompanionStudyAssociation> {

    private static Log log = LogFactory.getLog(CompanionStudyAssociation.class);

    @Override
    public Class<CompanionStudyAssociation> domainClass() {
        return CompanionStudyAssociation.class;
    }

    @Transactional(readOnly = false)
    public void save(CompanionStudyAssociation companionStudyAssociation) {
        getHibernateTemplate().saveOrUpdate(companionStudyAssociation);
    }
}
