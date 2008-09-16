package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import edu.duke.cabig.c3pr.dao.PlannedNotificationDao;
import edu.duke.cabig.c3pr.dao.RecipientScheduledNotificationDao;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.ScheduledNotification;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.PersonnelService;

/**
 * User: Vinay G Date: Jun 11, 2008 Time: 1:56:21 PM
 */

public class ViewInboxController extends ParameterizableViewController {

	protected static final Log log = LogFactory.getLog(ViewInboxController.class);
	
	private RecipientScheduledNotificationDao recipientScheduledNotificationDao;
	
    private ResearchStaffDao researchStaffDao;

    private PlannedNotificationDao plannedNotificationDao;

    private PersonnelService personnelService;
	

	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if(request.getParameter("rsnId") != null){
			Integer id = Integer.valueOf(request.getParameter("rsnId"));
			RecipientScheduledNotification rsn = recipientScheduledNotificationDao.getById(id);
			rsn.setIsRead(Boolean.TRUE);
			rsn.setDateRead(new Date());
			recipientScheduledNotificationDao.save(rsn);
			getNotifications(request);
		}
		
		return super.handleRequestInternal(request, response);
	}

	private void getNotifications(HttpServletRequest request){
    	List<RecipientScheduledNotification> recipientScheduledNotificationsList = personnelService.getRecentNotifications(request);
    	request.getSession().setAttribute("recipientScheduledNotification", recipientScheduledNotificationsList);
    }

	
	public RecipientScheduledNotificationDao getRecipientScheduledNotificationDao() {
		return recipientScheduledNotificationDao;
	}

	public void setRecipientScheduledNotificationDao(
			RecipientScheduledNotificationDao recipientScheduledNotificationDao) {
		this.recipientScheduledNotificationDao = recipientScheduledNotificationDao;
	}


	public PersonnelService getPersonnelService() {
		return personnelService;
	}


	public void setPersonnelService(PersonnelService personnelService) {
		this.personnelService = personnelService;
	}


	public PlannedNotificationDao getPlannedNotificationDao() {
		return plannedNotificationDao;
	}


	public void setPlannedNotificationDao(
			PlannedNotificationDao plannedNotificationDao) {
		this.plannedNotificationDao = plannedNotificationDao;
	}


	public ResearchStaffDao getResearchStaffDao() {
		return researchStaffDao;
	}


	public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
		this.researchStaffDao = researchStaffDao;
	}
	
	

	/* use this code if you want to use extreme components instead of regular html for the inbox view. 
	 * 
	 public Object build(TableModel model, Object object) throws Exception {


		//This goes in the handleRequestInternal 
		Object viewData = null;
		Context context = new HttpServletRequestContext(request);
		TableModel model = new TableModelImpl(context);
		Object rsnObject = request.getSession().getAttribute(
				"recipientScheduledNotification");
		Object snObject = request.getSession().getAttribute(
				"scheduledNotifications");
		try {
			if (rsnObject != null) {
				viewData = build(model, rsnObject).toString();
			}
			if (snObject != null) {
				viewData = build(model, snObject).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("assembler", viewData);
		//This goes in the handleRequestInternal
		
		List objList = null;
		List<ScheduledNotification> snList = null;
		List<RecipientScheduledNotification> rsnList = null;
		if (object instanceof List) {
			objList = (List) object;
			if (objList.get(0) instanceof ScheduledNotification) {
				snList = (List<ScheduledNotification>) objList;
			} else {
				if (objList.get(0) instanceof RecipientScheduledNotification) {
					snList = new ArrayList<ScheduledNotification>();
					rsnList = (List<RecipientScheduledNotification>) objList;
					for (RecipientScheduledNotification rsn : rsnList) {
						snList.add(rsn.getScheduledNotification());
					}
				}
			}
		}

		Table table = model.getTableInstance();
		table.setAutoIncludeParameters(false);
		table.setTableId("assembler");
		table.setItems(snList);
		table.setAction(model.getContext().getContextPath()
				+ "/pages/report/createReport");
		table.setTitle("Email Notifications");
		table.setShowPagination(false);
		table.setOnInvokeAction("buildTable('assembler')");
		table.setImagePath(model.getContext().getContextPath()
				+ "/images/table/*.gif");
		table.setSortable(false);
		model.addTable(table);

		Row row = model.getRowInstance();
		row.setHighlightRow(Boolean.TRUE);
		model.addRow(row);

		Column columnDate = model.getColumnInstance();
		columnDate.setTitle("Date");
		columnDate.setProperty("dateSent");
		model.addColumn(columnDate);

		Column columnTitle = model.getColumnInstance();
		columnTitle.setTitle("Title");
		columnTitle.setProperty("title");
		//columnTitle.setCell((ViewRegistrationLinkCustomCell.class).getName());
		model.addColumn(columnTitle);

		return model.assemble();
	}*/

}
