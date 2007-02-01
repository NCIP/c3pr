/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: PortletAppType.java,v 1.1.1.1 2007-02-01 20:50:39 kherm Exp $
 */

package org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

import java.util.ArrayList;

/**
 * Class PortletAppType.
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2007-02-01 20:50:39 $
 */
public class PortletAppType implements java.io.Serializable {


    //--------------------------/
    //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _version
     */
    private java.lang.String _version;

    /**
     * Field _id
     */
    private java.lang.String _id;

    /**
     * Field _portletList
     */
    private java.util.ArrayList _portletList;

    /**
     * Field _customPortletModeList
     */
    private java.util.ArrayList _customPortletModeList;

    /**
     * Field _customWindowStateList
     */
    private java.util.ArrayList _customWindowStateList;

    /**
     * Field _userAttributeList
     */
    private java.util.ArrayList _userAttributeList;

    /**
     * Field _securityConstraintList
     */
    private java.util.ArrayList _securityConstraintList;


    //----------------/
    //- Constructors -/
    //----------------/

    public PortletAppType() {
        super();
        _portletList = new ArrayList();
        _customPortletModeList = new ArrayList();
        _customWindowStateList = new ArrayList();
        _userAttributeList = new ArrayList();
        _securityConstraintList = new ArrayList();
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.PortletAppType()


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Method addCustomPortletMode
     *
     * @param vCustomPortletMode
     */
    public void addCustomPortletMode(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomPortletMode vCustomPortletMode)
            throws java.lang.IndexOutOfBoundsException {
        _customPortletModeList.add(vCustomPortletMode);
    } //-- void addCustomPortletMode(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.CustomPortletMode)

    /**
     * Method addCustomPortletMode
     *
     * @param index
     * @param vCustomPortletMode
     */
    public void addCustomPortletMode(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomPortletMode vCustomPortletMode)
            throws java.lang.IndexOutOfBoundsException {
        _customPortletModeList.add(index, vCustomPortletMode);
    } //-- void addCustomPortletMode(int, org.gridlab.gridsphere.portletcontainer.jsr.descriptor.CustomPortletMode)

    /**
     * Method addCustomWindowState
     *
     * @param vCustomWindowState
     */
    public void addCustomWindowState(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomWindowState vCustomWindowState)
            throws java.lang.IndexOutOfBoundsException {
        _customWindowStateList.add(vCustomWindowState);
    } //-- void addCustomWindowState(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.CustomWindowState)

    /**
     * Method addCustomWindowState
     *
     * @param index
     * @param vCustomWindowState
     */
    public void addCustomWindowState(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomWindowState vCustomWindowState)
            throws java.lang.IndexOutOfBoundsException {
        _customWindowStateList.add(index, vCustomWindowState);
    } //-- void addCustomWindowState(int, org.gridlab.gridsphere.portletcontainer.jsr.descriptor.CustomWindowState)

    /**
     * Method addPortlet
     *
     * @param vPortlet
     */
    public void addPortlet(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletDefinition vPortlet)
            throws java.lang.IndexOutOfBoundsException {
        _portletList.add(vPortlet);
    } //-- void addPortlet(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Portlet)

    /**
     * Method addPortlet
     *
     * @param index
     * @param vPortlet
     */
    public void addPortlet(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletDefinition vPortlet)
            throws java.lang.IndexOutOfBoundsException {
        _portletList.add(index, vPortlet);
    } //-- void addPortlet(int, org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Portlet)

    /**
     * Method addSecurityConstraint
     *
     * @param vSecurityConstraint
     */
    public void addSecurityConstraint(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityConstraint vSecurityConstraint)
            throws java.lang.IndexOutOfBoundsException {
        _securityConstraintList.add(vSecurityConstraint);
    } //-- void addSecurityConstraint(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.SecurityConstraint)

    /**
     * Method addSecurityConstraint
     *
     * @param index
     * @param vSecurityConstraint
     */
    public void addSecurityConstraint(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityConstraint vSecurityConstraint)
            throws java.lang.IndexOutOfBoundsException {
        _securityConstraintList.add(index, vSecurityConstraint);
    } //-- void addSecurityConstraint(int, org.gridlab.gridsphere.portletcontainer.jsr.descriptor.SecurityConstraint)

    /**
     * Method addUserAttribute
     *
     * @param vUserAttribute
     */
    public void addUserAttribute(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.UserAttribute vUserAttribute)
            throws java.lang.IndexOutOfBoundsException {
        _userAttributeList.add(vUserAttribute);
    } //-- void addUserAttribute(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.UserAttribute)

    /**
     * Method addUserAttribute
     *
     * @param index
     * @param vUserAttribute
     */
    public void addUserAttribute(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.UserAttribute vUserAttribute)
            throws java.lang.IndexOutOfBoundsException {
        _userAttributeList.add(index, vUserAttribute);
    } //-- void addUserAttribute(int, org.gridlab.gridsphere.portletcontainer.jsr.descriptor.UserAttribute)

    /**
     * Method clearCustomPortletMode
     */
    public void clearCustomPortletMode() {
        _customPortletModeList.clear();
    } //-- void clearCustomPortletMode()

    /**
     * Method clearCustomWindowState
     */
    public void clearCustomWindowState() {
        _customWindowStateList.clear();
    } //-- void clearCustomWindowState()

    /**
     * Method clearPortlet
     */
    public void clearPortlet() {
        _portletList.clear();
    } //-- void clearPortlet()

    /**
     * Method clearSecurityConstraint
     */
    public void clearSecurityConstraint() {
        _securityConstraintList.clear();
    } //-- void clearSecurityConstraint()

    /**
     * Method clearUserAttribute
     */
    public void clearUserAttribute() {
        _userAttributeList.clear();
    } //-- void clearUserAttribute()

    /**
     * Method enumerateCustomPortletMode
     */
    public java.util.Enumeration enumerateCustomPortletMode() {
        return new org.exolab.castor.util.IteratorEnumeration(_customPortletModeList.iterator());
    } //-- java.util.Enumeration enumerateCustomPortletMode()

    /**
     * Method enumerateCustomWindowState
     */
    public java.util.Enumeration enumerateCustomWindowState() {
        return new org.exolab.castor.util.IteratorEnumeration(_customWindowStateList.iterator());
    } //-- java.util.Enumeration enumerateCustomWindowState()

    /**
     * Method enumeratePortlet
     */
    public java.util.Enumeration enumeratePortlet() {
        return new org.exolab.castor.util.IteratorEnumeration(_portletList.iterator());
    } //-- java.util.Enumeration enumeratePortlet()

    /**
     * Method enumerateSecurityConstraint
     */
    public java.util.Enumeration enumerateSecurityConstraint() {
        return new org.exolab.castor.util.IteratorEnumeration(_securityConstraintList.iterator());
    } //-- java.util.Enumeration enumerateSecurityConstraint()

    /**
     * Method enumerateUserAttribute
     */
    public java.util.Enumeration enumerateUserAttribute() {
        return new org.exolab.castor.util.IteratorEnumeration(_userAttributeList.iterator());
    } //-- java.util.Enumeration enumerateUserAttribute()

    /**
     * Method getCustomPortletMode
     *
     * @param index
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomPortletMode getCustomPortletMode(int index)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _customPortletModeList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomPortletMode) _customPortletModeList.get(index);
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.CustomPortletMode getCustomPortletMode(int)

    /**
     * Method getCustomPortletMode
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomPortletMode[] getCustomPortletMode() {
        int size = _customPortletModeList.size();
        org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomPortletMode[] mArray = new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomPortletMode[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomPortletMode) _customPortletModeList.get(index);
        }
        return mArray;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.CustomPortletMode[] getCustomPortletMode()

    /**
     * Method getCustomPortletModeCount
     */
    public int getCustomPortletModeCount() {
        return _customPortletModeList.size();
    } //-- int getCustomPortletModeCount()

    /**
     * Method getCustomWindowState
     *
     * @param index
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomWindowState getCustomWindowState(int index)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _customWindowStateList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomWindowState) _customWindowStateList.get(index);
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.CustomWindowState getCustomWindowState(int)

    /**
     * Method getCustomWindowState
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomWindowState[] getCustomWindowState() {
        int size = _customWindowStateList.size();
        org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomWindowState[] mArray = new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomWindowState[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomWindowState) _customWindowStateList.get(index);
        }
        return mArray;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.CustomWindowState[] getCustomWindowState()

    /**
     * Method getCustomWindowStateCount
     */
    public int getCustomWindowStateCount() {
        return _customWindowStateList.size();
    } //-- int getCustomWindowStateCount()

    /**
     * Returns the value of field 'id'.
     *
     * @return the value of field 'id'.
     */
    public java.lang.String getId() {
        return this._id;
    } //-- java.lang.String getId()

    /**
     * Method getPortlet
     *
     * @param index
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletDefinition getPortlet(int index)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _portletList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletDefinition) _portletList.get(index);
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Portlet getPortlet(int)

    /**
     * Method getPortlet
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletDefinition[] getPortlet() {
        int size = _portletList.size();
        org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletDefinition[] mArray = new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletDefinition[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletDefinition) _portletList.get(index);
        }
        return mArray;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Portlet[] getPortlet()

    /**
     * Method getPortletCount
     */
    public int getPortletCount() {
        return _portletList.size();
    } //-- int getPortletCount()

    /**
     * Method getSecurityConstraint
     *
     * @param index
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityConstraint getSecurityConstraint(int index)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _securityConstraintList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityConstraint) _securityConstraintList.get(index);
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.SecurityConstraint getSecurityConstraint(int)

    /**
     * Method getSecurityConstraint
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityConstraint[] getSecurityConstraint() {
        int size = _securityConstraintList.size();
        org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityConstraint[] mArray = new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityConstraint[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityConstraint) _securityConstraintList.get(index);
        }
        return mArray;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.SecurityConstraint[] getSecurityConstraint()

    /**
     * Method getSecurityConstraintCount
     */
    public int getSecurityConstraintCount() {
        return _securityConstraintList.size();
    } //-- int getSecurityConstraintCount()

    /**
     * Method getUserAttribute
     *
     * @param index
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.UserAttribute getUserAttribute(int index)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _userAttributeList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.UserAttribute) _userAttributeList.get(index);
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.UserAttribute getUserAttribute(int)

    /**
     * Method getUserAttribute
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.UserAttribute[] getUserAttribute() {
        int size = _userAttributeList.size();
        org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.UserAttribute[] mArray = new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.UserAttribute[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.UserAttribute) _userAttributeList.get(index);
        }
        return mArray;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.UserAttribute[] getUserAttribute()

    /**
     * Method getUserAttributeCount
     */
    public int getUserAttributeCount() {
        return _userAttributeList.size();
    } //-- int getUserAttributeCount()

    /**
     * Returns the value of field 'version'.
     *
     * @return the value of field 'version'.
     */
    public java.lang.String getVersion() {
        return this._version;
    } //-- java.lang.String getVersion()

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
     * Method removeCustomPortletMode
     *
     * @param vCustomPortletMode
     */
    public boolean removeCustomPortletMode(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomPortletMode vCustomPortletMode) {
        boolean removed = _customPortletModeList.remove(vCustomPortletMode);
        return removed;
    } //-- boolean removeCustomPortletMode(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.CustomPortletMode)

    /**
     * Method removeCustomWindowState
     *
     * @param vCustomWindowState
     */
    public boolean removeCustomWindowState(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomWindowState vCustomWindowState) {
        boolean removed = _customWindowStateList.remove(vCustomWindowState);
        return removed;
    } //-- boolean removeCustomWindowState(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.CustomWindowState)

    /**
     * Method removePortlet
     *
     * @param vPortlet
     */
    public boolean removePortlet(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletDefinition vPortlet) {
        boolean removed = _portletList.remove(vPortlet);
        return removed;
    } //-- boolean removePortlet(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Portlet)

    /**
     * Method removeSecurityConstraint
     *
     * @param vSecurityConstraint
     */
    public boolean removeSecurityConstraint(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityConstraint vSecurityConstraint) {
        boolean removed = _securityConstraintList.remove(vSecurityConstraint);
        return removed;
    } //-- boolean removeSecurityConstraint(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.SecurityConstraint)

    /**
     * Method removeUserAttribute
     *
     * @param vUserAttribute
     */
    public boolean removeUserAttribute(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.UserAttribute vUserAttribute) {
        boolean removed = _userAttributeList.remove(vUserAttribute);
        return removed;
    } //-- boolean removeUserAttribute(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.UserAttribute)

    /**
     * Method setCustomPortletMode
     *
     * @param index
     * @param vCustomPortletMode
     */
    public void setCustomPortletMode(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomPortletMode vCustomPortletMode)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _customPortletModeList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _customPortletModeList.set(index, vCustomPortletMode);
    } //-- void setCustomPortletMode(int, org.gridlab.gridsphere.portletcontainer.jsr.descriptor.CustomPortletMode)

    /**
     * Method setCustomPortletMode
     *
     * @param customPortletModeArray
     */
    public void setCustomPortletMode(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomPortletMode[] customPortletModeArray) {
        //-- copy array
        _customPortletModeList.clear();
        for (int i = 0; i < customPortletModeArray.length; i++) {
            _customPortletModeList.add(customPortletModeArray[i]);
        }
    } //-- void setCustomPortletMode(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.CustomPortletMode)

    /**
     * Method setCustomWindowState
     *
     * @param index
     * @param vCustomWindowState
     */
    public void setCustomWindowState(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomWindowState vCustomWindowState)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _customWindowStateList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _customWindowStateList.set(index, vCustomWindowState);
    } //-- void setCustomWindowState(int, org.gridlab.gridsphere.portletcontainer.jsr.descriptor.CustomWindowState)

    /**
     * Method setCustomWindowState
     *
     * @param customWindowStateArray
     */
    public void setCustomWindowState(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomWindowState[] customWindowStateArray) {
        //-- copy array
        _customWindowStateList.clear();
        for (int i = 0; i < customWindowStateArray.length; i++) {
            _customWindowStateList.add(customWindowStateArray[i]);
        }
    } //-- void setCustomWindowState(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.CustomWindowState)

    /**
     * Sets the value of field 'id'.
     *
     * @param id the value of field 'id'.
     */
    public void setId(java.lang.String id) {
        this._id = id;
    } //-- void setId(java.lang.String)

    /**
     * Method setPortlet
     *
     * @param index
     * @param vPortlet
     */
    public void setPortlet(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletDefinition vPortlet)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _portletList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _portletList.set(index, vPortlet);
    } //-- void setPortlet(int, org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Portlet)

    /**
     * Method setPortlet
     *
     * @param portletArray
     */
    public void setPortlet(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletDefinition[] portletArray) {
        //-- copy array
        _portletList.clear();
        for (int i = 0; i < portletArray.length; i++) {
            _portletList.add(portletArray[i]);
        }
    } //-- void setPortlet(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Portlet)

    /**
     * Method setSecurityConstraint
     *
     * @param index
     * @param vSecurityConstraint
     */
    public void setSecurityConstraint(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityConstraint vSecurityConstraint)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _securityConstraintList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _securityConstraintList.set(index, vSecurityConstraint);
    } //-- void setSecurityConstraint(int, org.gridlab.gridsphere.portletcontainer.jsr.descriptor.SecurityConstraint)

    /**
     * Method setSecurityConstraint
     *
     * @param securityConstraintArray
     */
    public void setSecurityConstraint(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityConstraint[] securityConstraintArray) {
        //-- copy array
        _securityConstraintList.clear();
        for (int i = 0; i < securityConstraintArray.length; i++) {
            _securityConstraintList.add(securityConstraintArray[i]);
        }
    } //-- void setSecurityConstraint(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.SecurityConstraint)

    /**
     * Method setUserAttribute
     *
     * @param index
     * @param vUserAttribute
     */
    public void setUserAttribute(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.UserAttribute vUserAttribute)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _userAttributeList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _userAttributeList.set(index, vUserAttribute);
    } //-- void setUserAttribute(int, org.gridlab.gridsphere.portletcontainer.jsr.descriptor.UserAttribute)

    /**
     * Method setUserAttribute
     *
     * @param userAttributeArray
     */
    public void setUserAttribute(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.UserAttribute[] userAttributeArray) {
        //-- copy array
        _userAttributeList.clear();
        for (int i = 0; i < userAttributeArray.length; i++) {
            _userAttributeList.add(userAttributeArray[i]);
        }
    } //-- void setUserAttribute(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.UserAttribute)

    /**
     * Sets the value of field 'version'.
     *
     * @param version the value of field 'version'.
     */
    public void setVersion(java.lang.String version) {
        this._version = version;
    } //-- void setVersion(java.lang.String)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
            throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletAppType) Unmarshaller.unmarshal(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletAppType.class, reader);
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
