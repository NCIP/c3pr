/*
 * Copyright 2002-2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.duke.cabig.c3pr.tools;

import java.util.Properties;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.FactoryBeanNotInitializedException;
import org.springframework.beans.factory.InitializingBean;

/**
 * FactoryBean that can be used to return a value from a property object based on a key. This
 * factory bean can be used in situations that you nead to assign a bean property or constructor
 * argument as a result of a properties object lookup.
 * <p>
 * There are more ussage patterns:<br>
 * <li>Setting the properties object as an bean reference and key as separated properties</li>
 * <li>Setting the properties object as a bean name and key as separate properties</li>
 * <li>Setting the properties object as a bean name and key as one property.</li>
 * <li>Setting the properties object as a bean name and key as bean name.</li>
 * </p>
 * <p>
 * Setting the properties object as an bean reference and key as separated properties:
 * <li>set <code>targetObject</code> (@see #setTargetBeanName(String))</li>
 * <li>set <code>key</code> (@see #setKey(String))</li>
 * </p>
 * <p>
 * Setting the properties object as a bean name and key as separate properties:
 * <li>set <code>targetBeanName</code> (@see #setTargetBeanName(String))</li>
 * <li>set <code>key</code> (@see #setKey(String))</li>
 * </p>
 * <p>
 * Setting the properties object as a bean name and key as one:
 * <li>set <code>propertyPath</code> (@see #setStaticName(String))</li>
 * </p>
 * <p>
 * Setting the properties object as a bean name and key as bean name. A simple usage is based on
 * bean name.
 * 
 * <pre class="code">
 * &lt;bean id=&quot;myProperties&quot;
 *   class=&quot;org.springframework.beans.factory.config.PropertiesFactoryBean&quot;&gt;
 *   &lt;property name=&quot;properties&quot;&gt;
 *     &lt;props&gt;
 *       &lt;prop key=&quot;aKey&quot;&gt;aValue&lt;/prop&gt;
 *     &lt;/props&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * &lt;bean name=&quot;myProperties[aKey]&quot;
 *   class=&quot;org.springframework.beans.factory.config.PropertiesGetterFactoryBean&quot;/&gt;
 * &lt;bean id=&quot;myBean&quot; class=&quot;..&quot;&gt;
 *   &lt;property name=&quot;aProperty&quot; ref=&quot;myProperties[aKey]&quot;&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * </p>
 * 
 * @author Alin Dreghiciu
 * @since July 24, 2006
 */
public class PropertiesGetterFactoryBean implements FactoryBean, BeanNameAware, InitializingBean,
                BeanFactoryAware {

    /**
     * Holds the property object to be used to retrieve the value.
     */
    private Properties targetObject;

    /**
     * Holds the property object bean name to be used to retrieve the value.
     */
    private String targetBeanName;

    /**
     * Holds the key of the value to be retrieved.
     */
    private String key;

    /**
     * Holds the default object to be retrieved in case that the key is not found.
     */
    private String defaultValue;

    /**
     * Holds the bean name.
     */
    private String beanName;

    /**
     * Holds the static name.
     */
    private String propertyPath;

    /**
     * Bean factory aware factory bean.
     */
    private BeanFactory beanFactory;

    /*
     * {@inheritDoc}
     */
    public void afterPropertiesSet() {

        if (beanName != null && propertyPath == null) {
            propertyPath = beanName;
        }

        if (targetObject == null) {
            if (targetBeanName == null && propertyPath != null) {
                // parse propertyPath to target bean name && key
                int startOfKey = propertyPath.lastIndexOf('[');
                int endOfKey = propertyPath.lastIndexOf(']');
                if (startOfKey == -1 || endOfKey == -1 || startOfKey > endOfKey - 2) {
                    throw new IllegalArgumentException("Invalid syntax."
                                    + "propertyPath must follow the pattern targetBeanName[key]: "
                                    + "e.g. 'database.properties[connection.username]'");
                }
                key = propertyPath.substring(startOfKey + 1, endOfKey);
                targetBeanName = propertyPath.substring(0, startOfKey);
            }
            if (targetBeanName == null || targetBeanName.trim().equals("")) {
                throw new IllegalArgumentException("TargetBeanName can not be null or empty."
                                + " Set one of targetBeanName, propertyPath or bean name");
            }
        }

        if (key == null || key.trim().equals("")) {
            throw new IllegalArgumentException("Key can not be null empty."
                            + " Set one of key, propertyPath or bean name");
        }

        // if we have no target object retrive it based on target bean name
        if (targetObject == null) {
            targetObject = (Properties) beanFactory.getBean(targetBeanName, Properties.class);
        }

    }

    /*
     * {@inheritDoc}
     */
    public Object getObject() {
        if (targetObject == null) {
            throw new FactoryBeanNotInitializedException();
        }
        return targetObject.getProperty(key, defaultValue);
    }

    /*
     * {@inheritDoc}
     */
    public Class getObjectType() {
        return String.class;
    }

    /*
     * {@inheritDoc}
     */
    public boolean isSingleton() {
        return true;
    }

    /**
     * Returns the bean name.
     * 
     * @return Returns the bean name.
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * Sets the bean name. <br/> The bean name will be interpreted as propertyPath & key pattern if
     * the targetObject/key or propertyPath are not set.
     * 
     * @param beanName
     *                The bean name to set.
     */
    public void setBeanName(final String beanName) {
        this.beanName = beanName;
    }

    /**
     * Return the defualt value.
     * 
     * @return Returns the defaultValue.
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Seths the default value to be returned if the properties object does not contain a valye of
     * the key.
     * 
     * @param defaultValue
     *                The default value to set.
     */
    public void setDefaultValue(final String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Return the key of the value to be retrieved.
     * 
     * @return Returns the key.
     */
    public String getKey() {
        return key;
    }

    /**
     * The key of the value to be retrieved.
     * 
     * @param key
     *                The key to set.
     */
    public void setKey(final String key) {
        this.key = key;
    }

    /**
     * Returns the propertyPath object that contains the value to be retieved.
     * 
     * @return Returns the targetObject.
     */
    public Properties getTargetObject() {
        return targetObject;
    }

    /**
     * Sets the propertyPath object that contains the value to be retrieved.
     * 
     * @param targetObject
     *                The propertyPath object to set.
     */
    public void setTargetObject(final Properties targetObject) {
        this.targetObject = targetObject;
    }

    /**
     * Returns the propertyPath that will be retrieved.
     * 
     * @return Returns the propertyPath.
     */
    public String getPropertyPath() {
        return propertyPath;
    }

    /**
     * Sets the propertyPath to be retrieved.<br/> The value should follow the pattern
     * beanName[key] where bean name is the name of a bean in the context that maps to a
     * propertyPath object and key is the key of the propertyPath to be retrieved.
     * 
     * @param propertyPath
     *                The propertyPath to set.
     */
    public void setPropertyPath(final String propertyPath) {
        this.propertyPath = propertyPath;
    }

    /*
     * {@inheritDoc}
     */
    public void setBeanFactory(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Gets the target bean name.
     * 
     * @return the bean name.
     */
    public String getTargetBeanName() {
        return targetBeanName;
    }

    /**
     * Sets the target bean name of the bean that resolves to a porperty object.
     * 
     * @param targetBeanName
     *                the bean name
     */
    public void setTargetBeanName(final String targetBeanName) {
        this.targetBeanName = targetBeanName;
    }

}
