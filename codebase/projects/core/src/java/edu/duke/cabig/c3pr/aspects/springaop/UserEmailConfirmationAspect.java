package edu.duke.cabig.c3pr.aspects.springaop;

import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.utils.RoleBasedHealthcareSitesAndStudiesDTO;
import gov.nih.nci.logging.api.util.StringUtils;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Oct 2, 2007 Time: 4:46:46 PM To change this template
 * use File | Settings | File Templates.
 */
@Aspect
public class UserEmailConfirmationAspect {

    private MailSender mailSender;

    private SimpleMailMessage accountCreatedTemplateMessage;
    
    private CSMUserRepository csmRepository;

    private Logger log = Logger.getLogger(UserEmailConfirmationAspect.class);
    
    private String changeURL;

    @Required
    public void setChangeURL(String changeURL) {
		this.changeURL = changeURL;
	}
    
	@AfterReturning("execution(* edu.duke.cabig.c3pr.domain.repository.impl.PersonUserRepositoryImpl.createOrModifyResearchStaffWithUserAndAssignRoles(..))" 
                    + " && args(researchStaff, username, listAssociation)")
//	@AfterReturning("execution(* edu.duke.cabig.c3pr.dao.PersonUserDao.saveOrUpdatePersonUser(..))" 
//                    + " && args(personUser)")
    public void createResearchStaffWithCSMUserAndAssignRoles(PersonUser researchStaff, String username, List<RoleBasedHealthcareSitesAndStudiesDTO> listAssociation) {
		if(!StringUtils.isBlank(username)){
			sendEmail(researchStaff);
		} else {
			log.debug("Not sending email for the edit user flow.");
		}
    }
	
	@AfterReturning("execution(* edu.duke.cabig.c3pr.domain.repository.impl.PersonUserRepositoryImpl.createOrModifyUserWithoutResearchStaffAndAssignRoles(..))"
            + " && args(researchStaff, username, listAssociation)")
	public void createCSMUser(PersonUser researchStaff, String username, List<RoleBasedHealthcareSitesAndStudiesDTO> listAssociation) {
		if(!StringUtils.isBlank(username)){
			sendEmail(researchStaff);
		} else {
			log.debug("Not sending email for the edit user flow.");
		}
	}

	@AfterReturning("execution(* edu.duke.cabig.c3pr.domain.repository.impl.PersonUserRepositoryImpl.createSuperUser(..))"
            + " && args(researchStaff,  username , listAssociation)")
	public void createSuperUser(PersonUser researchStaff, String username, List<RoleBasedHealthcareSitesAndStudiesDTO> listAssociation) {
		if(!StringUtils.isBlank(username)){
			sendEmail(researchStaff);
		} else {
			log.debug("Not sending email for the edit user flow.");
		}
	}
	
	private void sendEmail(PersonUser personUser) {
		try {
			if(!StringUtils.isBlank(personUser.getLoginId())){
				SimpleMailMessage msg = new SimpleMailMessage( this.accountCreatedTemplateMessage);
		          msg.setTo(personUser.getEmail());
		          msg.setText("A new C3PR account has been created for you.\n"
		                  + "Your username is follows:\n"
		                  + "Username: " + csmRepository.getUsernameById(personUser.getLoginId())
		                  + "\n"
		                  + "You must create your password before you can login. In order to do so please visit this URL:\n"
		                  + "\n"
		                  + changeURL + "&token=" + personUser.getToken() + "\n"
		                  + "\n"
		                  + "Regards\n"
		                  + "The C3PR Notification System.\n");
		          log.debug("Trying to send user account confirmation email. URL is " + changeURL + "&token=" + personUser.getToken());
		          this.mailSender.send(msg);
			}
	      }
	      catch (MailException e) {
	          log.error("Could not send email due to  " + e.getMessage(),e);
	          // just log it for now
	      }
	}

	@Required
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

	@Required
    public void setAccountCreatedTemplateMessage(SimpleMailMessage accountCreatedTemplateMessage) {
        this.accountCreatedTemplateMessage = accountCreatedTemplateMessage;
    }

	@Required
	public void setCsmRepository(CSMUserRepository csmRepository) {
		this.csmRepository = csmRepository;
	}
}
