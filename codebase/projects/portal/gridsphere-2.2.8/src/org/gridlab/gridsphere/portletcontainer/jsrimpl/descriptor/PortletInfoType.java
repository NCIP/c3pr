/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: PortletInfoType.java,v 1.1.1.1 2007-02-01 20:50:43 kherm Exp $
 */

package org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class PortletInfoType.
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2007-02-01 20:50:43 $
 */
public class PortletInfoType implements java.io.Serializable {


    //--------------------------/
    //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _id
     */
    private java.lang.String _id;

    /**
     * Field _title
     */
    private org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Title _title;

    /**
     * Field _shortTitle
     */
    private org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.ShortTitle _shortTitle;

    /**
     * Field _keywords
     */
    private org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Keywords _keywords;


    //----------------/
    //- Constructors -/
    //----------------/

    public PortletInfoType() {
        super();
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.PortletInfoType()


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'id'.
     *
     * @return the value of field 'id'.
     */
    public java.lang.String getId() {
        return this._id;
    } //-- java.lang.String getId()

    /**
     * Returns the value of field 'keywords'.
     *
     * @return the value of field 'keywords'.
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Keywords getKeywords() {
        return this._keywords;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Keywords getKeywords()

    /**
     * Returns the value of field 'shortTitle'.
     *
     * @return the value of field 'shortTitle'.
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.ShortTitle getShortTitle() {
        return this._shortTitle;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.ShortTitle getShortTitle()

    /**
     * Returns the value of field 'title'.
     *
     * @return the value of field 'title'.
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Title getTitle() {
        return this._title;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Title getTitle()

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
     * Sets the value of field 'id'.
     *
     * @param id the value of field 'id'.
     */
    public void setId(java.lang.String id) {
        this._id = id;
    } //-- void setId(java.lang.String)

    /**
     * Sets the value of field 'keywords'.
     *
     * @param keywords the value of field 'keywords'.
     */
    public void setKeywords(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Keywords keywords) {
        this._keywords = keywords;
    } //-- void setKeywords(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Keywords)

    /**
     * Sets the value of field 'shortTitle'.
     *
     * @param shortTitle the value of field 'shortTitle'.
     */
    public void setShortTitle(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.ShortTitle shortTitle) {
        this._shortTitle = shortTitle;
    } //-- void setShortTitle(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.ShortTitle)

    /**
     * Sets the value of field 'title'.
     *
     * @param title the value of field 'title'.
     */
    public void setTitle(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Title title) {
        this._title = title;
    } //-- void setTitle(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Title)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
            throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletInfoType) Unmarshaller.unmarshal(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletInfoType.class, reader);
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
