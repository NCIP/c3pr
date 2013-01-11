/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.ajax;

import java.util.Collection;
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
import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.domain.PersonUser;

public class SearchResearchStaffAjaxFacade {
    private static Log log = LogFactory.getLog(SearchResearchStaffAjaxFacade.class);

    private PersonUserDao personUserDao;

    private HealthcareSiteDao healthcareSiteDao;

    public Object build(TableModel model, Collection<PersonUser> rStaffResults) throws Exception {

        Table table = model.getTableInstance();
        table.setAutoIncludeParameters(false);
        table.setTableId("assembler");
        table.setItems(rStaffResults);
        table.setAction(model.getContext().getContextPath() + "/pages/admin/editResearchStaff");
        table.setTitle("Person or User");
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
        columnSite.setTitle("Organization");
        columnSite.setCell((HealthcareSiteLinkDisplayCell.class).getName());  
        columnSite.setProperty("healthcareSites");
        model.addColumn(columnSite);

        Column columnCtep = model.getColumnInstance();
        columnCtep.setTitle("Assigned identifier");
        columnCtep.setProperty("assignedIdentifier");
        model.addColumn(columnCtep);
        
        Column columnNci = model.getColumnInstance();
        columnNci.setTitle("NCI identifier");
        columnNci.setProperty("externalId");
        columnNci.setCell((NciIdLinkDisplayCell.class).getName());
        model.addColumn(columnNci);
        
        Column columnEmail = model.getColumnInstance();
        columnEmail.setTitle("Email");
        columnEmail.setProperty("email");
        model.addColumn(columnEmail);

        return model.assemble();
    }

//    public String getTable(Map<String, List> parameterMap, String[] params,
//                    HttpServletRequest request) {
//
//        LocalResearchStaff rStaff = new LocalResearchStaff();
//        if (!StringUtils.isBlank(params[0])) {
//            rStaff.setFirstName(params[0]);
//        }
//        if (!StringUtils.isBlank(params[1])) {
//            rStaff.setLastName(params[1]);
//        }
//        if (!StringUtils.isBlank(params[2])) {
//            rStaff.setAssignedIdentifier(params[2]);
//        }
//        if (!StringUtils.isBlank(params[3])) {
//            HealthcareSite healthcareSite = healthcareSiteDao.getById(Integer.parseInt(params[3]));
//            rStaff.addHealthcareSite(healthcareSite);
//        }
//
//        List<ResearchStaff> rStaffResults = personUserDao.searchByExample(rStaff, true);
//
//        Context context = null;
//        if (parameterMap == null) {
//            context = new HttpServletRequestContext(request);
//        }
//        else {
//            context = new HttpServletRequestContext(request, parameterMap);
//        }
//
//        TableModel model = new TableModelImpl(context);
//        try {
//            return build(model, rStaffResults).toString();
//        }
//        catch (Exception e) {
//            log.error("Exception caught in SearchresearchStaffAjaxFacade", e);
//        }
//
//        return "";
//    }
    
    public String getPersonOrUserTable(Map parameterMap, HttpServletRequest request) {
        Context context = new HttpServletRequestContext(request);

        TableModel model = new TableModelImpl(context);
        //Collection<PersonOrUserWrapper> personOrUserWrapperList = (Collection<PersonOrUserWrapper>) parameterMap.get("personOrUserResults");
        Collection<PersonUser> personOrUserWrapperList = (Collection<PersonUser>) parameterMap.get("personOrUserResults");
        try {
            return build(model, personOrUserWrapperList).toString();
        }
        catch (Exception e) {
            log.debug(e.getMessage());
        }
        return "";
    }

    public PersonUserDao getPersonUserDao() {
        return personUserDao;
    }

    public void setPersonUserDao(PersonUserDao personUserDao) {
        this.personUserDao = personUserDao;
    }

    public HealthcareSiteDao getHealthcareSiteDao() {
        return healthcareSiteDao;
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }

}
