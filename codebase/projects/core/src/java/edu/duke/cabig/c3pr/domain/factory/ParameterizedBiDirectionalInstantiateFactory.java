/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain.factory;

import gov.nih.nci.cabig.ctms.domain.DomainObject;

import java.lang.reflect.Method;

import org.apache.commons.collections15.functors.InstantiateFactory;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating ParameterizedBiDirectionalInstantiate objects.
 */
public class ParameterizedBiDirectionalInstantiateFactory<T extends DomainObject> extends
                InstantiateFactory<T> {
    
    /** The parent. */
    protected Object parent;

    /** The bi directional property name. */
    private String biDirectionalPropertyName;

    /** The class to instantiate. */
    private Class<T> classToInstantiate;
    
    private Class biDirectionalClass; 

    /**
     * Instantiates a new parameterized bi directional instantiate factory.
     * 
     * @param classToInstantiate the class to instantiate
     * @param parent the parent
     */
    public ParameterizedBiDirectionalInstantiateFactory(Class<T> classToInstantiate, Object parent) {
        this(classToInstantiate, parent, parent.getClass().getSimpleName());
    }

    /**
     * Instantiates a new parameterized bi directional instantiate factory.
     * 
     * @param classToInstantiate the class to instantiate
     * @param parent the parent
     * @param biDirectionalPropertyName the bi directional property name
     */
    public ParameterizedBiDirectionalInstantiateFactory(Class<T> classToInstantiate, Object parent,
                    String biDirectionalPropertyName) {
    	this(classToInstantiate, parent, biDirectionalPropertyName, parent.getClass());
    }

    /**
     * Instantiates a new parameterized bi directional instantiate factory.
     * 
     * @param classToInstantiate the class to instantiate
     * @param parent the parent
     * @param biDirectionalPropertyName the bi directional property name
     */
    public ParameterizedBiDirectionalInstantiateFactory(Class<T> classToInstantiate, Object parent,
                    String biDirectionalPropertyName, Class biDirectionalClassName) {
        super(classToInstantiate);
        this.classToInstantiate = classToInstantiate;
        this.parent = parent;
        this.biDirectionalPropertyName = biDirectionalPropertyName;
        this.biDirectionalClass = biDirectionalClassName;
    }

    
    
    /* (non-Javadoc)
     * @see org.apache.commons.collections15.functors.InstantiateFactory#create()
     */
    @Override
    public T create() {
        T object = null;
        try {
            object = (T) classToInstantiate.newInstance();
            Method m = object.getClass().getMethod(getSetterString(this.biDirectionalPropertyName),
                            new Class[] { biDirectionalClass });
            m.invoke(object, new Object[] { parent });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * Gets the setter string.
     * 
     * @param property the property
     * 
     * @return the setter string
     */
    public String getSetterString(String property) {
        return "set" + property;
    }

    /**
     * Gets the class to instantiate.
     * 
     * @return the class to instantiate
     */
    public Class getClassToInstantiate() {
        return classToInstantiate;
    }

    /**
     * Sets the class to instantiate.
     * 
     * @param classToInstantiate the new class to instantiate
     */
    public void setClassToInstantiate(Class classToInstantiate) {
        this.classToInstantiate = classToInstantiate;
    }
}
