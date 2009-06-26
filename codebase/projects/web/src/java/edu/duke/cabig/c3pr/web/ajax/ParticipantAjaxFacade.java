package edu.duke.cabig.c3pr.web.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.HttpSessionRequiredException;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;

/**
 * @author Priyatam
 */
public class ParticipantAjaxFacade {
    private ParticipantDao participantDao;

    private HealthcareSiteDao healthcareSiteDao;

    private static Log log = LogFactory.getLog(ParticipantAjaxFacade.class);

    @SuppressWarnings("unchecked")
    private <T> T buildReduced(T src, List<String> properties) {
        T dst = null;
        try {
            // it doesn't seem like this cast should be necessary
            dst = (T) src.getClass().newInstance();
        }
        catch (InstantiationException e) {
            throw new RuntimeException("Failed to instantiate " + src.getClass().getName(), e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to instantiate " + src.getClass().getName(), e);
        }

        BeanWrapper source = new BeanWrapperImpl(src);
        BeanWrapper destination = new BeanWrapperImpl(dst);
        for (String property : properties) {
            destination.setPropertyValue(property, source.getPropertyValue(property));
        }
        return dst;
    }

    public List<HealthcareSite> matchHealthcareSites(String text) throws Exception {

        List<HealthcareSite> healthcareSites = healthcareSiteDao
                        .getBySubnames(extractSubnames(text));

        List<HealthcareSite> reducedHealthcareSites = new ArrayList<HealthcareSite>(healthcareSites
                        .size());
        for (HealthcareSite healthcareSite : healthcareSites) {
        	if(healthcareSite instanceof RemoteHealthcareSite){
        		reducedHealthcareSites.add(buildReduced(healthcareSite, Arrays.asList("id", "name",
                "ctepCode","externalId")));
        	}
        	else {reducedHealthcareSites.add(buildReduced(healthcareSite, Arrays.asList("id", "name",
                            "ctepCode")));
        	}
        }
        return reducedHealthcareSites;

    }

    private final Object getCommandOnly(HttpServletRequest request) throws Exception {
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

    // //// CONFIGURATION

    @Required
    private String getFormSessionAttributeName() {
        return "edu.duke.cabig.c3pr.web.CreateParticipantController.FORM.command";
    }

    private String getFormSessionAttributeNameAgain() {
        return "edu.duke.cabig.c3pr.web.EditParticipantController.FORM.command";
    }

    private String[] extractSubnames(String text) {
        return text.split("\\s+");
    }

    public HealthcareSiteDao getHealthcareSiteDao() {
        return healthcareSiteDao;
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }

    public ParticipantDao getParticipantDao() {
        return participantDao;
    }

    public void setParticipantDao(ParticipantDao participantDao) {
        this.participantDao = participantDao;
    }

}