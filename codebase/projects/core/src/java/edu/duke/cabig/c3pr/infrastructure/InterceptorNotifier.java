package edu.duke.cabig.c3pr.infrastructure;

import gov.nih.nci.cabig.ctms.audit.ChainedInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Interceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/*
 * @author Vinay Gangoli
 * @Date   07/23/2008
 * 
 * We have a chainedInterceptor in the applicationContext-core which has gridInterceptor as the only 
 * parameter. Now the Rules framework loads after core and needs to add the NotificationInterceptor 
 * to the chainedInterceptor. However if we added it as a property in the appContext-core that would
 * be introducing a core dependency on rules which we wanted to avoid. 
 * Hence this class was created. This class runs after appContext initialization and adds all Interceptors 
 * (those that extend EmptyInterceptor) that have been loaded to the ChainedInterceptor, thereby resolving 
 * the dependency issue.
 * 
 * This class implements the appListener which provides a callback "onApplicationEvent" after applicationContext 
 * Initialization. It also implements AppContextAware so the appContext is available in the callback. 
 * 
 */
public class InterceptorNotifier implements ApplicationListener, ApplicationContextAware {
    
    private ApplicationContext applicationContext;

    /*
     * ContextRefreshedEvent is a standard Spring provided event which is published on appContext initialiation; 
     * meaning when all the spring beans are loaded.
     * 
     * this method looks for this event and then crates an array of all the EmptyInterceptors 
     * (like NotificationInterceptor and GridInterceptor)and replaces the one in chainedInterceptor with it. 
     * 
     * NOTE: This overriddes the existing set of interceptors present in the ChainedInterceptor.
     * 
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    public void onApplicationEvent(ApplicationEvent evt) {
        if (evt instanceof ContextRefreshedEvent) {
        	 Map<String, Interceptor> emptyInterceptorMap = applicationContext.getBeansOfType(EmptyInterceptor.class);

             Set<String> set = emptyInterceptorMap.keySet();
             List<Interceptor> interceptorList = new ArrayList<Interceptor>();
             for (String str : set) {
                 interceptorList.add(emptyInterceptorMap.get(str));
             }

             Map<String, Interceptor> chainedInterceptorMap = applicationContext.getBeansOfType(ChainedInterceptor.class);
             Set<String> cSet = chainedInterceptorMap.keySet();
             for (String str : cSet) {
                 ((ChainedInterceptor)chainedInterceptorMap.get(str)).setInterceptors(interceptorList.toArray(new Interceptor[0]));
             }
        }
    }

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
