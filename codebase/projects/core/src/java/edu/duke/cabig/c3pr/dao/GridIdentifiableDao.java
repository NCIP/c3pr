/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import edu.nwu.bioinformatics.commons.CollectionUtils;
import gov.nih.nci.cabig.ctms.domain.DomainObject;
import gov.nih.nci.cabig.ctms.domain.GridIdentifiable;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 14, 2007 Time: 3:34:36 PM To change this template
 * use File | Settings | File Templates.
 */
public abstract class GridIdentifiableDao<T extends DomainObject & GridIdentifiable> extends
                C3PRBaseDao<T> implements gov.nih.nci.cabig.ctms.dao.GridIdentifiableDao<T> {

    /* (non-Javadoc)
     * @see gov.nih.nci.cabig.ctms.dao.GridIdentifiableDao#getByGridId(java.lang.String)
     */
    public T getByGridId(String gridId) {
        StringBuilder query = new StringBuilder("from ").append(domainClass().getName()).append(
                        " o where gridId = ?");
        Object[] params = { gridId };
        return (T) CollectionUtils.firstElement(getHibernateTemplate().find(query.toString(),
                        params));
    }

}
