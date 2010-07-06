package edu.duke.cabig.c3pr.aspects.springaop;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;

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

	@AfterReturning("execution(* edu.duke.cabig.c3pr.domain.repository.impl.ResearchStaffRepositoryImpl.createResearchStaffWithCSMUserAndAssignRoles(..))" 
                    + " && args(researchStaff, username, associationMap, hasAccessToAllSites)")
    public void createResearchStaffWithCSMUserAndAssignRoles(ResearchStaff researchStaff, String username, 
			Map<HealthcareSite, List<C3PRUserGroupType>> associationMap, boolean hasAccessToAllSites) {
		sendEmail(researchStaff);
    }
	
	@AfterReturning("execution(* edu.duke.cabig.c3pr.domain.repository.impl.ResearchStaffRepositoryImpl.createCSMUser(..))"
            + " && args(researchStaff, username, hasAccessToAllSites)")
	public void createCSMUser(ResearchStaff researchStaff, String username, boolean hasAccessToAllSites) {
		sendEmail(researchStaff);
	}

	@AfterReturning("execution(* edu.duke.cabig.c3pr.domain.repository.impl.ResearchStaffRepositoryImpl.createSuperUser(..))"
            + " && args(researchStaff,  username , associationMap)")
	public void createSuperUser(ResearchStaff researchStaff, String username, 
			Map<HealthcareSite, List<C3PRUserGroupType>> associationMap) {
		sendEmail(researchStaff);
	}
	
	@AfterReturning("execution(* edu.duke.cabig.c3pr.domain.repository.impl.ResearchStaffRepositoryImpl.createResearchStaffWithCSMUser(..))"
            + " && args(researchStaff , username, hasAccessToAllSites)")
	public void createResearchStaffWithCSMUser(ResearchStaff researchStaff, String username, boolean hasAccessToAllSites) {
		sendEmail(researchStaff);
	}
	
	@AfterReturning("execution(* edu.duke.cabig.c3pr.domain.repository.impl.ResearchStaffRepositoryImpl.createCSMUserAndAssignRoles(..))"
            + " && args(researchStaff, username, associationMap, hasAccessToAllSites)")
	public void createCSMUserAndAssignRoles(ResearchStaff researchStaff, String username, Map<HealthcareSite, List<C3PRUserGroupType>> associationMap,
			boolean hasAccessToAllSites) {
		sendEmail(researchStaff);
	}

	private void sendEmail(ResearchStaff staff) {
		try {
	          SimpleMailMessage msg = new SimpleMailMessage( this.accountCreatedTemplateMessage);
	          msg.setTo(staff.getEmail());
	          msg.setText("A new C3PR account has been created for you.\n"
	                  + "Your username is follows:\n"
	                  + "Username: " + csmRepository.getUsernameById(staff.getLoginId())
	                  + "\n"
	                  + "You must create your password before you can login. In order to do so please visit this URL:\n"
	                  + "\n"
	                  + changeURL + "&token=" + staff.getToken() + "\n"
	                  + "\n"
	                  + "Regards\n"
	                  + "The C3PR Notification System.\n");
	          log.debug("Trying to send user account confirmation email. URL is " + changeURL + "&token=" + staff.getToken());
	          this.mailSender.send(msg);
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
