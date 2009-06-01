package edu.duke.cabig.c3pr.web.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.Summary3ReportDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Summary3Report;
import edu.duke.cabig.c3pr.service.SummaryReportService;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AutomaticSaveAjaxableFormController;
import edu.duke.cabig.c3pr.web.report.tabs.Summary3ReportDetailsTab;
import edu.duke.cabig.c3pr.web.report.tabs.Summary3ReportResultsTab;
import edu.duke.cabig.c3pr.xml.Summary3ReportGenerator;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

public class Summary3ReportController<C extends Summary3Report> extends  AutomaticSaveAjaxableFormController<C, Summary3Report, Summary3ReportDao> {
	
	private SummaryReportService summaryReportService;
	
	private Summary3ReportDao summary3ReportDao;
	
	public void setSummary3ReportDao(Summary3ReportDao summary3ReportDao) {
		this.summary3ReportDao = summary3ReportDao;
	}

	private HealthcareSiteDao healthcareSiteDao;
	
	
	private Summary3ReportGenerator summary3ReportGenerator;
		
		public void setSummary3ReportGenerator(
			Summary3ReportGenerator summary3ReportGenerator) {
		this.summary3ReportGenerator = summary3ReportGenerator;
	}

		public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

		public void setSummaryReportService(SummaryReportService summaryReportService) {
			this.summaryReportService = summaryReportService;
		}
		
		public Summary3ReportController() {
	        setCommandClass(Summary3Report.class);
	        Flow<C> flow = new Flow<C>("Summary 3 Report");
	        layoutTabs(flow);
	        setFlow(flow);
	        setBindOnNewForm(true);
	    }
		
		@Override
		protected Object formBackingObject(HttpServletRequest request)
				throws Exception {
			Summary3Report summary3Report = new Summary3Report();
	//		String localOrganizationNCIInstituteCode =  Configuration.LOCAL_NCI_INSTITUTE_CODE.getDefault().toString();
			String localOrganizationNCIInstituteCode =  "CRB";
			HealthcareSite reportingOrganization = healthcareSiteDao.getByNciInstituteCode(localOrganizationNCIInstituteCode);
			summary3Report.setReportingOrganization(reportingOrganization);
			Date startDate = new Date();
		//	Calendar cal = new GregorianCalendar();
			int year = startDate.getYear();
			startDate.setMonth(0);
			startDate.setDate(1);
			Date endDate = new Date();
			
			summary3Report.setStartDate(startDate);
			summary3Report.setEndDate(endDate);
			
			return summary3Report;
		}
		
		@Override
		protected void postProcessPage(HttpServletRequest request, Object command,
				Errors errors, int page) throws Exception {
			super.postProcessPage(request, command, errors, page);
			Summary3Report summary3Report = (Summary3Report) command;
			if(page == 0){
				summaryReportService.buildSummary3Report(summary3Report);
			}
		}
		
		 /**
	     * Layout Tabs
	     *
	     * @param request - flow the Flow object
	     */
	    protected void layoutTabs(Flow flow) {
	        flow.addTab(new Summary3ReportDetailsTab());
	        flow.addTab(new Summary3ReportResultsTab());
	    }
		
		private void generateOutput(String outFile,String filePath,HttpServletResponse response) throws Exception{
			
			File file = new File(filePath+File.separator+outFile);
			FileInputStream fileIn = new FileInputStream(file);
			
			response.setContentType( "application/x-download" );
			response.setHeader( "Content-Disposition", "attachment; filename="+outFile );
			response.setHeader("Content-length", String.valueOf(file.length()));
			response.setHeader("Pragma", "private");
			response.setHeader("Cache-control","private, must-revalidate");
			
			
			OutputStream out = response.getOutputStream();
			
			byte[] buffer = new byte[2048];
			int bytesRead = fileIn.read(buffer);
			while (bytesRead >= 0) {
			  if (bytesRead > 0)
			    out.write(buffer, 0, bytesRead);
			    bytesRead = fileIn.read(buffer);
			}
			out.flush();
			out.close();
			fileIn.close();
		}
		
		@Override
		protected void initBinder(HttpServletRequest request,
				ServletRequestDataBinder binder) throws Exception {
			super.initBinder(request, binder);
			binder.registerCustomEditor(Date.class, ControllerTools.getDateEditor(false));
			binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(healthcareSiteDao));
		}


		@Override
		protected ModelAndView processFinish(HttpServletRequest request,
				HttpServletResponse response, Object command, BindException errors)
				throws Exception {
			String filePath = System.getenv("CATALINA_HOME") + System.getProperty("file.separator")
	        + "conf" + System.getProperty("file.separator") + "c3pr"  + System.getProperty("file.separator") +"SummaryReports" + System.getProperty("file.separator") ;
			
			Summary3Report summary3Report = (Summary3Report) command;
	   		try {
	   			
	    			String xml =  summaryReportService.generateXML(summary3Report);;
	    			
		    			String pdfOutFile = "summaryReport-"+summary3Report.getReportingOrganization()+".pdf";
		    	        // generate report and send ...
		    	        summary3ReportGenerator.generatePdf(xml,filePath+pdfOutFile);
		    			generateOutput(pdfOutFile,filePath,response);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RemoteException ("Error generating PDF ",e);
				}
				
			return null;
		}

		@Override
		protected Summary3ReportDao getDao() {
			return summary3ReportDao;
		}

		@Override
		protected Summary3Report getPrimaryDomainObject(C command) {
			return command ;
		}
	   
	}


