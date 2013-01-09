/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.service.impl.passwordpolicy;

import org.springframework.beans.factory.annotation.Required;

import edu.duke.cabig.c3pr.dao.PasswordPolicyDao;
import edu.duke.cabig.c3pr.domain.User;
import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordPolicy;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.passwordpolicy.PasswordPolicyService;
import edu.duke.cabig.c3pr.service.passwordpolicy.validators.LoginPolicyValidator;
import edu.duke.cabig.c3pr.service.passwordpolicy.validators.PasswordCreationPolicyValidator;

public class PasswordPolicyServiceImpl implements PasswordPolicyService {

    PasswordCreationPolicyValidator passwordCreationPolicyValidator;

    LoginPolicyValidator loginPolicyValidator;

    PasswordPolicyDao passwordPolicyDao;

    CSMUserRepository csmUserRepository;

    public PasswordPolicyServiceImpl() {
        passwordCreationPolicyValidator = new PasswordCreationPolicyValidator();
        loginPolicyValidator = new LoginPolicyValidator();
    }

    public PasswordPolicy getPasswordPolicy() {
        return passwordPolicyDao.getById(1);
    }

    public void setPasswordPolicy(PasswordPolicy passwordPolicy) {
        passwordPolicyDao.save(passwordPolicy);
    }

    public String publishPasswordPolicy() {
        return "TODO";
    }

    public String publishPasswordPolicy(String xsltFileName) {
        return "TODO";
    }

    public boolean validatePasswordAgainstCreationPolicy(User user, String password)
            throws C3PRBaseException {
        return passwordCreationPolicyValidator.validate(getPasswordPolicy(), user, password);
    }

    /*
     * public boolean validatePasswordAgainstLoginPolicy(Credential credential) throws
     * CaaersSystemException { return loginPolicyValidator.validate(getPasswordPolicy(),
     * credential); }
     */

    @Required
    public void setPasswordPolicyDao(PasswordPolicyDao passwordPolicyDao) {
        this.passwordPolicyDao = passwordPolicyDao;
    }

    @Required
    public void setCsmUserRepository(final CSMUserRepository csmUserRepository) {
        this.csmUserRepository = csmUserRepository;
        passwordCreationPolicyValidator.setCsmUserRepository(csmUserRepository);
        //loginPolicyValidator.setCsmUserRepository(csmUserRepository);

    }


}
