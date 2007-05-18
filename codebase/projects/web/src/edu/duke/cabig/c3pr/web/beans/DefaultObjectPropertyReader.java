package edu.duke.cabig.c3pr.web.beans;

import org.springframework.beans.PropertyAccessorUtils;

public class DefaultObjectPropertyReader extends AbstractObjectPropertyReader {

	public DefaultObjectPropertyReader(Object currentObject, String objectPath) {
		super(currentObject, objectPath);
		// TODO Auto-generated constructor stub
	}
	public DefaultObjectPropertyReader(Object currentObject) {
		super(currentObject);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object handleNullPropertyValue(Object nestedObject, int key)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public Object getPropertyValueFromPath() throws Exception{
		int pos = PropertyAccessorUtils.getFirstNestedPropertySeparatorIndex(getPropertyPath());
		// handle nested properties recursively
		if (pos > -1) {
			String nestedProperty = getPropertyPath().substring(0, pos);
			String nestedPath = getPropertyPath().substring(pos + 1);
			Object obj = getPropertyValue(getPropertyDescriptor(nestedProperty));
			if (getKeys() != null) {
				// apply indexes and map keys
				obj = applyKeys(obj);
			}
			if (continueTraverse) {
				DefaultObjectPropertyReader nestedDefaultObjectPropertyReader = new DefaultObjectPropertyReader(
						obj, nestedPath);
				return nestedDefaultObjectPropertyReader.getPropertyValueFromPath();
			}
		}else{
			Object obj = getPropertyValue(getPropertyDescriptor(getPropertyPath()));
			if (getKeys() != null) {
				// apply indexes and map keys
				obj = applyKeys(obj);
			}
			return obj;
		}
		return null;
	}
}
