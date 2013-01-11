/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.web.HttpSessionRequiredException;

import edu.duke.cabig.c3pr.dao.ICD9DiseaseSiteDao;
import edu.duke.cabig.c3pr.domain.ICD9DiseaseSite;

public class ICD9DiseaseSiteFacade {
    private ICD9DiseaseSiteDao icd9DiseaseSiteDao;

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

    public List<ICD9DiseaseSite> matchDiseaseSites(String text) {

        List<ICD9DiseaseSite> anatomicSites = icd9DiseaseSiteDao.getBySubnames(new String[] { text });
        // cut down objects for serialization
        List<ICD9DiseaseSite> reducedAnatomicSites = new ArrayList<ICD9DiseaseSite>();
        for (ICD9DiseaseSite anatomicSite : anatomicSites) {
        	if(anatomicSite.getSelectable()){
        		reducedAnatomicSites.add(buildReduced(anatomicSite, Arrays.asList("id", "name","code","selectable")));
        	}
        }
        return reducedAnatomicSites;
    }
    
    public List<ICD9DiseaseSite> getLevel2DiseaseSiteCategories(Integer level1Id) {

        List<ICD9DiseaseSite> level2ICD9DiseaseSites = icd9DiseaseSiteDao.getById(level1Id).getChildSites();
        // cut down objects for serialization
        List<ICD9DiseaseSite> reducedAnatomicSites = new ArrayList<ICD9DiseaseSite>();
        for (ICD9DiseaseSite level2ICD9DiseaseSite : level2ICD9DiseaseSites) {
            reducedAnatomicSites.add(buildReduced(level2ICD9DiseaseSite, Arrays.asList("id", "name","code","selectable")));
        }
        return reducedAnatomicSites;
    }
    
    public List<ICD9DiseaseSite> getLevel3DiseaseSiteCategories(Integer level2Id) {

        List<ICD9DiseaseSite> level3ICD9DiseaseSites = icd9DiseaseSiteDao.getById(level2Id).getChildSites();
        // cut down objects for serialization
        List<ICD9DiseaseSite> reducedAnatomicSites = new ArrayList<ICD9DiseaseSite>();
        for (ICD9DiseaseSite level3ICD9DiseaseSite : level3ICD9DiseaseSites) {
            reducedAnatomicSites.add(buildReduced(level3ICD9DiseaseSite, Arrays.asList("id", "name","code","selectable")));
        }
        return reducedAnatomicSites;
    }
    
    public List<ICD9DiseaseSite> getLevel4DiseaseSiteCategories(Integer level3Id) {

        List<ICD9DiseaseSite> level4ICD9DiseaseSites = icd9DiseaseSiteDao.getById(level3Id).getChildSites();
        // cut down objects for serialization
        List<ICD9DiseaseSite> reducedAnatomicSites = new ArrayList<ICD9DiseaseSite>();
        for (ICD9DiseaseSite level4ICD9DiseaseSite : level4ICD9DiseaseSites) {
            reducedAnatomicSites.add(buildReduced(level4ICD9DiseaseSite, Arrays.asList("id", "name","code","selectable")));
        }
        return reducedAnatomicSites;
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

    private String getFormSessionAttributeName() {
        return "edu.duke.cabig.c3pr.web.registration.RegistrationDetailsController.FORM.command";
    }

    private String getFormSessionAttributeNameAgain() {
        return "edu.duke.cabig.c3pr.web.registration.RegistrationDetailsController.FORM.command";
    }

    private String[] extractSubnames(String text) {
        return text.split("\\s+");
    }

    public ICD9DiseaseSiteDao getICD9DiseaseSiteDao() {
        return icd9DiseaseSiteDao;
    }

    public void setAnatomicSiteDao(ICD9DiseaseSiteDao icd9DiseaseSiteDao) {
        this.icd9DiseaseSiteDao = icd9DiseaseSiteDao;
    }

}
