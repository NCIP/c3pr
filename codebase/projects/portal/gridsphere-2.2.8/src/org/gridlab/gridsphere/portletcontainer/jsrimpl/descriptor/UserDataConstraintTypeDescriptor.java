/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: UserDataConstraintTypeDescriptor.java,v 1.1.1.1 2007-02-01 20:50:56 kherm Exp $
 */

package org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.validators.StringValidator;

/**
 * Class UserDataConstraintTypeDescriptor.
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2007-02-01 20:50:56 $
 */
public class UserDataConstraintTypeDescriptor extends org.exolab.castor.xml.util.XMLClassDescriptorImpl {


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

    public UserDataConstraintTypeDescriptor() {
        super();
        nsURI = "http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd";
        xmlName = "user-data-constraintType";

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
                UserDataConstraintType target = (UserDataConstraintType) object;
                return target.getId();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    UserDataConstraintType target = (UserDataConstraintType) object;
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
                UserDataConstraintType target = (UserDataConstraintType) object;
                return target.getDescription();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    UserDataConstraintType target = (UserDataConstraintType) object;
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
        //-- _transportGuarantee
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.types.TransportGuaranteeType.class, "_transportGuarantee", "transport-guarantee", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue(java.lang.Object object)
                    throws IllegalStateException {
                UserDataConstraintType target = (UserDataConstraintType) object;
                return target.getTransportGuarantee();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    UserDataConstraintType target = (UserDataConstraintType) object;
                    target.setTransportGuarantee((org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.types.TransportGuaranteeType) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }

            public java.lang.Object newInstance(java.lang.Object parent) {
                return null;
            }
        });
        desc.setHandler(new org.exolab.castor.xml.handlers.EnumFieldHandler(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.types.TransportGuaranteeType.class, handler));
        desc.setImmutable(true);
        desc.setNameSpaceURI("http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);

        //-- validation code for: _transportGuarantee
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.UserDataConstraintTypeDescriptor()


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
        return org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.UserDataConstraintType.class;
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
