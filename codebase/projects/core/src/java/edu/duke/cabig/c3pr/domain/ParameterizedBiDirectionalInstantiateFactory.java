package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.domain.DomainObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.collections15.functors.InstantiateFactory;

public class ParameterizedBiDirectionalInstantiateFactory<T extends DomainObject> extends InstantiateFactory<T>{
	private Object parent;
	private String biDirectionalPropertyName;
	private Class<T> classToInstantiate;
	
	public ParameterizedBiDirectionalInstantiateFactory(Class<T> classToInstantiate, Object parent) {
		super(classToInstantiate);
		this.classToInstantiate=classToInstantiate;
		this.parent=parent;
		this.biDirectionalPropertyName=parent.getClass().getSimpleName();
	}

	public ParameterizedBiDirectionalInstantiateFactory(Class<T> classToInstantiate, Object parent, String biDirectionalPropertyName) {
		super(classToInstantiate);
		this.classToInstantiate=classToInstantiate;
		this.parent=parent;
		this.biDirectionalPropertyName=biDirectionalPropertyName;
	}

	@Override
	public T create() {
		// TODO Auto-generated method stub
		T object=null;
		try {
			object= (T)classToInstantiate.newInstance();
			Method m=object.getClass().getMethod(getSetterString(this.biDirectionalPropertyName), new Class[]{this.parent.getClass()});
			m.invoke(object, new Object[]{parent});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public String getSetterString(String property){
		return "set"+property;
	}

	public Class getClassToInstantiate() {
		return classToInstantiate;
	}

	public void setClassToInstantiate(Class classToInstantiate) {
		this.classToInstantiate = classToInstantiate;
	}
}
