package edu.duke.cabig.c3pr.dao;

import edu.nwu.bioinformatics.commons.CollectionUtils;
import gov.nih.nci.cabig.ctms.domain.DomainObject;
import gov.nih.nci.cabig.ctms.domain.GridIdentifiable;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 14, 2007
 * Time: 3:34:36 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class GridIdentifiableDao<T extends DomainObject & GridIdentifiable>
extends C3PRBaseDao<T>
    implements gov.nih.nci.cabig.ctms.dao.GridIdentifiableDao<T>  {

       @SuppressWarnings("unchecked")
    public T getByGridId(T template) {
        return (T) CollectionUtils.firstElement(getHibernateTemplate().findByExample(template));
    }

    public T getByGridId(String gridId) {
        StringBuilder query = new StringBuilder("from ")
          .append(domainClass().getName()).append(" o where gridId = ?");
        Object[] params = {gridId};
        return (T) CollectionUtils.firstElement(getHibernateTemplate().find(query.toString(), params));
    }


}