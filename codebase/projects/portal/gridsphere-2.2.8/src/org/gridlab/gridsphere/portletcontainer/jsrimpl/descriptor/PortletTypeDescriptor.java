/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: PortletTypeDescriptor.java,v 1.1.1.1 2007-02-01 20:50:47 kherm Exp $
 */

package org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.validators.StringValidator;

/**
 * Class PortletTypeDescriptor.
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2007-02-01 20:50:47 $
 */
public class PortletTypeDescriptor extends org.exolab.castor.xml.util.XMLClassDescriptorImpl {


    //--------------------------/
    //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field nsPrefix
     */
    private java.lang.String nsPrefix;

    /**
     * Field nsURI
     */
    private java.lang.String nsURI;

    /**
     * Field xmlName
     */
    private java.lang.String xmlName;

    /**
     * Field identity
     */
    private org.exolab.castor.xml.XMLFieldDescriptor identity;


    //----------------/
    //- Constructors -/
    //----------------/

    public PortletTypeDescriptor() {
        super();
        nsURI = "http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd";
        xmlName = "portletType";

        //-- set grouping compositor
        setCompositorAsSequence();
        org.exolab.castor.xml.util.XMLFieldDescriptorImpl desc = null;
        org.exolab.castor.xml.XMLFieldHandler handler = null;
        org.exolab.castor.xml.FieldValidator fieldValidator = null;
        //-- initialize attribute descriptors

        //-- _id
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(java.lang.String.class, "_id", "id", org.exolab.castor.xml.NodeType.Attribute);
        desc.setImmutable(true);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue(java.lang.Object object)
                    throws IllegalStateException {
                PortletDefinitionType target = (PortletDefinitionType) object;
                return target.getId();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    PortletDefinitionType target = (PortletDefinitionType) object;
                    target.setId((java.lang.String) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }

            public java.lang.Object newInstance(java.lang.Object parent) {
                return null;
            }
        });
        desc.setHandler(handler);
        addFieldDescriptor(desc);

        //-- validation code for: _id
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        { //-- local scope
            StringValidator typeValidator = new StringValidator();
            typeValidator.setWhiteSpace("preserve");
            fieldValidator.setValidator(typeValidator);
        }
        desc.setValidator(fieldValidator);
        //-- initialize element descriptors

        //-- _descriptionList
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description.class, "_descriptionList", "description", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue(java.lang.Object object)
                    throws IllegalStateException {
                PortletDefinitionType target = (PortletDefinitionType) object;
                return target.getDescription();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    PortletDefinitionType target = (PortletDefinitionType) object;
                    target.addDescription((org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }

            public java.lang.Object newInstance(java.lang.Object parent) {
                return new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description();
            }
        });
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd");
        desc.setMultivalued(true);
        addFieldDescriptor(desc);

        //-- validation code for: _descriptionList
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(0);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _portletName
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletName.class, "_portletName", "portlet-name", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue(java.lang.Object object)
                    throws IllegalStateException {
                PortletDefinitionType target = (PortletDefinitionType) object;
                return target.getPortletName();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    PortletDefinitionType target = (PortletDefinitionType) object;
                    target.setPortletName((org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletName) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }

            public java.lang.Object newInstance(java.lang.Object parent) {
                return new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletName();
            }
        });
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);

        //-- validation code for: _portletName
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _displayNameList
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.DisplayName.class, "_displayNameList", "display-name", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue(java.lang.Object object)
                    throws IllegalStateException {
                PortletDefinitionType target = (PortletDefinitionType) object;
                return target.getDisplayName();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    PortletDefinitionType target = (PortletDefinitionType) object;
                    target.addDisplayName((org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.DisplayName) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }

            public java.lang.Object newInstance(java.lang.Object parent) {
                return new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.DisplayName();
            }
        });
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd");
        desc.setMultivalued(true);
        addFieldDescriptor(desc);

        //-- validation code for: _displayNameList
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(0);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _portletClass
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(java.lang.String.class, "_portletClass", "portlet-class", org.exolab.castor.xml.NodeType.Element);
        desc.setImmutable(true);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue(java.lang.Object object)
                    throws IllegalStateException {
                PortletDefinitionType target = (PortletDefinitionType) object;
                return target.getPortletClass();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    PortletDefinitionType target = (PortletDefinitionType) object;
                    target.setPortletClass((org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletClass) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }

            public java.lang.Object newInstance(java.lang.Object parent) {
                return null;
            }
        });
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);

        //-- validation code for: _portletClass
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
            StringValidator typeValidator = new StringValidator();
            typeValidator.setWhiteSpace("collapse");
            fieldValidator.setValidator(typeValidator);
        }
        desc.setValidator(fieldValidator);
        //-- _initParamList
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.InitParam.class, "_initParamList", "init-param", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue(java.lang.Object object)
                    throws IllegalStateException {
                PortletDefinitionType target = (PortletDefinitionType) object;
                return target.getInitParam();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    PortletDefinitionType target = (PortletDefinitionType) object;
                    target.addInitParam((org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.InitParam) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }

            public java.lang.Object newInstance(java.lang.Object parent) {
                return new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.InitParam();
            }
        });
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd");
        desc.setMultivalued(true);
        addFieldDescriptor(desc);

        //-- validation code for: _initParamList
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(0);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _expirationCache
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.ExpirationCache.class, "_expirationCache", "expiration-cache", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue(java.lang.Object object)
                    throws IllegalStateException {
                PortletDefinitionType target = (PortletDefinitionType) object;
                return target.getExpirationCache();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    PortletDefinitionType target = (PortletDefinitionType) object;
                    target.setExpirationCache((org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.ExpirationCache) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }

            public java.lang.Object newInstance(java.lang.Object parent) {
                return new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.ExpirationCache();
            }
        });
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd");
        desc.setMultivalued(false);
        addFieldDescriptor(desc);

        //-- validation code for: _expirationCache
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _supportsList
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Supports.class, "_supportsList", "supports", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue(java.lang.Object object)
                    throws IllegalStateException {
                PortletDefinitionType target = (PortletDefinitionType) object;
                return target.getSupports();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    PortletDefinitionType target = (PortletDefinitionType) object;
                    target.addSupports((org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Supports) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }

            public java.lang.Object newInstance(java.lang.Object parent) {
                return new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Supports();
            }
        });
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd");
        desc.setRequired(true);
        desc.setMultivalued(true);
        addFieldDescriptor(desc);

        //-- validation code for: _supportsList
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _supportedLocaleList
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SupportedLocale.class, "_supportedLocaleList", "supported-locale", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue(java.lang.Object object)
                    throws IllegalStateException {
                PortletDefinitionType target = (PortletDefinitionType) object;
                return target.getSupportedLocale();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    PortletDefinitionType target = (PortletDefinitionType) object;
                    target.addSupportedLocale((org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SupportedLocale) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }

            public java.lang.Object newInstance(java.lang.Object parent) {
                return new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SupportedLocale();
            }
        });
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd");
        desc.setMultivalued(true);
        addFieldDescriptor(desc);

        //-- validation code for: _supportedLocaleList
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(0);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);

        //-- _portletPreferences
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletPreferences.class, "_portletPreferences", "portlet-preferences", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue(java.lang.Object object)
                    throws IllegalStateException {
                PortletDefinitionType target = (PortletDefinitionType) object;
                return target.getPortletPreferences();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    PortletDefinitionType target = (PortletDefinitionType) object;
                    target.setPortletPreferences((org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletPreferences) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }

            public java.lang.Object newInstance(java.lang.Object parent) {
                return new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletPreferences();
            }
        });
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd");
        desc.setMultivalued(false);
        addFieldDescriptor(desc);

        //-- validation code for: _portletPreferences
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _securityRoleRefList
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityRoleRef.class, "_securityRoleRefList", "security-role-ref", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue(java.lang.Object object)
                    throws IllegalStateException {
                PortletDefinitionType target = (PortletDefinitionType) object;
                return target.getSecurityRoleRef();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    PortletDefinitionType target = (PortletDefinitionType) object;
                    target.addSecurityRoleRef((org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityRoleRef) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }

            public java.lang.Object newInstance(java.lang.Object parent) {
                return new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityRoleRef();
            }
        });
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd");
        desc.setMultivalued(true);
        addFieldDescriptor(desc);

        //-- validation code for: _securityRoleRefList
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(0);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.PortletTypeDescriptor()


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Method getAccessMode
     */
    public org.exolab.castor.mapping.AccessMode getAccessMode() {
        return null;
    } //-- org.exolab.castor.mapping.AccessMode getAccessMode()

    /**
     * Method getExtends
     */
    public org.exolab.castor.mapping.ClassDescriptor getExtends() {
        return null;
    } //-- org.exolab.castor.mapping.ClassDescriptor getExtends()

    /**
     * Method getIdentity
     */
    public org.exolab.castor.mapping.FieldDescriptor getIdentity() {
        return identity;
    } //-- org.exolab.castor.mapping.FieldDescriptor getIdentity()

    /**
     * Method getJavaClass
     */
    public java.lang.Class getJavaClass() {
        return org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletDefinitionType.class;
    } //-- java.lang.Class getJavaClass()

    /**
     * Method getNameSpacePrefix
     */
    public java.lang.String getNameSpacePrefix() {
        return nsPrefix;
    } //-- java.lang.String getNameSpacePrefix()

    /**
     * Method getNameSpaceURI
     */
    public java.lang.String getNameSpaceURI() {
        return nsURI;
    } //-- java.lang.String getNameSpaceURI()

    /**
     * Method getValidator
     */
    public org.exolab.castor.xml.TypeValidator getValidator() {
        return this;
    } //-- org.exolab.castor.xml.TypeValidator getValidator()

    /**
     * Method getXMLName
     */
    public java.lang.String getXMLName() {
        return xmlName;
    } //-- java.lang.String getXMLName()

}
