package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;

/**
 * Hibernate implementation of StratificationCriterionPermissibleAnswer
 * 
 * @see edu.duke.cabig.c3pr.dao.StratificationCriterionPermissibleAnswer
 * @author Priyatam
 */
public class StratificationCriterionAnswerDao extends
                GridIdentifiableDao<StratificationCriterionPermissibleAnswer> {

    @Override
    public Class<StratificationCriterionPermissibleAnswer> domainClass() {
        return StratificationCriterionPermissibleAnswer.class;
    }

}
