package edu.duke.cabig.c3pr.web.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.InvestigatorGroup;
import edu.duke.cabig.c3pr.service.OrganizationService;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.web.SimpleFormAjaxableController;
import edu.duke.cabig.c3pr.web.beans.DefaultObjectPropertyReader;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.TabConfigurer;

/*
 * @author Ramakrishna
 * 
 */
public class CreateInvestigatorGroupsController extends SimpleFormAjaxableController<InvestigatorGroupsCommand> {
	
	public CreateInvestigatorGroupsController() {
		 super();
		 this.setPage(new InvestigatorsGroupsTab<InvestigatorGroupsCommand>("Investigator Groups","Investigator Groups","admin/investigator_groups_create"));
	}
	
	private static Log log = LogFactory.getLog(CreateInvestigatorGroupsController.class);
    private OrganizationDao organizationDao;
    private OrganizationService organizationService;
    private String EDIT_FLOW = "EDIT_FLOW";
    private String SAVE_FLOW = "SAVE_FLOW";
    private String FLOW = "FLOW";
    private HealthcareSiteDao healthcareSiteDao;
    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
    private C3PRDefaultTabConfigurer c3PRDefaultTabConfigurer;
    private InvestigatorsGroupsTab<InvestigatorGroupsCommand> investigatorGroupsTab;

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		 log.debug("Loading formBackingObject:");
		InvestigatorGroupsCommand investigatorGroupsCommand = new InvestigatorGroupsCommand();
		this.c3PRDefaultTabConfigurer.injectDependencies(this.getPage());
		return investigatorGroupsCommand;
	}
	
	@Override
	 protected void initBinder(HttpServletRequest request,
             ServletRequestDataBinder binder) throws Exception {
			super.initBinder(request, binder);
			binder.registerCustomEditor(Date.class, ControllerTools
			.getDateEditor(true));
			binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(
			healthcareSiteDao));
			binder.registerCustomEditor(HealthcareSiteInvestigator.class, new CustomDaoEditor(
					healthcareSiteInvestigatorDao));
	 	}
	
	@Override
    protected ModelAndView onSubmit(HttpServletRequest request,
                                                 HttpServletResponse response, Object command, BindException errors) throws Exception {
		super.onSubmit(request, response, command, errors);

        InvestigatorGroupsCommand investigatorGroupsCommand = (InvestigatorGroupsCommand) command;
        log.debug("Inside the CreateInvestigatorGroupsController:");
        if(!isAjaxRequest(request) && (request.getParameter("_siteChanged").equals("false"))){
        	healthcareSiteDao.save(investigatorGroupsCommand.getHealthcareSite());
        }
        if((investigatorGroupsCommand.getHealthcareSite())!=null){
    		if (request.getParameter("_addGroup")!=null && request.getParameter("_addGroup").equals("true")){
    			investigatorGroupsCommand.getHealthcareSite().addInvestigatorGroup(new InvestigatorGroup());
    		}
    		 if (request.getParameter("_action")!=null && "siteChange".equals(request.getParameter("_action"))) {
    	            request.getSession().setAttribute("selectedSite", request.getParameter("_selectedSite"));
    	        }
        }
        Map map = errors.getModel();
		map.put("command",investigatorGroupsCommand); 
        ModelAndView mv = new ModelAndView("admin/show_investigator_groups", map);
        return mv;
    }
	
@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
	ModelAndView superModelAndView= super.handleRequestInternal(request, response);
	if(isAjaxRequest(request)){
		synchronized (this){
			if(request.getParameter("selected_site")!=null && request.getParameter("selected_site").equalsIgnoreCase("selected_site")){
				return new ModelAndView("admin/show_investigator_affiliations",superModelAndView.getModel());
			}
			return new ModelAndView("admin/show_investigator_groups",superModelAndView.getModel());
		}
	}
	return new ModelAndView("admin/investigator_groups_create",superModelAndView.getModel());
	}

public List<InvestigatorGroup> getInvestigatorGroups(int organizationId,HttpServletRequest request)
			throws Exception {
	 		HealthcareSite healthcareSite = healthcareSiteDao.getById(organizationId);
	 		((InvestigatorGroupsCommand) getCommand(request)).setHealthcareSite(healthcareSite);
	 		if (healthcareSite.getInvestigatorGroups()!=null &&healthcareSite.getInvestigatorGroups().size()>0){
	 			return healthcareSite.getInvestigatorGroups();
	 		}
	 		return null;
	}

 @Override
 protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception {
     // TODO Auto-generated method stub
     super.onBind(request, command, errors);
     handleRowDeletion(request, command);
 }

 public void handleRowDeletion(HttpServletRequest request, Object command) throws Exception {
     Enumeration enumeration = request.getParameterNames();
     Hashtable<String, List<Integer>> table = new Hashtable<String, List<Integer>>();
     while (enumeration.hasMoreElements()) {
         String param = (String) enumeration.nextElement();
         if (param.startsWith("_deletedRow-")) {
             String[] params = param.split("-");
             if (table.get(params[1]) == null)
                 table.put(params[1], new ArrayList<Integer>());
             table.get(params[1]).add(new Integer(params[2]));
         }
     }
     deleteRows(command, table);
 }

 public void deleteRows(Object command, Hashtable<String, List<Integer>> table) throws Exception {
     Enumeration<String> e = table.keys();
     while (e.hasMoreElements()) {
         String path = e.nextElement();
         List col = (List) new DefaultObjectPropertyReader(command, path).getPropertyValueFromPath();
         List<Integer> rowNums = table.get(path);
         List temp = new ArrayList();
         for (int i = 0; i < col.size(); i++) {
             if (!rowNums.contains(new Integer(i)))
                 temp.add(col.get(i));
         }
         col.removeAll(col);
         col.addAll(temp);
     }
 }

    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}

	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

	public HealthcareSiteInvestigatorDao getHealthcareSiteInvestigatorDao() {
		return healthcareSiteInvestigatorDao;
	}

	public void setHealthcareSiteInvestigatorDao(
			HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
		this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
	}
	
	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	@Override
	protected InvestigatorGroupsCommand save(Object command, BindException errors) {
	//	healthcareSiteDao.save(((InvestigatorGroupsCommand)command).getHealthcareSite());
		return null;
	}

	public InvestigatorsGroupsTab<InvestigatorGroupsCommand> getInvestigatorGroupsTab() {
		return investigatorGroupsTab;
	}

	
	public void setInvestigatorGroupsTab(
			InvestigatorsGroupsTab<InvestigatorGroupsCommand> investigatorGroupsTab) {
		this.investigatorGroupsTab = investigatorGroupsTab;
	}

	public C3PRDefaultTabConfigurer getC3PRDefaultTabConfigurer() {
		return c3PRDefaultTabConfigurer;
	}
	
	@Required
	public void setC3PRDefaultTabConfigurer(
			C3PRDefaultTabConfigurer c3PRDefaultTabConfigurer) {
		this.c3PRDefaultTabConfigurer = c3PRDefaultTabConfigurer;
	}

}
