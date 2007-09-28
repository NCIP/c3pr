package edu.duke.cabig.c3pr.domain;

import java.lang.reflect.Method;

import org.apache.commons.collections15.functors.InstantiateFactory;

public class BiDirectionalInstantiateFactory<T> extends InstantiateFactory<T>{
	private Object parent;
	private String biDirectionalPropertyName;
	private Class[] params;
	
	public BiDirectionalInstantiateFactory(Class<T> classToInstantiate, Object parent) {
		this(classToInstantiate,parent,parent.getClass().getSimpleName(),new Class[]{parent.getClass()});
	}

	public BiDirectionalInstantiateFactory(Class<T> classToInstantiate, Object parent, String biDirectionalPropertyName) {
		super(classToInstantiate);
		this.parent=parent;
		this.biDirectionalPropertyName=biDirectionalPropertyName;
	}
	
	public BiDirectionalInstantiateFactory(Class<T> classToInstantiate, Object parent, String biDirectionalPropertyName, Class... paramTypes) {
		super(classToInstantiate);
		this.parent=parent;
		this.biDirectionalPropertyName=biDirectionalPropertyName;
		this.params = paramTypes;
	}

	@Override
	public T create() {
		T object= super.create();
		try {
			Method m=object.getClass().getMethod(getSetterString(this.biDirectionalPropertyName),params );
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
