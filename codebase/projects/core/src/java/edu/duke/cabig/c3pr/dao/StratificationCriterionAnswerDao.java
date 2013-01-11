/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
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
