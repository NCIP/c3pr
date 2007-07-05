package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.domain.DomainObject;

import org.apache.commons.collections15.Factory;

public class  ParameterizedInstantiateFactory<T extends DomainObject> implements
		Factory<T> {

	private  Class<? extends T> classToInstantiate;

	public T create() {
		try {
			return (T) classToInstantiate.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public Class<? extends T> getClassToInstantiate() {
		return classToInstantiate;
	}

	public void  setClassToInstantiate(Class<? extends T> classToInstantiate) {
		this.classToInstantiate = classToInstantiate;
	}

}
