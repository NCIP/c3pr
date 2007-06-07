package edu.duke.cabig.c3pr.web.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.HttpSessionRequiredException;

import edu.duke.cabig.c3pr.dao.AnatomicSiteDao;
import edu.duke.cabig.c3pr.dao.StudyParticipantAssignmentDao;
import edu.duke.cabig.c3pr.domain.AnatomicSite;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.utils.Lov;

public class AnatomicDiseaseSiteFacade {
	private AnatomicSiteDao anatomicSiteDao;

	@SuppressWarnings("unchecked")
	private <T> T buildReduced(T src, List<String> properties) {
		T dst = null;
		try {
			// it doesn't seem like this cast should be necessary
			dst = (T) src.getClass().newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException("Failed to instantiate "
					+ src.getClass().getName(), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Failed to instantiate "
					+ src.getClass().getName(), e);
		}

		BeanWrapper source = new BeanWrapperImpl(src);
		BeanWrapper destination = new BeanWrapperImpl(dst);
		for (String property : properties) {
			destination.setPropertyValue(property, source
					.getPropertyValue(property));
		}
		return dst;
	}

	public List<AnatomicSite> matchDiseaseSites(String text) {

		List<AnatomicSite> anatomicSites = anatomicSiteDao.getBySubnames(new String[]{text});
		// cut down objects for serialization
		List<AnatomicSite> reducedAnatomicSites = new ArrayList<AnatomicSite>();
		for(AnatomicSite anatomicSite : anatomicSites){
			reducedAnatomicSites.add(buildReduced(anatomicSite, Arrays.asList("id","name")));
		}
		return reducedAnatomicSites;
	}

	private final Object getCommandOnly(HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session == null) {
			throw new HttpSessionRequiredException(
					"Must have session when trying to bind (in session-form mode)");
		}
		String formAttrName = getFormSessionAttributeName();
		Object sessionFormObject = session.getAttribute(formAttrName);
		if (sessionFormObject == null) {
			formAttrName = getFormSessionAttributeNameAgain();
			sessionFormObject = session.getAttribute(formAttrName);
			return sessionFormObject;
		}

		return sessionFormObject;
	}

	private String getFormSessionAttributeName() {
		return "edu.duke.cabig.c3pr.web.CreateRegistrationController.FORM.command";
	}

	private String getFormSessionAttributeNameAgain() {
		return "edu.duke.cabig.c3pr.web.CreateRegistrationController.FORM.command";
	}

	private String[] extractSubnames(String text) {
		return text.split("\\s+");
	}

	public AnatomicSiteDao getAnatomicSiteDao() {
		return anatomicSiteDao;
	}

	public void setAnatomicSiteDao(AnatomicSiteDao anatomicSiteDao) {
		this.anatomicSiteDao = anatomicSiteDao;
	}

	// //// CONFIGURATION

}
