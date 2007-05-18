package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestDataBinder;

import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AbstractTabbedFlowFormController;
import edu.duke.cabig.c3pr.web.beans.DefaultObjectPropertyReader;
import edu.duke.cabig.c3pr.web.beans.HttpServletLazyCollectionInitializer;
import edu.duke.cabig.c3pr.web.beans.HttpServletLazyCollectionInitializerImpl;
import edu.duke.cabig.c3pr.web.beans.ObjectPropertyReader;

/**
 * @author Kruttik
 *
 */
public abstract class AbstractTabbedFlowRowManagerController extends AbstractTabbedFlowFormController implements LazyListBindable {
	String DELETED_ROW_INDICATOR = "_deletedRow";
    @Override
    protected void onBind(HttpServletRequest request, Object command) throws Exception {
    	Enumeration params=request.getParameterNames();
    	Map<String, List<String>> deletionMap=new HashMap<String, List<String>>();
    	while(params.hasMoreElements()){
    		String param=(String)params.nextElement();
    		if(param.startsWith(DELETED_ROW_INDICATOR)){
    			String[] parts=param.split("-");
    			List indexes=deletionMap.get(parts[1]);
    			if(indexes==null)
    				indexes=new ArrayList();
    			indexes.add(parts[2]);
    			deletionMap.put(parts[1], indexes);
    		}
    	}
    	if(deletionMap.size()>0){
    		deleteRows(command,deletionMap);
    	}
    }
    
    @Override
    protected ServletRequestDataBinder createBinder(HttpServletRequest request, Object command) throws Exception {
    	// TODO Auto-generated method stub
    	ServletRequestDataBinder binder= super.createBinder(request, command);
    	if(isFormSubmission(request)||isBindOnNewForm()){
    		HttpServletLazyCollectionInitializer customLazyCollectionInitializer=getLazyCollectionInitializer(command);
    		initLazyListBinder(request, customLazyCollectionInitializer);
    		try {
				customLazyCollectionInitializer.lazilyInitializeCollections(request);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
    	}
    	return binder;
    }
    
    
    public HttpServletLazyCollectionInitializer getLazyCollectionInitializer(Object object){
    	return new HttpServletLazyCollectionInitializerImpl(object);
    }
    
    public void deleteRows(Object object,Map<String, List<String>> map) throws Exception {
    	ObjectPropertyReader objectPropertyReader=new DefaultObjectPropertyReader(object);
    	Iterator keys=map.keySet().iterator();
    	while(keys.hasNext()){
    		String property=(String)keys.next();
	    	Object obj=objectPropertyReader.getPropertyValueFromPath(property);
	    	if (obj instanceof List) {
				List list = (List) obj;
				List<String> indexes=map.get(property);
				List temp=new ArrayList();
				for(int i=0 ; i<list.size() ; i++){
					if(!indexes.contains(i+""))
						temp.add(list.get(i));
				}
				while(list.size()>0){
					list.remove(0);
				}
				list.addAll(temp);
			}
    	}
    }
    
    public void initLazyListBinder(HttpServletRequest request, HttpServletLazyCollectionInitializer lazyCollectionInitializer) throws Exception {
    	// TODO Auto-generated method stub
    	
    }
}
