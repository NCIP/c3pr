/**
 * SiteAcccrualReportType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class SiteAcccrualReportType  implements java.io.Serializable {
    private java.lang.String ctepId;
    private java.lang.String name;
    private java.lang.String streetAddress;
    private java.lang.String city;
    private java.lang.String stateCode;
    private java.lang.String postalCode;
    private java.lang.String countryCode;
    private java.lang.Double lat;
    private java.lang.Double _long;
    private gov.nih.nci.cabig.ccts.domain.AccrualType accrual;
    private gov.nih.nci.cabig.ccts.domain.StudyAccrualReportType[] studyAccrualReports;

    public SiteAcccrualReportType() {
    }

    public SiteAcccrualReportType(
           java.lang.Double _long,
           gov.nih.nci.cabig.ccts.domain.AccrualType accrual,
           java.lang.String city,
           java.lang.String countryCode,
           java.lang.String ctepId,
           java.lang.Double lat,
           java.lang.String name,
           java.lang.String postalCode,
           java.lang.String stateCode,
           java.lang.String streetAddress,
           gov.nih.nci.cabig.ccts.domain.StudyAccrualReportType[] studyAccrualReports) {
           this.ctepId = ctepId;
           this.name = name;
           this.streetAddress = streetAddress;
           this.city = city;
           this.stateCode = stateCode;
           this.postalCode = postalCode;
           this.countryCode = countryCode;
           this.lat = lat;
           this._long = _long;
           this.accrual = accrual;
           this.studyAccrualReports = studyAccrualReports;
    }


    /**
     * Gets the ctepId value for this SiteAcccrualReportType.
     * 
     * @return ctepId
     */
    public java.lang.String getCtepId() {
        return ctepId;
    }


    /**
     * Sets the ctepId value for this SiteAcccrualReportType.
     * 
     * @param ctepId
     */
    public void setCtepId(java.lang.String ctepId) {
        this.ctepId = ctepId;
    }


    /**
     * Gets the name value for this SiteAcccrualReportType.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this SiteAcccrualReportType.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the streetAddress value for this SiteAcccrualReportType.
     * 
     * @return streetAddress
     */
    public java.lang.String getStreetAddress() {
        return streetAddress;
    }


    /**
     * Sets the streetAddress value for this SiteAcccrualReportType.
     * 
     * @param streetAddress
     */
    public void setStreetAddress(java.lang.String streetAddress) {
        this.streetAddress = streetAddress;
    }


    /**
     * Gets the city value for this SiteAcccrualReportType.
     * 
     * @return city
     */
    public java.lang.String getCity() {
        return city;
    }


    /**
     * Sets the city value for this SiteAcccrualReportType.
     * 
     * @param city
     */
    public void setCity(java.lang.String city) {
        this.city = city;
    }


    /**
     * Gets the stateCode value for this SiteAcccrualReportType.
     * 
     * @return stateCode
     */
    public java.lang.String getStateCode() {
        return stateCode;
    }


    /**
     * Sets the stateCode value for this SiteAcccrualReportType.
     * 
     * @param stateCode
     */
    public void setStateCode(java.lang.String stateCode) {
        this.stateCode = stateCode;
    }


    /**
     * Gets the postalCode value for this SiteAcccrualReportType.
     * 
     * @return postalCode
     */
    public java.lang.String getPostalCode() {
        return postalCode;
    }


    /**
     * Sets the postalCode value for this SiteAcccrualReportType.
     * 
     * @param postalCode
     */
    public void setPostalCode(java.lang.String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     * Gets the countryCode value for this SiteAcccrualReportType.
     * 
     * @return countryCode
     */
    public java.lang.String getCountryCode() {
        return countryCode;
    }


    /**
     * Sets the countryCode value for this SiteAcccrualReportType.
     * 
     * @param countryCode
     */
    public void setCountryCode(java.lang.String countryCode) {
        this.countryCode = countryCode;
    }


    /**
     * Gets the lat value for this SiteAcccrualReportType.
     * 
     * @return lat
     */
    public java.lang.Double getLat() {
        return lat;
    }


    /**
     * Sets the lat value for this SiteAcccrualReportType.
     * 
     * @param lat
     */
    public void setLat(java.lang.Double lat) {
        this.lat = lat;
    }


    /**
     * Gets the _long value for this SiteAcccrualReportType.
     * 
     * @return _long
     */
    public java.lang.Double get_long() {
        return _long;
    }


    /**
     * Sets the _long value for this SiteAcccrualReportType.
     * 
     * @param _long
     */
    public void set_long(java.lang.Double _long) {
        this._long = _long;
    }


    /**
     * Gets the accrual value for this SiteAcccrualReportType.
     * 
     * @return accrual
     */
    public gov.nih.nci.cabig.ccts.domain.AccrualType getAccrual() {
        return accrual;
    }


    /**
     * Sets the accrual value for this SiteAcccrualReportType.
     * 
     * @param accrual
     */
    public void setAccrual(gov.nih.nci.cabig.ccts.domain.AccrualType accrual) {
        this.accrual = accrual;
    }


    /**
     * Gets the studyAccrualReports value for this SiteAcccrualReportType.
     * 
     * @return studyAccrualReports
     */
    public gov.nih.nci.cabig.ccts.domain.StudyAccrualReportType[] getStudyAccrualReports() {
        return studyAccrualReports;
    }


    /**
     * Sets the studyAccrualReports value for this SiteAcccrualReportType.
     * 
     * @param studyAccrualReports
     */
    public void setStudyAccrualReports(gov.nih.nci.cabig.ccts.domain.StudyAccrualReportType[] studyAccrualReports) {
        this.studyAccrualReports = studyAccrualReports;
    }

    public gov.nih.nci.cabig.ccts.domain.StudyAccrualReportType getStudyAccrualReports(int i) {
        return this.studyAccrualReports[i];
    }

    public void setStudyAccrualReports(int i, gov.nih.nci.cabig.ccts.domain.StudyAccrualReportType _value) {
        this.studyAccrualReports[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SiteAcccrualReportType)) return false;
        SiteAcccrualReportType other = (SiteAcccrualReportType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ctepId==null && other.getCtepId()==null) || 
             (this.ctepId!=null &&
              this.ctepId.equals(other.getCtepId()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.streetAddress==null && other.getStreetAddress()==null) || 
             (this.streetAddress!=null &&
              this.streetAddress.equals(other.getStreetAddress()))) &&
            ((this.city==null && other.getCity()==null) || 
             (this.city!=null &&
              this.city.equals(other.getCity()))) &&
            ((this.stateCode==null && other.getStateCode()==null) || 
             (this.stateCode!=null &&
              this.stateCode.equals(other.getStateCode()))) &&
            ((this.postalCode==null && other.getPostalCode()==null) || 
             (this.postalCode!=null &&
              this.postalCode.equals(other.getPostalCode()))) &&
            ((this.countryCode==null && other.getCountryCode()==null) || 
             (this.countryCode!=null &&
              this.countryCode.equals(other.getCountryCode()))) &&
            ((this.lat==null && other.getLat()==null) || 
             (this.lat!=null &&
              this.lat.equals(other.getLat()))) &&
            ((this._long==null && other.get_long()==null) || 
             (this._long!=null &&
              this._long.equals(other.get_long()))) &&
            ((this.accrual==null && other.getAccrual()==null) || 
             (this.accrual!=null &&
              this.accrual.equals(other.getAccrual()))) &&
            ((this.studyAccrualReports==null && other.getStudyAccrualReports()==null) || 
             (this.studyAccrualReports!=null &&
              java.util.Arrays.equals(this.studyAccrualReports, other.getStudyAccrualReports())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCtepId() != null) {
            _hashCode += getCtepId().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getStreetAddress() != null) {
            _hashCode += getStreetAddress().hashCode();
        }
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getStateCode() != null) {
            _hashCode += getStateCode().hashCode();
        }
        if (getPostalCode() != null) {
            _hashCode += getPostalCode().hashCode();
        }
        if (getCountryCode() != null) {
            _hashCode += getCountryCode().hashCode();
        }
        if (getLat() != null) {
            _hashCode += getLat().hashCode();
        }
        if (get_long() != null) {
            _hashCode += get_long().hashCode();
        }
        if (getAccrual() != null) {
            _hashCode += getAccrual().hashCode();
        }
        if (getStudyAccrualReports() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStudyAccrualReports());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStudyAccrualReports(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SiteAcccrualReportType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "SiteAcccrualReportType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctepId");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "ctepId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "streetAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "city"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stateCode");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "stateCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "postalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "countryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lat");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "lat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_long");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "long"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accrual");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "accrual"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "AccrualType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("studyAccrualReports");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "studyAccrualReports"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StudyAccrualReportType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
