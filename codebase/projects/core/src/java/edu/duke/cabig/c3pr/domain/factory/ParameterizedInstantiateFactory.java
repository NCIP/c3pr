package edu.duke.cabig.c3pr.domain.factory;

import gov.nih.nci.cabig.ctms.domain.DomainObject;

import org.apache.commons.collections15.Factory;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Domain objects.
 */
public class ParameterizedInstantiateFactory<T extends DomainObject> implements Factory<T> {

    /** The class to instantiate. */
    private Class<? extends T> classToInstantiate;

    /**
     * Instantiates a new parameterized instantiate factory.
     */
    public ParameterizedInstantiateFactory() {
        super();
    }

    /**
     * Instantiates a new parameterized instantiate factory.
     * 
     * @param classToInstantiate the class to instantiate
     */
    public ParameterizedInstantiateFactory(Class<? extends T> classToInstantiate) {
        super();
        this.classToInstantiate = classToInstantiate;
    }

    /* (non-Javadoc)
     * @see org.apache.commons.collections15.Factory#create()
     */
    public T create() {
        try {
            return (T) classToInstantiate.newInstance();
        }
        catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the class to instantiate.
     * 
     * @return the class to instantiate
     */
    public Class<? extends T> getClassToInstantiate() {
        return classToInstantiate;
    }

    /**
     * Sets the class to instantiate.
     * 
     * @param classToInstantiate the new class to instantiate
     */
    public void setClassToInstantiate(Class<? extends T> classToInstantiate) {
        this.classToInstantiate = classToInstantiate;
    }

}
