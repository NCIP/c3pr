package edu.duke.cabig.c3pr.domain;

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
		T object= super.create();
		try {
			Method m=object.getClass().getMethod(getSetterString(this.biDirectionalPropertyName), new Class[]{this.parent.getClass()});
			m.invoke(object, new Object[]{parent});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
			
		return object;
	}
	
	public String getSetterString(String property){
		return "set"+property;
	}
}
