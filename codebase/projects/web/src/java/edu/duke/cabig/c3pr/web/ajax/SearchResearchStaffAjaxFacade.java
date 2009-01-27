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
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.ResearchStaff;

public class SearchResearchStaffAjaxFacade {
    private static Log log = LogFactory.getLog(SearchResearchStaffAjaxFacade.class);

    private ResearchStaffDao researchStaffDao;

    private HealthcareSiteDao healthcareSiteDao;

    public Object build(TableModel model, Collection rStaffResults) throws Exception {

        Table table = model.getTableInstance();
        table.setAutoIncludeParameters(false);
        table.setTableId("assembler");
        table.setItems(rStaffResults);
        table.setAction(model.getContext().getContextPath() + "/pages/admin/createResearchStaff");
        table.setTitle("Research Staff");
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
        columnName.setCell((ResearchStaffLinkDisplayCell.class).getName());
        model.addColumn(columnName);

        Column columnSite = model.getColumnInstance();
        columnSite.setTitle("Site");
        columnSite.setProperty("healthcareSite.name");
        model.addColumn(columnSite);

        Column columnNci = model.getColumnInstance();
        columnNci.setTitle("NCI Identifier");
        columnNci.setProperty("nciIdentifier");
        model.addColumn(columnNci);

        return model.assemble();
    }

    public String getTable(Map<String, List> parameterMap, String[] params,
                    HttpServletRequest request) {

        ResearchStaff rStaff = new ResearchStaff();
        if (!StringUtils.isEmpty(params[0])) {
            rStaff.setFirstName(params[0]);
        }
        if (!StringUtils.isEmpty(params[1])) {
            rStaff.setLastName(params[1]);
        }
        if (!StringUtils.isEmpty(params[2])) {
            rStaff.setNciIdentifier(params[2]);
        }
        if (!StringUtils.isEmpty(params[3])) {
            HealthcareSite healthcareSite = healthcareSiteDao.getById(Integer.parseInt(params[3]));
            rStaff.setHealthcareSite(healthcareSite);
        }

        List<ResearchStaff> rStaffResults = researchStaffDao.searchByExample(rStaff, true);

        Context context = null;
        if (parameterMap == null) {
            context = new HttpServletRequestContext(request);
        }
        else {
            context = new HttpServletRequestContext(request, parameterMap);
        }

        TableModel model = new TableModelImpl(context);
        try {
            return build(model, rStaffResults).toString();
        }
        catch (Exception e) {
            log.error("Exception caught in SearchresearchStaffAjaxFacade");
            e.printStackTrace();
        }

        return "";
    }

    public ResearchStaffDao getResearchStaffDao() {
        return researchStaffDao;
    }

    public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
        this.researchStaffDao = researchStaffDao;
    }

    public HealthcareSiteDao getHealthcareSiteDao() {
        return healthcareSiteDao;
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }

}
