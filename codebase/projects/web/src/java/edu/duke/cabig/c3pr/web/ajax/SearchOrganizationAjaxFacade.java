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

import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;

public class SearchOrganizationAjaxFacade {
    private static Log log = LogFactory.getLog(SearchOrganizationFacade.class);
    private OrganizationDao organizationDao;
    
    public Object build(TableModel model, Collection studies) throws Exception {

        Table table = model.getTableInstance();
        table.setAutoIncludeParameters(false);
        table.setTableId("assembler");
        table.setItems(studies);
        table.setAction(model.getContext().getContextPath() + "/pages/admin/createOrganization"); 
        table.setTitle("Organizations");
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
        columnName.setProperty("name");
        columnName.setCell((OrganizationLinkDisplayCell.class).getName());
        model.addColumn(columnName);

        Column columnNci = model.getColumnInstance();
        columnNci.setTitle("NCI Identifier");
        columnNci.setProperty("nciInstituteCode");
        model.addColumn(columnNci);

        return model.assemble();
    }

    public String getTable(Map<String, List> parameterMap, String[] params, HttpServletRequest request) {

    	HealthcareSite hcs = new HealthcareSite();
    	if(!StringUtils.isEmpty(params[0])){
    		hcs.setName(params[0]);
    	}
        if(!StringUtils.isEmpty(params[1])){
        	hcs.setNciInstituteCode(params[1]);
        }
        List<HealthcareSite> orgResults = organizationDao.searchByExample(hcs, true);

        Context context = null;
        if (parameterMap == null) {
            context = new HttpServletRequestContext(request);
        } else {
            context = new HttpServletRequestContext(request, parameterMap);
        }

        TableModel model = new TableModelImpl(context);
        try {
            return build(model, orgResults).toString();
        } catch (Exception e) {
        	log.error("Exception caught in SearchOrganizationFacade");
            e.printStackTrace();
        }

        return "";
    }

	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}

	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

}
