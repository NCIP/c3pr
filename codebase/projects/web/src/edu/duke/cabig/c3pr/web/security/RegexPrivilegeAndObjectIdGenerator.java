package edu.duke.cabig.c3pr.web.security;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public class RegexPrivilegeAndObjectIdGenerator extends
		AbstractPrivilegeAndObjectIdGenerator {

	private static final Log logger = LogFactory.getLog(RegexPrivilegeAndObjectIdGenerator.class);

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.web.security.AbstractPrivilegeAndObjectIdGenerator#getKeyValue(java.lang.Object)
	 */
	@Override
	protected String getKeyValue(Object object) {
		return (String)object;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.web.security.AbstractPrivilegeAndObjectIdGenerator#supports(java.lang.Object)
	 */
	@Override
	protected boolean supports(Object object) {
		return object instanceof String;
	}

	protected String getObjectPrivilegeString(String key){
		String objPrivStr = null;
		for(String pattern : getObjectPrivilegeMap().keySet()){
			if(key.matches(pattern)){
				objPrivStr = getObjectPrivilegeMap().get(pattern);
				break;
			}
		}

		logger.debug("############### Returning " + objPrivStr + " #################");

		return objPrivStr;
	}

	 

}
