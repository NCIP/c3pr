package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.semanticbits.security.grid.GridLoginContext;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.service.ParticipantService;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.Lov;


public class SearchAndRegisterController extends SimpleFormController {
	private static Log log = LogFactory
			.getLog(SearchAndRegisterController.class);

	private StudyService studyService;
	private ParticipantService participantService;

	public StudyService getStudyService() {
		return studyService;
	}

	public void setStudyService(StudyService studyService) {
		this.studyService = studyService;
	}

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object oCommand, BindException errors)
			throws Exception {
		SearchRegisterCommand searchRegisterCommand = (SearchRegisterCommand) oCommand;
		String category = searchRegisterCommand.getSearchCategory();
		if (category.equalsIgnoreCase("participant")) {
			String searchTextPart=searchRegisterCommand.getSearchTypeTextPart();
			String searchType=searchRegisterCommand.getSearchTypePart();
			log.debug("search string = " + searchTextPart + "; type = " + searchType);
			Participant participant=new Participant();
			if ("N".equals(searchType)) {
				participant.setLastName(searchTextPart);
			}
			if ("Identifier".equals(searchType)) {
	    		Identifier identifier = new Identifier();
				identifier.setValue(searchTextPart);
				//FIXME:
				participant.addIdentifier(identifier);
			} 
			if(participantService==null){
				System.out.println("---------------------participantService is null------------------------");
			}
			List<Participant> participants = participantService
					.search(participant);

			Iterator<Participant> participantIter = participants.iterator();
			while (participantIter.hasNext()) {
				participant = participantIter.next();
				System.out.println("Id for participant is "
						+ participant.getId());
				System.out.println("LastName of participant is "
						+ participant.getLastName());
				System.out.println("FirstName of participant is "
						+ participant.getFirstName());
				// System.out.println(" D.O.B of participant is " +
				// participant.getBirthDate());

			}
			String type = searchRegisterCommand.getSearchType();
			String searchtext = searchRegisterCommand.getSearchTypeText();

			log.debug("Search results size " + participants.size());
			Map map = errors.getModel();
			map.put("participants", participants);
			map.put("studySiteId", request.getParameter("studySiteId"));
			map.put("searchTypeParticipant", getSearchTypeParticipant());			
			ModelAndView modelAndView = new ModelAndView("reg_participant_search", map);
			return modelAndView;

		}
		String type=searchRegisterCommand.getSearchType();
		String searchtext=searchRegisterCommand.getSearchTypeText();
		Study study = new Study();
    	log.debug("search string = " +searchtext+"; type = "+type);
 	   
    	if ("s".equals(type))
    		study.setStatus(searchtext);
    	else if ("id".equals(type))
    	{
    		Identifier id = new Identifier();
    		id.setValue(searchtext);
    		study.addIdentifier(id);
    	}
    	else if ("shortTitle".equals(type))
    		study.setShortTitleText(searchtext);
    	else if ("longTitle".equals(type))
    		study.setShortTitleText(searchtext);
    	
    	List<Study> studies = studyService.search(study);   
    	log.debug("Search results size " +studies.size());
    	Map map = errors.getModel();
    	map.put("studies", studies);
    	map.put("searchType",getSearchType() );    	
		ModelAndView modelAndView = new ModelAndView(getSuccessView(), map);
		return modelAndView;
	}

	protected Map<String, Object> referenceData(
			HttpServletRequest httpServletRequest) throws Exception {
		Map<String, Object> refdata = new HashMap<String, Object>();
		GridLoginContext gridLoginContext=(GridLoginContext)httpServletRequest.getSession().getAttribute("login-context");
		refdata.put("user", gridLoginContext.getFirstName()+" "+gridLoginContext.getLastName());
		refdata.put("searchType", getSearchType());
		refdata.put("searchTypePart", getSearchTypeParticipant());
		return refdata;
	}
	 private List<LOV> getSearchTypeParticipant(){
			List<LOV> col = new ArrayList<LOV>();
			LOV lov1 = new LOV("N", "Last Name");
			LOV lov2 = new LOV("Identifier", "Identifier");						
			col.add(lov1);
	    	col.add(lov2);    	
	    	return col;
		}
		private List<Lov> getSearchType(){
			Lov col = new Lov();
			col.addData("s", "Status");
			col.addData("id", "Identifier");
			col.addData("shortTitle", "Short Title");
			col.addData("longTitle", "Long Title");
				
	    	return col.getData();
		}	

	public class LOV {

		private String code;

		private String desc;

		LOV(String code, String desc) {
			this.code = code;
			this.desc = desc;

		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

	public ParticipantService getParticipantService() {
		return participantService;
	}

	public void setParticipantService(ParticipantService participantService) {
		this.participantService = participantService;
	}
}
