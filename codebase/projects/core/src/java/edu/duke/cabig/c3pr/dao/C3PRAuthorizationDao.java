package edu.duke.cabig.c3pr.dao;

import gov.nih.nci.logging.api.logger.hibernate.HibernateSessionFactoryHelper;
import gov.nih.nci.security.authorization.domainobjects.Application;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.dao.DIAuthorizationDao;
import gov.nih.nci.security.util.StringEncrypter;
import gov.nih.nci.security.util.StringUtilities;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

public class C3PRAuthorizationDao extends DIAuthorizationDao {
	
	static final Logger log = Logger.getLogger(C3PRAuthorizationDao.class);
	boolean isEncryptionEnabled = true ;
	private SessionFactory sessionFactory;
	
	@Override
	public User getUser(String loginName) {
		 	User user = null ;
	        User search = new User();
	        search.setLoginName(loginName);
	        Example example = Example.create(search).ignoreCase();
	        Session s = HibernateSessionFactoryHelper.getAuditSession(sessionFactory);
	        List list = s.createCriteria(User.class).add(example ).list();
	        if(list.size() != 0)
	        {
	            user = (User)list.get(0);
	        }
	        try
	        {
	            user = (User)performEncrytionDecryption(user, false);
	        }
	        catch(gov.nih.nci.security.util.StringEncrypter.EncryptionException e)
	        {
	            
	        }
	        try
	        {
	            s.close();
	        }
	        catch(Exception ex2)
	        {
	            if(log.isDebugEnabled())
	            {
	                log.debug((new StringBuilder()).append("Authorization|||getUser|Failure|Error in Closing Session |").append(ex2.getMessage()).toString());
	            }
	        }
	        return user;
	}
	
	   private Object performEncrytionDecryption(Object obj, boolean encrypt) throws gov.nih.nci.security.util.StringEncrypter.EncryptionException
    {
        if(obj instanceof User)
        {
            User user = (User)obj;
            if(isEncryptionEnabled && StringUtilities.initTrimmedString(user.getPassword()).length() > 0)
            {
                StringEncrypter stringEncrypter = new StringEncrypter();
                if(encrypt)
                {
                    user.setPassword(stringEncrypter.encrypt(user.getPassword().trim()));
                } else
                {
                    user.setPassword(stringEncrypter.decrypt(user.getPassword().trim()));
                }
            }
            return user;
        }
        if(obj instanceof Application)
        {
            Application application = (Application)obj;
            if(isEncryptionEnabled && StringUtilities.initTrimmedString(application.getDatabasePassword()).length() > 0)
            {
                StringEncrypter stringEncrypter = new StringEncrypter();
                if(encrypt)
                {
                    application.setDatabasePassword(stringEncrypter.encrypt(application.getDatabasePassword().trim()));
                } else
                {
                    application.setDatabasePassword(stringEncrypter.decrypt(application.getDatabasePassword().trim()));
                }
            }
            return application;
        } else
        {
            return obj;
        }
    }

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}}
