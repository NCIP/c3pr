/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: PreferencesValidatorType.java,v 1.1.1.1 2007-02-01 20:50:48 kherm Exp $
 */

package org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * The portlet-name element contains the canonical name of the
 * portlet. Each portlet name is unique within the portlet
 * application.
 * Used in: portlet, portlet-mapping
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2007-02-01 20:50:48 $
 */
public class PreferencesValidatorType implements java.io.Serializable {


    //--------------------------/
    //- Class/Member Variables -/
    //--------------------------/

    /**
     * internal content storage
     */
    private String _content = "";


    //----------------/
    //- Constructors -/
    //----------------/

    public PreferencesValidatorType() {
        super();
        setContent("");
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.PortletNameType()


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'content'. The field 'content'
     * has the following description: internal content storage
     *
     * @return the value of field 'content'.
     */
    public String getContent() {
        return this._content;
    } //-- java.lang.String getContent()

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
     * Sets the value of field 'content'. The field 'content' has
     * the following description: internal content storage
     *
     * @param content the value of field 'content'.
     */
    public void setContent(String content) {
        this._content = content;
    } //-- void setContent(java.lang.String)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static Object unmarshal(java.io.Reader reader)
            throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (PreferencesValidatorType) Unmarshaller.unmarshal(PreferencesValidatorType.class, reader);
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
