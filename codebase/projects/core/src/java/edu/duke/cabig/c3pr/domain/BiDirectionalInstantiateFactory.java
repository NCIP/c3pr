/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.lang.reflect.Method;

import org.apache.commons.collections15.functors.InstantiateFactory;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating BiDirectionalInstantiate objects.
 */
public class BiDirectionalInstantiateFactory<T> extends InstantiateFactory<T> {
    
    /** The parent. */
    private Object parent;

    /** The bi directional property name. */
    private String biDirectionalPropertyName;

    /** The params. */
    private Class[] params;

    /**
     * Instantiates a new bi directional instantiate factory.
     * 
     * @param classToInstantiate the class to instantiate
     * @param parent the parent
     */
    public BiDirectionalInstantiateFactory(Class<T> classToInstantiate, Object parent) {
        this(classToInstantiate, parent, parent.getClass().getSimpleName(), new Class[] { parent
                        .getClass() });
    }

    /**
     * Instantiates a new bi directional instantiate factory.
     * 
     * @param classToInstantiate the class to instantiate
     * @param parent the parent
     * @param biDirectionalPropertyName the bi directional property name
     */
    public BiDirectionalInstantiateFactory(Class<T> classToInstantiate, Object parent,
                    String biDirectionalPropertyName) {
        super(classToInstantiate);
        this.parent = parent;
        this.biDirectionalPropertyName = biDirectionalPropertyName;
    }

    /**
     * Instantiates a new bi directional instantiate factory.
     * 
     * @param classToInstantiate the class to instantiate
     * @param parent the parent
     * @param biDirectionalPropertyName the bi directional property name
     * @param paramTypes the param types
     */
    public BiDirectionalInstantiateFactory(Class<T> classToInstantiate, Object parent,
                    String biDirectionalPropertyName, Class... paramTypes) {
        super(classToInstantiate);
        this.parent = parent;
        this.biDirectionalPropertyName = biDirectionalPropertyName;
        this.params = paramTypes;
    }

    /* (non-Javadoc)
     * @see org.apache.commons.collections15.functors.InstantiateFactory#create()
     */
    @Override
    public T create() {
        T object = super.create();
        try {
            Method m = object.getClass().getMethod(getSetterString(this.biDirectionalPropertyName),
                            params);
            m.invoke(object, new Object[] { parent });
        }
        catch (Exception e) {
            throw new RuntimeException(e);
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
     * Gets the parent.
     * 
     * @return the parent
     * 
     * @throws Exception the exception
     */
    public Object getParent() throws Exception{
    	return this.parent;
    }
}
