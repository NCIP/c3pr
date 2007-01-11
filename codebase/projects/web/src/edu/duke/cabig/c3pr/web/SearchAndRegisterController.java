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

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ParticipantIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.service.ParticipantService;
import edu.duke.cabig.c3pr.service.StudyService;


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
	    	if ("MRN".equals(searchType)) {
				ParticipantIdentifier participantIdentifier = new ParticipantIdentifier();
				participantIdentifier.setMedicalRecordNumber(searchTextPart);
				//FIXME:
				// participant.addParticipantIdentifier(participantIdentifier);
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
		System.out
				.println("-----------------------------------------------------------------------");
		System.out.println("type: " + type);
		System.out.println("searchText: " + searchtext);
		System.out
				.println("-----------------------------------------------------------------------");
		log.debug("search string = " + searchtext + "; type = " + type);

		if ("status".equals(type))
			study.setStatus(searchtext);
		else if ("T".equals(type))
			study.setType(searchtext);
		else if ("P".equals(type))
			study.setPhaseCode(searchtext);
		else if ("S".equals(type))
			study.setStatus(searchtext);
		else if ("SP".equals(type))
			study.setSponsorCode(searchtext);
		else if ("M".equals(type))
			study.setMonitorCode(searchtext);
		else if ("D".equals(type))
			study.setDiseaseCode(searchtext);

		List<Study> studies = studyService.search(study);
		if (studies == null || studies.size() == 0) {
			System.out
					.println("----------------------studies is null----------------------");
			studies = new ArrayList<Study>();
			Study temp = new Study();
			temp.setId(0);
			temp.setMultiInstitutionIndicator(new Boolean(true));
			temp.setShortTitleText("CALGB_TEST_MOCK");
			temp.setSponsorCode("C_1_XYZ_MOCK");
			temp.setTargetAccrualNumber(23);
			temp.setStatus("open");
			studies.add(temp);
			Study temp1 = new Study();
			temp1.setId(0);
			temp1.setMultiInstitutionIndicator(new Boolean(true));
			temp1.setShortTitleText("DUKE_TEST_MOCK");
			temp1.setSponsorCode("C_1_ABC_MOCK");
			temp1.setTargetAccrualNumber(55);
			temp1.setStatus("closed");
			studies.add(temp1);
		} else
			System.out
					.println("----------------------studies is not null----------------------");
		log.debug("Search results size " + studies.size());
		for (int i = 0; i < studies.size(); i++) {
			if (studies.get(i).getStudySites() == null
					|| studies.get(i).getStudySites().size() == 0) {
				System.out.println("removing study[" + i + "] from studies");
				studies.remove(i);
				i--;
			}
		}
		log.debug("Search results size after filtering " + studies.size());
		System.out.println("Search results size " + studies.size());
		Map map = errors.getModel();
		map.put("studies", studies);
		map.put("searchType", getSearchType());
//		map.put("searchTypePart", getSearchTypeParticipant());		
		ModelAndView modelAndView = new ModelAndView(getSuccessView(), map);
		return modelAndView;
	}

	protected Map<String, Object> referenceData(
			HttpServletRequest httpServletRequest) throws Exception {
		Map<String, Object> refdata = new HashMap<String, Object>();

		refdata.put("searchType", getSearchType());
		refdata.put("searchTypePart", getSearchTypeParticipant());
		return refdata;
	}
	 private List<LOV> getSearchTypeParticipant(){
			List<LOV> col = new ArrayList<LOV>();
			LOV lov1 = new LOV("N", "LastName");
			LOV lov2 = new LOV("MRN", "MRN");						
			col.add(lov1);
	    	col.add(lov2);    	
	    	return col;
		}
	private List<LOV> getSearchType() {
		List<LOV> col = new ArrayList<LOV>();
		LOV lov1 = new LOV("T", "Type");
		LOV lov2 = new LOV("S", "Status");
		LOV lov3 = new LOV("P", "Phase");
		LOV lov4 = new LOV("D", "Disease");
		LOV lov5 = new LOV("M", "Monitor");
		LOV lov6 = new LOV("SP", "Sponsor");

		col.add(lov1);
		col.add(lov2);
		col.add(lov3);
		col.add(lov4);
		col.add(lov5);
		col.add(lov6);

		return col;
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
