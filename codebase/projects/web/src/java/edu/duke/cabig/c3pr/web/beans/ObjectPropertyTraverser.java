package edu.duke.cabig.c3pr.web.beans;

import org.springframework.beans.PropertyValues;

public interface ObjectPropertyTraverser extends ObjectPropertyReader {

    public void traversePropertyValues(PropertyValues pvs) throws Exception;

    public void traversePropertyPath() throws Exception;
}
