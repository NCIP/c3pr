package edu.duke.cabig.c3pr.utils;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.File;
import javax.mail.MessagingException;
import java.io.*;
import java.util.*;
import javax.naming.*;
import javax.mail.internet.MimeBodyPart;


/**
 * A Class class.
 * <P>
 * @author Vinay Kumar
 */
public class EmailManager
{

  String[] to=null;
  String[] cc=null;
  String[] bcc=null;
  String from=null;
  String body=null;
  String subject=null;
  //String host = "corp.hp.com";
  String host = "mailfwd.nih.gov";
  String[] attachements=null;
  MimeBodyPart[] attachementBodys = null;

  public EmailManager() {
  }


  public void setHost(String host)
  {
    this.host=host;
  }

  public String getHost()
  {
    return host;
  }

  public void setTo(String to)
  {
    this.to = new String[1];
    this.to[0]=to;
  }

  public void setTo(String[] to)
  {
    this.to=to;
  }

  public void setCc(String cc)
  {
    this.cc = new String[1];
    this.cc[0]=cc;
  }

  public void setCc(String[] cc)
  {
    this.cc=cc;
  }

  public void setBcc(String bcc)
  {
    this.bcc = new String[1];
    this.bcc[0]=bcc;
  }

  public void setBcc(String[] bcc)
  {
    this.bcc=bcc;
  }

  public void setAttachements(String attachement)
  {
    this.attachements = new String[1];
    this.attachements[0]=attachement;
  }

  public void setAttachements(String[] attachements)
  {
    this.attachements=attachements;
  }

  public void setAttachementBodys(MimeBodyPart[] attachementBodys)
  {
    this.attachementBodys = attachementBodys;
  }

  public void setFrom(String from)
  {
    this.from=from;
  }

  public void setBody(String body)
  {
    this.body=body;
  }

  public void setSubject(String subject)
  {
    this.subject=subject;
  }

  private final MimeBodyPart getFileBodyPart(String filename)  throws Exception {
    MimeBodyPart mbp = new MimeBodyPart();
    try {
	    File file = new File(filename);
	    if (file.exists()) {
        DataSource attachment = new FileDataSource(file);
        mbp.setDataHandler(new DataHandler(attachment));
        mbp.setFileName(file.getName());
      } else {
          throw new Exception("File " + filename + " does not exist or " +
				       "the path to the file is incorrect.");
      }
    } catch(MessagingException e) {
	    throw new Exception("The file named by " + filename + " could not be"
				   + " attached.");
    }
    return mbp;
	}

  private final MimeBodyPart getUrlBodyPart(String url_path) throws Exception {

    MimeBodyPart mbp = new MimeBodyPart();

    try {
	    URL url = new URL(url_path);
	    mbp.setDataHandler(new DataHandler(url));
		  mbp.setFileName(url_path);

    }
    catch(MalformedURLException e) {
	    throw new Exception("The URL entered as an attachment was " +
			"incorrectly formatted please check it and try again.");
    }
    catch(MessagingException e) {
	    throw new Exception("The Resource named by " + url_path + " could not"
				   + " be attached.");
    }
    return mbp;
	}

  private final MimeBodyPart[] getAttachementBodyParts() throws Exception {
    if(attachements==null) {
      return null;
    }
    MimeBodyPart[] results = new MimeBodyPart[attachements.length];
    for(int i=0; i<attachements.length; i++) {
      String attachement = attachements[i];
      if(attachement.startsWith("http://")||attachement.startsWith("https://")) {
        results[i] = getUrlBodyPart(attachement);
      }
      else {
        results[i] = getFileBodyPart(attachement);
      }
    }
    return results;
  }

  public void sendMessage() throws Exception
  {
    Session session;
    boolean debug = false;

      Properties props = new Properties();
	    // set host to server
      props.put("mail.smtp.host", host);
	    // send to all legal addresses
      props.put("mail.smtp.sendpartial", "true");
	    // set notification
      props.put("mail.smtp.dsn.notify", "FAILURE");
	    // set amount of message to get returned
      props.put("mail.smtp.dsn.ret", "FULL");

      session = Session.getDefaultInstance(props, null);

    try
    {
	    // create a message
	    Message msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress(from));

      InternetAddress[] addresses;
      //set To:
      if(to != null ) {
        addresses = new InternetAddress[to.length];
        for(int i =0;i<to.length;i++)
        {
          InternetAddress address = new InternetAddress(to[i]);
	      //InternetAddress[] address = {new InternetAddress(to)};
          addresses[i] = address;
        }
	      msg.setRecipients(Message.RecipientType.TO, addresses);
      }

      //set Cc:
      if(cc != null) {
         addresses = new InternetAddress[cc.length];
         for(int i =0;i<cc.length;i++)
         {
           InternetAddress address = new InternetAddress(cc[i]);
	         addresses[i] = address;
         }
	      msg.setRecipients(Message.RecipientType.CC, addresses);
      }

      //set Bcc:
      if(bcc != null) {
         addresses = new InternetAddress[bcc.length];
         for(int i =0;i<bcc.length;i++)
         {
           InternetAddress address = new InternetAddress(bcc[i]);
	         addresses[i] = address;
         }
	      msg.setRecipients(Message.RecipientType.BCC, addresses);
      }

	    msg.setSubject(subject);
	    msg.setSentDate(new Date());
      //MutiPart Format
      Multipart mp = new MimeMultipart();
      MimeBodyPart mbp = new MimeBodyPart();
      //Set email body
      mbp.setText(body);
      mp.addBodyPart(mbp);

      //Set Attachements
      MimeBodyPart[] mbps = getAttachementBodyParts();
      if(mbps != null) {
       for(int i=0; i<mbps.length; i++) {
        mp.addBodyPart(mbps[i]);
       }
      }
      if(this.attachementBodys!=null) {
        for(int i=0; i<this.attachementBodys.length; i++) {
          mp.addBodyPart(this.attachementBodys[i]);
        }
      }
      msg.setContent(mp);

	    Transport.send(msg);
    }
    catch (MessagingException mex)
    {

      LogWriter logWriter = LogWriter.getInstance(); logWriter.log(LogWriter.SEVERE, mex.getMessage(), mex);
	    Exception ex = mex;
	    do
      {
		    if (ex instanceof SendFailedException)
        {
		      SendFailedException sfex = (SendFailedException)ex;
		      Address[] invalid = sfex.getInvalidAddresses();
		      if(invalid != null)
          {
            //System.out.println("    ** Invalid Addresses");
			      if(invalid != null)
            {
			        for(int i = 0; i < invalid.length; i++)
			 	        System.err.println("         " + invalid[i]);
			      }
		      }
		      Address[] validUnsent = sfex.getValidUnsentAddresses();
		      if(validUnsent != null)
          {
			      //System.out.println("    ** ValidUnsent Addresses");
			      if(validUnsent != null)
            {
			        for(int i = 0; i < validUnsent.length; i++)
			 	        System.err.println("         "+validUnsent[i]);
			      }
		      }
          Address[] validSent = sfex.getValidSentAddresses();
		      if(validSent != null)
          {
			      System.err.println("    ** ValidSent Addresses");
			      if(validSent != null)
            {
			        for(int i = 0; i < validSent.length; i++)
			 	        System.out.println("         "+validSent[i]);
      			}
		      } // end if validSent!=null
        } // end if ex instanceof SendFailedException

	 	    System.err.println();
	      } while ((ex = ((MessagingException)ex).getNextException())!= null);

        // Throw the Exception
        System.out.println("Another Exception");

	    } // end catch
    } // end sendMessage
  public static void main(String args[])
  {
    // This part changes for each process' main method because
    //  we need to know which class we are instantiating.
    //  We can't inherit main methods anyhow because they are static,
    //  so we have to write this out anyhow.
    try {
    EmailManager mailHandler = new EmailManager();
    // Test the process.  This part is the same for all processes.
    //String[] to = {"vinayk@hp.com", "jojela72@hotmail.com"};
    //String[] cc = {"vinayk@hp.com", "jojela72@hotmail.com"};
    String[] to = {"choij@mail.nih.gov", "kumarvi@mail.nih.gov"};
    String[] att = {"c:\\temp\\CDEBrowser_SearchResults.xml"};
    mailHandler.setHost("mailfwd.nih.gov");
    mailHandler.setTo(to);
    mailHandler.setFrom("kumarvi@mail.nih.gov");
    mailHandler.setAttachements(att);
    mailHandler.setSubject("test");
    mailHandler.setBody("This is a test");
    mailHandler.sendMessage();
    System.out.println("Successful");
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  } // end main
}
