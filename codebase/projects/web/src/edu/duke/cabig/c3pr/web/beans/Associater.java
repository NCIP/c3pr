package edu.duke.cabig.c3pr.web.beans;

import java.lang.reflect.Method;

public interface Associater {
	public void associate(Object entity1, Object entity2, Method method) throws Exception;
}
