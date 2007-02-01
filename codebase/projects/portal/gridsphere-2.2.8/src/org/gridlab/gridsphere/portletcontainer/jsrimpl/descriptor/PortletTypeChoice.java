/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: PortletTypeChoice.java,v 1.1.1.1 2007-02-01 20:50:46 kherm Exp $
 */

package org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class PortletTypeChoice.
 * 
 * @version $Revision: 1.1.1.1 $ $Date: 2007-02-01 20:50:46 $
 */
public class PortletTypeChoice implements java.io.Serializable {


    //--------------------------/
    //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _portletTypeChoiceSequence
     */
    private org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletTypeChoiceSequence _portletTypeChoiceSequence;

    /**
     * Field _portletInfo
     */
    private org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletInfo _portletInfo;


    //----------------/
    //- Constructors -/
    //----------------/

    public PortletTypeChoice() {
        super();
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.PortletTypeChoice()


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'portletInfo'.
     * 
     * @return the value of field 'portletInfo'.
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletInfo getPortletInfo() {
        return this._portletInfo;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.PortletInfo getPortletInfo()

    /**
     * Returns the value of field 'portletTypeChoiceSequence'.
     * 
     * @return the value of field 'portletTypeChoiceSequence'.
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletTypeChoiceSequence getPortletTypeChoiceSequence() {
        return this._portletTypeChoiceSequence;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.PortletTypeChoiceSequence getPortletTypeChoiceSequence()

    /**
     * Method isValid
     */
    public boolean isValid() {
        try {
            validate();
        } catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * Method marshal
     *
     * @param out
     */
    public void marshal(java.io.Writer out)
            throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {

        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     *
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
            throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {

        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Sets the value of field 'portletInfo'.
     * 
     * @param portletInfo the value of field 'portletInfo'.
     */
    public void setPortletInfo(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletInfo portletInfo) {
        this._portletInfo = portletInfo;
    } //-- void setPortletInfo(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.PortletInfo)

    /**
     * Sets the value of field 'portletTypeChoiceSequence'.
     *
     * @param portletTypeChoiceSequence the value of field
     *                                  'portletTypeChoiceSequence'.
     */
    public void setPortletTypeChoiceSequence(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletTypeChoiceSequence portletTypeChoiceSequence) {
        this._portletTypeChoiceSequence = portletTypeChoiceSequence;
    } //-- void setPortletTypeChoiceSequence(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.PortletTypeChoiceSequence)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
            throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletTypeChoice) Unmarshaller.unmarshal(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletTypeChoice.class, reader);
    } //-- java.lang.Object unmarshal(java.io.Reader) 

    /**
     * Method validate
     */
    public void validate()
            throws org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
