/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: BannerSettings.java,v 1.1.1.1 2007-02-01 20:07:19 kherm Exp $
 */

package org.gridlab.gridsphere.extras.portlets.banner;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class BannerSettings.
 * 
 * @version $Revision: 1.1.1.1 $ $Date: 2007-02-01 20:07:19 $
 */
public class BannerSettings implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _bannerTitle
     */
    private java.lang.String _bannerTitle;

    /**
     * Field _bannerFile
     */
    private java.lang.String _bannerFile;


      //----------------/
     //- Constructors -/
    //----------------/

    public BannerSettings() {
        super();
    } //-- org.gridlab.gridsphere.extras.portlets.banner.BannerSettings()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'bannerFile'.
     * 
     * @return the value of field 'bannerFile'.
     */
    public java.lang.String getBannerFile()
    {
        return this._bannerFile;
    } //-- java.lang.String getBannerFile() 

    /**
     * Returns the value of field 'bannerTitle'.
     * 
     * @return the value of field 'bannerTitle'.
     */
    public java.lang.String getBannerTitle()
    {
        return this._bannerTitle;
    } //-- java.lang.String getBannerTitle() 

    /**
     * Method isValid
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
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
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Sets the value of field 'bannerFile'.
     * 
     * @param bannerFile the value of field 'bannerFile'.
     */
    public void setBannerFile(java.lang.String bannerFile)
    {
        this._bannerFile = bannerFile;
    } //-- void setBannerFile(java.lang.String) 

    /**
     * Sets the value of field 'bannerTitle'.
     * 
     * @param bannerTitle the value of field 'bannerTitle'.
     */
    public void setBannerTitle(java.lang.String bannerTitle)
    {
        this._bannerTitle = bannerTitle;
    } //-- void setBannerTitle(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.gridlab.gridsphere.extras.portlets.banner.BannerSettings) Unmarshaller.unmarshal(org.gridlab.gridsphere.extras.portlets.banner.BannerSettings.class, reader);
    } //-- java.lang.Object unmarshal(java.io.Reader) 

    /**
     * Method validate
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
