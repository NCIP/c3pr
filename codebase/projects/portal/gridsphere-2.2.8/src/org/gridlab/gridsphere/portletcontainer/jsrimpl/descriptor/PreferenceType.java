/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: PreferenceType.java,v 1.1.1.1 2007-02-01 20:50:48 kherm Exp $
 */

package org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.types.ReadOnlyType;

import java.util.ArrayList;

/**
 * Persistent preference values that may be used for customization
 * and personalization by the portlet.
 * Used in: portlet-preferences
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2007-02-01 20:50:48 $
 */
public class PreferenceType implements java.io.Serializable {


    //--------------------------/
    //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _id
     */
    private java.lang.String _id;

    /**
     * Field _name
     */
    private org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Name _name;

    /**
     * Field _valueList
     */
    private java.util.ArrayList _valueList;

    /**
     * Field _readOnly
     */
    private org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.types.ReadOnlyType _readOnly;


    //----------------/
    //- Constructors -/
    //----------------/

    public PreferenceType() {
        super();
        _readOnly = new ReadOnlyType();
        _valueList = new ArrayList();
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.PreferenceType()


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Method addValue
     *
     * @param vValue
     */
    public void addValue(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Value vValue)
            throws java.lang.IndexOutOfBoundsException {
        _valueList.add(vValue);
    } //-- void addValue(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Value)

    /**
     * Method addValue
     *
     * @param index
     * @param vValue
     */
    public void addValue(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Value vValue)
            throws java.lang.IndexOutOfBoundsException {
        _valueList.add(index, vValue);
    } //-- void addValue(int, org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Value)

    /**
     * Method clearValue
     */
    public void clearValue() {
        _valueList.clear();
    } //-- void clearValue()

    /**
     * Method enumerateValue
     */
    public java.util.Enumeration enumerateValue() {
        return new org.exolab.castor.util.IteratorEnumeration(_valueList.iterator());
    } //-- java.util.Enumeration enumerateValue()

    /**
     * Returns the value of field 'id'.
     *
     * @return the value of field 'id'.
     */
    public java.lang.String getId() {
        return this._id;
    } //-- java.lang.String getId()

    /**
     * Returns the value of field 'name'.
     *
     * @return the value of field 'name'.
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Name getName() {
        return this._name;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Name getName()

    /**
     * Returns the value of field 'readOnly'.
     *
     * @return the value of field 'readOnly'.
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.types.ReadOnlyType getReadOnly() {
        return this._readOnly;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.types.ReadOnlyType getReadOnly()

    /**
     * Method getValue
     *
     * @param index
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Value getValue(int index)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _valueList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Value) _valueList.get(index);
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Value getValue(int)

    /**
     * Method getValue
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Value[] getValue() {
        int size = _valueList.size();
        org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Value[] mArray = new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Value[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Value) _valueList.get(index);
        }
        return mArray;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Value[] getValue()

    /**
     * Method getValueCount
     */
    public int getValueCount() {
        return _valueList.size();
    } //-- int getValueCount()

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
     * Method removeValue
     *
     * @param vValue
     */
    public boolean removeValue(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Value vValue) {
        boolean removed = _valueList.remove(vValue);
        return removed;
    } //-- boolean removeValue(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Value)

    /**
     * Sets the value of field 'id'.
     *
     * @param id the value of field 'id'.
     */
    public void setId(java.lang.String id) {
        this._id = id;
    } //-- void setId(java.lang.String)

    /**
     * Sets the value of field 'name'.
     *
     * @param name the value of field 'name'.
     */
    public void setName(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Name name) {
        this._name = name;
    } //-- void setName(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Name)

    /**
     * Sets the value of field 'readOnly'.
     *
     * @param readOnly the value of field 'readOnly'.
     */
    public void setReadOnly(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.types.ReadOnlyType readOnly) {
        this._readOnly = readOnly;
    } //-- void setReadOnly(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.types.ReadOnlyType)

    /**
     * Method setValue
     *
     * @param index
     * @param vValue
     */
    public void setValue(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Value vValue)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _valueList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _valueList.set(index, vValue);
    } //-- void setValue(int, org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Value)

    /**
     * Method setValue
     *
     * @param valueArray
     */
    public void setValue(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Value[] valueArray) {
        //-- copy array
        _valueList.clear();
        for (int i = 0; i < valueArray.length; i++) {
            _valueList.add(valueArray[i]);
        }
    } //-- void setValue(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Value)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
            throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PreferenceType) Unmarshaller.unmarshal(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PreferenceType.class, reader);
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
