package edu.duke.cabig.c3pr.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import edu.duke.cabig.c3pr.domain.ResearchStaff;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Oct 2, 2007 Time: 4:46:46 PM To change this template
 * use File | Settings | File Templates.
 */
@Aspect
public class UserEmailConfirmationAspect {

    private MailSender mailSender;

    private SimpleMailMessage accountCreatedTemplateMessage;

    private Logger log = Logger.getLogger(UserEmailConfirmationAspect.class);
    
    private String changeURL;

    @Required
    public void setChangeURL(String changeURL) {
		this.changeURL = changeURL;
	}

	@AfterReturning("execution(* edu.duke.cabig.c3pr.service.PersonnelService.save(..))"
                    + " && args(staff)")
    public void sendEmail(ResearchStaff staff) {

    	try {
          SimpleMailMessage msg = new SimpleMailMessage(
                          this.accountCreatedTemplateMessage);
          msg.setTo(staff.getEmail());
          msg.setText("A new C3PR account has been created for you.\n"
                  + "Your username is follows:\n"
                  + "Username: " + staff.getEmail()
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
//        for (ContactMechanism cm : staff.getContactMechanisms()) {
//            if (cm.getType().equals(ContactMechanismType.EMAIL)) {
//                try {
//                    SimpleMailMessage msg = new SimpleMailMessage(
//                                    this.accountCreatedTemplateMessage);
//                    msg.setTo(cm.getValue());
//                    msg.setText("An account has been created for you.\n" + " Username:"
//                                    + cm.getValue() + " Password:" + staff.getLastName() + ""
//                                    + "\n -c3pr admin");
//                    log.debug("Trying to send user account confirmation email");
//                    this.mailSender.send(msg);
//                }
//                catch (MailException e) {
//                    log.debug("Could not send email due to  " + e.getMessage());
//                    // just log it for now
//                }
//
//            }
//        }
    }

    public MailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public SimpleMailMessage getAccountCreatedTemplateMessage() {
        return accountCreatedTemplateMessage;
    }

    public void setAccountCreatedTemplateMessage(SimpleMailMessage accountCreatedTemplateMessage) {
        this.accountCreatedTemplateMessage = accountCreatedTemplateMessage;
    }
}
