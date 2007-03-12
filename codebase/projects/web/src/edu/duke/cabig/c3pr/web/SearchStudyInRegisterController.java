package edu.duke.cabig.c3pr.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.Lov;

public class SearchStudyInRegisterController extends SimpleFormController {
	private static Log log = LogFactory
			.getLog(SearchStudyInRegisterController.class);

	private StudyDao studyDao;

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object oCommand, BindException errors)
			throws Exception {
		SearchCommand searchRegisterCommand = (SearchCommand) oCommand;
		String type=searchRegisterCommand.getSearchType();
		String searchtext=searchRegisterCommand.getSearchText();
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
    	
    	List<Study> studies = studyDao.searchByExample(study, true);   
    	log.debug("Search results size " +studies.size());
    	Map map = errors.getModel();
		map.put("studies", studies);
		map.put("searchTypeRefData", getSearchType());
    	map.put("participantId", request.getParameter("participantId"));		
		ModelAndView modelAndView = new ModelAndView(getSuccessView(), map);
		return modelAndView;
	}

	protected Map<String, Object> referenceData(
			HttpServletRequest httpServletRequest) throws Exception {
		Map<String, Object> refdata = new HashMap<String, Object>();

		refdata.put("searchType", getSearchType());
		return refdata;
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
	
	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}
}
