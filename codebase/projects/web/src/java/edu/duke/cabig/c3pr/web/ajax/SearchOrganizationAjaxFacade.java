/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.ajax;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.bean.Table;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelImpl;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;

/**
 * The Class SearchOrganizationAjaxFacade.
 */
public class SearchOrganizationAjaxFacade {
    
    /** The log. */
    private static Log log = LogFactory.getLog(SearchOrganizationAjaxFacade.class);

    /** The healthcare site dao. */
    private HealthcareSiteDao healthcareSiteDao;

    /**
     * Builds the Organization Search Results table.
     * 
     * @param model the model
     * @param healthcareSites the healthcare sites
     * 
     * @return the object
     * 
     * @throws Exception the exception
     */
    public Object build(TableModel model, Collection<HealthcareSite> healthcareSites) throws Exception {

        Table table = model.getTableInstance();
        table.setAutoIncludeParameters(false);
        table.setTableId("assembler");
        table.setItems(healthcareSites);
        table.setAction(model.getContext().getContextPath() + "/pages/admin/createOrganization");
        table.setTitle("Organizations");
        table.setShowPagination(false);
//        table.setRowsDisplayed(15);
        table.setOnInvokeAction("buildTable('assembler')");
        table.setImagePath(model.getContext().getContextPath() + "/images/table/*.gif");
        table.setShowExports(false);
        table.setSortable(false);
        model.addTable(table);

        Row row = model.getRowInstance();
        row.setHighlightRow(Boolean.TRUE);
        model.addRow(row);
		
		Column columnName = model.getColumnInstance();
		columnName.setTitle("Name");
		columnName.setProperty("name");
		columnName.setCell((OrganizationLinkDisplayCell.class).getName());
		model.addColumn(columnName);

        Column columnCtep = model.getColumnInstance();
        columnCtep.setTitle("Primary identifier (CTEP)");
        columnCtep.setProperty("primaryIdentifier");
        model.addColumn(columnCtep);

        Column columnNci = model.getColumnInstance();
        columnNci.setTitle("NCI identifier");
        columnNci.setProperty("externalId");
        columnNci.setCell((NciIdLinkDisplayCell.class).getName());
        model.addColumn(columnNci);
        
        return model.assemble();
    }

    /**
     * Gets the table.
     * 
     * @param parameterMap the parameter map
     * @param params the params
     * @param request the request
     * 
     * @return the table
     */
    public String getTable(Map<String, List<String>> parameterMap, String[] params,
                    HttpServletRequest request) {

        HealthcareSite hcs = new LocalHealthcareSite();
        if (!StringUtils.isEmpty(params[0])) {
            hcs.setName(params[0]);
        }
        if (!StringUtils.isEmpty(params[1])) {
            hcs.setCtepCode(params[1]);
        }
        List<HealthcareSite> orgResults = healthcareSiteDao.searchByExample(hcs, true, -1);

        Context context = null;
        if (parameterMap == null) {
            context = new HttpServletRequestContext(request);
        }
        else {
            context = new HttpServletRequestContext(request, parameterMap);
        }

        TableModel model = new TableModelImpl(context);
        try {
            return build(model, orgResults).toString();
        }
        catch (Exception e) {
            log.error("Exception caught in SearchOrganizationFacade", e);
        }

        return "";
    }


	/**
	 * Sets the healthcare site dao.
	 * 
	 * @param healthcareSiteDao the new healthcare site dao
	 */
	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

}
