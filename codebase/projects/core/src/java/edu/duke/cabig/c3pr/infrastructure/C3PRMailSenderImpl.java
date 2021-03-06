/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.infrastructure;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import edu.duke.cabig.c3pr.tools.Configuration;

public class C3PRMailSenderImpl extends JavaMailSenderImpl {
	
	private Configuration configuration ;
	
	@Required
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	@Override
	public void setJavaMailProperties(Properties javaMailProperties) {
		if (getProtocol().equalsIgnoreCase("smtps")) {
			javaMailProperties.put("mail.smtps.auth", configuration.get(Configuration.OUTGOING_MAIL_AUTH));
			javaMailProperties.put("mail.smtps.starttls.enable", "true");
			javaMailProperties.put("mail.smtps.debug", "true");
		}else if(getProtocol().equalsIgnoreCase("smtp")){
			javaMailProperties.put("mail.smtp.auth",  configuration.get(Configuration.OUTGOING_MAIL_AUTH));
		}
		super.setJavaMailProperties(javaMailProperties);
	}
	
	@Override
	public MimeMessage createMimeMessage() {
		return super.createMimeMessage();
	}
}
