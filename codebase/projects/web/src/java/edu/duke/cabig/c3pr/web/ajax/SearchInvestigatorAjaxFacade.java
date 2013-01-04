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
import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.LocalInvestigator;
import edu.duke.cabig.c3pr.utils.StringUtils;

public class SearchInvestigatorAjaxFacade {
    private static Log log = LogFactory.getLog(SearchInvestigatorAjaxFacade.class);
    private HealthcareSiteDao healthcareSiteDao;

    public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	private InvestigatorDao investigatorDao;

    public Object build(TableModel model, Collection invResults) throws Exception {

        Table table = model.getTableInstance();
        table.setAutoIncludeParameters(false);
        table.setTableId("assembler");
        table.setItems(invResults);
        table.setAction(model.getContext().getContextPath() + "/pages/admin/createInvestigator");
        table.setTitle("Investigators");
        table.setShowPagination(false);
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
        columnName.setProperty("fullName");
        columnName.setCell((InvestigatorLinkDisplayCell.class).getName());
        model.addColumn(columnName);
        
        Column organizations = model.getColumnInstance();
        organizations.setTitle("Organization(s)");
        organizations.setProperty("organizationNames");
//        organizations.setCell((HealthcareSiteInvestigatorLinkDisplayCell.class).getName());
        model.addColumn(organizations);

        Column columnCtep = model.getColumnInstance();
        columnCtep.setTitle("Assigned identifier");
        columnCtep.setProperty("assignedIdentifier");
        model.addColumn(columnCtep);
        
        Column columnNci = model.getColumnInstance();
        columnNci.setTitle("NCI identifier");
        columnNci.setProperty("externalId");
        columnNci.setCell((NciIdLinkDisplayCell.class).getName());
        model.addColumn(columnNci);

        return model.assemble();
    }

    public String getTable(Map<String, List> parameterMap, String[] params,
                    HttpServletRequest request) {

        Investigator inv = new LocalInvestigator();
        if (!StringUtils.isBlank(params[0])) {
            inv.setFirstName(params[0]);
        }
        if (!StringUtils.isBlank(params[1])) {
            inv.setLastName(params[1]);
        }
        if (!StringUtils.isBlank(params[2])) {
            inv.setAssignedIdentifier(params[2]);
        }
        if (!StringUtils.isBlank(params[3])) {
            HealthcareSite healthcareSite = healthcareSiteDao.getById(Integer.parseInt(params[3]));
//            HealthcareSiteInvestigator healthcareSiteInvestigator = new HealthcareSiteInvestigator();
//            healthcareSiteInvestigator.setHealthcareSite(healthcareSite);
//            healthcareSiteInvestigator.setInvestigator(inv);
//            inv.getHealthcareSiteInvestigators().add(healthcareSiteInvestigator);
            inv.getHealthcareSiteInvestigators().get(0).setHealthcareSite(healthcareSite);
            
        }


        List<Investigator> invResults = investigatorDao.searchByExample(inv, true);

        Context context = null;
        if (parameterMap == null) {
            context = new HttpServletRequestContext(request);
        }
        else {
            context = new HttpServletRequestContext(request, parameterMap);
        }

        TableModel model = new TableModelImpl(context);
        try {
            return build(model, invResults).toString();
        }
        catch (Exception e) {
            log.error("Exception caught in SearchInvestigatorAjaxFacade");
            e.printStackTrace();
        }

        return "";
    }

    public InvestigatorDao getInvestigatorDao() {
        return investigatorDao;
    }

    public void setInvestigatorDao(InvestigatorDao investigatorDao) {
        this.investigatorDao = investigatorDao;
    }

}
