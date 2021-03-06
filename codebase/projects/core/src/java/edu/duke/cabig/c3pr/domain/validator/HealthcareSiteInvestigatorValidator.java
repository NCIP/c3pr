/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain.validator;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;

public class HealthcareSiteInvestigatorValidator implements Validator {

    public boolean supports(Class clazz) {
        return HealthcareSiteInvestigator.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
    }

    public void validateInvestigators(Object target, Errors errors) {
        HealthcareSiteInvestigator siteInvestigator = (HealthcareSiteInvestigator) target;
        HealthcareSite site = siteInvestigator.getHealthcareSite();
        List<HealthcareSiteInvestigator> allSiteInvestigators = site
                        .getHealthcareSiteInvestigators();
        Set<HealthcareSiteInvestigator> uniqueSiteInvestigators = new TreeSet<HealthcareSiteInvestigator>();
        uniqueSiteInvestigators.addAll(allSiteInvestigators);
        if (allSiteInvestigators.size() > uniqueSiteInvestigators.size()) {
            errors.reject("Investigator already exists");
        }

    }

}
