package edu.duke.cabig.c3pr.web.beans;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorUtils;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;

public class HttpServletLazyCollectionInitializerImpl extends AbstractObjectPropertyReader
                implements ObjectPropertyTraverser, HttpServletLazyCollectionInitializer,
                Associater {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
                    .getLogger(HttpServletLazyCollectionInitializerImpl.class);

    String NESTED_PROPERTY_TYPE_LIST = "ListType";

    String NESTED_PROPERTY_TYPE_SET = "SetType";

    String NESTED_PROPERTY_TYPE_ARRAY = "ArrayType";

    private Map<Class, String> registeredCollections = new HashMap<Class, String>();

    // implicit super constructors
    public HttpServletLazyCollectionInitializerImpl(Object currentObject) {
        super(currentObject);
    }

    public HttpServletLazyCollectionInitializerImpl(Object currentObject, String objectPath) {
        super(currentObject, objectPath);
    }

    public ObjectPropertyTraverser createObjectPropertyTraverser(String objectPath) {
        HttpServletLazyCollectionInitializerImpl nestedObjectPropertyEditor = new HttpServletLazyCollectionInitializerImpl(
                        getCurrentObject(), objectPath);
        nestedObjectPropertyEditor.setRegisteredCollections(this.registeredCollections);
        return nestedObjectPropertyEditor;
    }

    public ObjectPropertyTraverser createObjectPropertyTraverser(Object obj, String objectPath) {
        HttpServletLazyCollectionInitializerImpl nestedObjectPropertyEditor = new HttpServletLazyCollectionInitializerImpl(
                        obj, objectPath);
        nestedObjectPropertyEditor.setRegisteredCollections(this.registeredCollections);
        return nestedObjectPropertyEditor;
    }

    // methods implementaton of HttpServletLazyCollectionInitializer interface
    public void processLazyInitialization(Object object, int key, String collectionType)
                    throws Exception {
        if (collectionType.equals(NESTED_PROPERTY_TYPE_ARRAY)) {

        }
        else {
            laziliyInitialize(object, getField(), key);
        }
    }

    public void laziliyInitialize(Object collection, Field collectionField, int index)
                    throws Exception {
        if (collection instanceof List) {
            List list = (List) collection;
            Object newObject = createNewObject(collectionField);
            if (hasAssociation(newObject)) {
                associate(newObject, getCurrentObject(), getMethod(newObject, getCurrentObject()));
            }
            list.add(index, newObject);
        }
    }

    public void lazilyInitializeCollections(HttpServletRequest request) throws Exception {
        MutablePropertyValues mpv = new ServletRequestParameterPropertyValues(request);
        traversePropertyValues(mpv);
    }

    // utility methods
    public Field getField() {
        for (Field f : getCurrentObject().getClass().getDeclaredFields()) {
            if (f.getName().equals(getActualName())) return f;
        }
        return null;
    }

    public Object createNewObject(Field field) throws Exception {
        Type type = field.getGenericType();
        if (type instanceof ParameterizedType) {
            ParameterizedType pT = (ParameterizedType) type;
            Type objectType = pT.getActualTypeArguments()[0];
            Class objectClass = (Class) objectType;
            return objectClass.newInstance();
        }
        return null;
    }

    // abstract method implementation of abstract class AbstractObjectPropertyTraverser
    @Override
    public Object handleNullPropertyValue(Object value, int key) throws Exception {
        if (value == null) {
            continueTraverse = false;
            return null;
        }
        else if (value.getClass().isArray()) {
            continueTraverse = false;
            processLazyInitialization(value, key, NESTED_PROPERTY_TYPE_ARRAY);
            return null;
        }
        else if (value instanceof List) {
            continueTraverse = false;
            processLazyInitialization(value, key, NESTED_PROPERTY_TYPE_LIST);
            return null;
        }
        else if (value instanceof Set) {
            // Apply index to Iterator in case of a Set.
            continueTraverse = false;
            processLazyInitialization(value, key, NESTED_PROPERTY_TYPE_SET);
            return null;
        }
        return value;
    }

    public void associate(Object entity1, Object entity2, Method method) throws Exception {
        method.invoke(entity1, entity2);
    }

    public void addAssociator(Class c, String methodName) {
        registeredCollections.put(c, methodName);
    }

    public boolean hasAssociation(Object o) {
        if (registeredCollections.get(o.getClass()) != null) return true;
        return false;
    }

    public Method getMethod(Object o, Object param) throws Exception {
        return o.getClass().getMethod(registeredCollections.get(o.getClass()),
                        new Class[] { param.getClass() });
    }

    // implementation methods from ObjectPropertyTraverser interface
    public void traversePropertyPath() throws Exception {
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
                ObjectPropertyTraverser nestedObjectPropertyEditor = createObjectPropertyTraverser(
                                obj, nestedPath);
                nestedObjectPropertyEditor.traversePropertyPath();
            }
        }
    }

    public void traversePropertyValues(PropertyValues pvs) {
        PropertyValue[] pvArray = pvs.getPropertyValues();
        for (int i = 0; i < pvArray.length; i++) {
            ObjectPropertyTraverser nestedObjectPropertyEditor = createObjectPropertyTraverser(pvArray[i]
                            .getName());
            try {
                nestedObjectPropertyEditor.traversePropertyPath();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getPropertyValueFromPath() throws Exception {
        return null;
    }

    public Map<Class, String> getRegisteredCollections() {
        return registeredCollections;
    }

    public void setRegisteredCollections(Map<Class, String> registeredCollections) {
        this.registeredCollections = registeredCollections;
    }

}
