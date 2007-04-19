package edu.duke.cabig.c3pr.utils.web.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.utils.web.CustomMethodInvocater;

public class ObjectGraphBasedEditor extends PropertyEditorSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ObjectGraphBasedEditor.class);

	public static final String DATA_TYPE_STRING = "string";

	public static final String DATA_TYPE_INT = "int";

	private Object commandObject;

	private String objectPath;

	private String defaultComparatorProperty="id";

	private String defaultComparatorType = DATA_TYPE_INT;
	
	private List<String> methodsToInvoke= new ArrayList<String>();

	public ObjectGraphBasedEditor(Object commandObject, String objectPath) {
		this.commandObject = commandObject;
		this.objectPath = objectPath;
		setMethodsToInvoke(objectPath);
		if (logger.isDebugEnabled()) {
			logger.debug("CustomCommandEditor(Object, String) - " + this); //$NON-NLS-1$
		}
	}

	public ObjectGraphBasedEditor(Object commandObject, String objectPath,
			String defaultComparatorProperty) {
		this.commandObject = commandObject;
		this.objectPath = objectPath;
		this.defaultComparatorProperty = defaultComparatorProperty;
		setMethodsToInvoke(objectPath);
		if (logger.isDebugEnabled()) {
			logger.debug("CustomCommandEditor(Object, String, String) - " + this); //$NON-NLS-1$
		}
	}

	public ObjectGraphBasedEditor(Object commandObject, String objectPath,
			String defaultComparatorProperty, String defaultArgumentType) {
		this.commandObject = commandObject;
		this.objectPath = objectPath;
		this.defaultComparatorProperty = defaultComparatorProperty;
		this.defaultComparatorType = defaultArgumentType;
		if (logger.isDebugEnabled()) {
			logger.debug("CustomCommandEditor(Object, String, String, String) - " + this); //$NON-NLS-1$
		}
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Object value=null;
		if (logger.isDebugEnabled()) {
			logger.debug("setAsText(String) - Setting property"); //$NON-NLS-1$
		}
		try {
			value=getObjectFromGraphWithText(text);
		} catch (Exception e) {
			logger.error("setAsText(String)", e); //$NON-NLS-1$
		}

		setValue(value);
	}
	
	public Object getObjectFromGraphWithText(String text) throws Exception{
		Object value=null;
		Object targetObject = getObjectFromGraph();
		if (targetObject instanceof Collection) {
			Iterator it=((Collection)targetObject).iterator();
			while(it.hasNext()){
				Object element=it.next();
				CustomMethodInvocater methodInvocater=CustomMethodInvocater.parse(element,getGetterString(defaultComparatorProperty));
				Object methodReturn=methodInvocater.invoke();
				if(equal(methodReturn,text)){
					value=element;
					break;
				}
			}
		}else{
			CustomMethodInvocater methodInvocater=CustomMethodInvocater.parse(targetObject,getGetterString(defaultComparatorProperty));
			Object methodReturn=methodInvocater.invoke();
			if(equal(methodReturn,text)){
				value=methodReturn;
			}
		}
		return value;
	}
	
	public Object getObjectFromGraph() throws Exception{
		Object targetObject = commandObject;
		for(int i=0 ; i<methodsToInvoke.size() ; i++){
			CustomMethodInvocater methodInvocater=CustomMethodInvocater.parse(targetObject,methodsToInvoke.get(i));
			targetObject=methodInvocater.invoke();
		}
		return targetObject;
	}

	public void setMethodsToInvoke(String path) {
		if(path==null)
			return;
		if(path.equals(""))
			return;
		String[] properties=path.split("\\.");
		if (logger.isDebugEnabled()) {
			logger.debug("setMethodsToInvoke(String) - there are " + properties.length + " properties in " + path + ":"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		for(int i=0 ; i<properties.length ; i++){
			String property=properties[i];
			if (logger.isDebugEnabled()) {
				logger.debug("setMethodsToInvoke(String) - " + property); //$NON-NLS-1$
			}
			methodsToInvoke.add(getGetterString(property));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("setMethodsToInvoke(String)"); //$NON-NLS-1$
		}
	}

	private boolean equal(Object obj, String text){
		if(defaultComparatorType.equalsIgnoreCase(DATA_TYPE_INT)){
			if(((Integer)obj).intValue()==Integer.parseInt(text))
				return true;
			else
				return false;
		}
		if(defaultComparatorType.equalsIgnoreCase(DATA_TYPE_STRING)){
			if(((String)obj).equalsIgnoreCase(text))
				return true;
			else
				return false;
		}
		return false;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer("---Command PropertyEditor--\n");
		sb.append("commandObject:"+commandObject.getClass().getName()+"\n");
		sb.append("objectPath:"+objectPath+"\n");
		sb.append("defaultComparatorProperty:"+defaultComparatorProperty+"\n");
		sb.append("defaultComparatorType:"+defaultComparatorType+"\n");
		sb.append("methodsToInvoke:");
		for(int i=0 ; i<methodsToInvoke.size() ; i++){
			sb.append(methodsToInvoke.get(i)+", ");
		}
		sb.append("\n");
		sb.append("sample getter for property friends and argument John of type String:"+getGetterString("friends",new String[]{"John"})+"\n");
		return sb.toString();
	}

	public String getGetterString(String property){
		return getGetterString(property, new String[]{});
	}
	public String getGetterString(String property,String[] args){
		String argString="";
		for(int i=0 ; i<args.length ; i++){
			argString+=(i==0?"":",")+args[i];
		}
		return "get"+new String(""+property.charAt(0)).toUpperCase()+property.substring(1,property.length())+"("+argString+")";
	}
}
