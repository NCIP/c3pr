package edu.duke.cabig.c3pr.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.collections15.functors.InstantiateFactory;

public class BiDirectionalInstantiateFactory<T> extends InstantiateFactory<T>{
	private Object parent;
	private String biDirectionalPropertyName;
	
	public BiDirectionalInstantiateFactory(Class<T> classToInstantiate, Object parent) {
		super(classToInstantiate);
		this.parent=parent;
		this.biDirectionalPropertyName=parent.getClass().getSimpleName();
	}

	public BiDirectionalInstantiateFactory(Class<T> classToInstantiate, Object parent, String biDirectionalPropertyName) {
		super(classToInstantiate);
		this.parent=parent;
		this.biDirectionalPropertyName=biDirectionalPropertyName;
	}

	@Override
	public T create() {
		// TODO Auto-generated method stub
		T object= super.create();
		try {
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
		}
		return object;
	}
	
	public String getSetterString(String property){
		return "set"+property;
	}
}
