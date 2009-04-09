/**
 * CompanionStudyType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class CompanionStudyType  implements java.io.Serializable {
    private gov.nih.nci.cabig.ccts.domain.Study companionStudy;
    private java.lang.String mandatoryIndicator;
    private java.lang.String gridId;  // attribute
    private java.math.BigInteger version;  // attribute

    public CompanionStudyType() {
    }

    public CompanionStudyType(
           gov.nih.nci.cabig.ccts.domain.Study companionStudy,
           java.lang.String gridId,
           java.lang.String mandatoryIndicator,
           java.math.BigInteger version) {
           this.companionStudy = companionStudy;
           this.mandatoryIndicator = mandatoryIndicator;
           this.gridId = gridId;
           this.version = version;
    }


    /**
     * Gets the companionStudy value for this CompanionStudyType.
     * 
     * @return companionStudy
     */
    public gov.nih.nci.cabig.ccts.domain.Study getCompanionStudy() {
        return companionStudy;
    }


    /**
     * Sets the companionStudy value for this CompanionStudyType.
     * 
     * @param companionStudy
     */
    public void setCompanionStudy(gov.nih.nci.cabig.ccts.domain.Study companionStudy) {
        this.companionStudy = companionStudy;
    }


    /**
     * Gets the mandatoryIndicator value for this CompanionStudyType.
     * 
     * @return mandatoryIndicator
     */
    public java.lang.String getMandatoryIndicator() {
        return mandatoryIndicator;
    }


    /**
     * Sets the mandatoryIndicator value for this CompanionStudyType.
     * 
     * @param mandatoryIndicator
     */
    public void setMandatoryIndicator(java.lang.String mandatoryIndicator) {
        this.mandatoryIndicator = mandatoryIndicator;
    }


    /**
     * Gets the gridId value for this CompanionStudyType.
     * 
     * @return gridId
     */
    public java.lang.String getGridId() {
        return gridId;
    }


    /**
     * Sets the gridId value for this CompanionStudyType.
     * 
     * @param gridId
     */
    public void setGridId(java.lang.String gridId) {
        this.gridId = gridId;
    }


    /**
     * Gets the version value for this CompanionStudyType.
     * 
     * @return version
     */
    public java.math.BigInteger getVersion() {
        return version;
    }


    /**
     * Sets the version value for this CompanionStudyType.
     * 
     * @param version
     */
    public void setVersion(java.math.BigInteger version) {
        this.version = version;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CompanionStudyType)) return false;
        CompanionStudyType other = (CompanionStudyType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.companionStudy==null && other.getCompanionStudy()==null) || 
             (this.companionStudy!=null &&
              this.companionStudy.equals(other.getCompanionStudy()))) &&
            ((this.mandatoryIndicator==null && other.getMandatoryIndicator()==null) || 
             (this.mandatoryIndicator!=null &&
              this.mandatoryIndicator.equals(other.getMandatoryIndicator()))) &&
            ((this.gridId==null && other.getGridId()==null) || 
             (this.gridId!=null &&
              this.gridId.equals(other.getGridId()))) &&
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion())));
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
        if (getCompanionStudy() != null) {
            _hashCode += getCompanionStudy().hashCode();
        }
        if (getMandatoryIndicator() != null) {
            _hashCode += getMandatoryIndicator().hashCode();
        }
        if (getGridId() != null) {
            _hashCode += getGridId().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CompanionStudyType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "CompanionStudyType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("gridId");
        attrField.setXmlName(new javax.xml.namespace.QName("", "gridId"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("version");
        attrField.setXmlName(new javax.xml.namespace.QName("", "version"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companionStudy");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "companionStudy"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "Study"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mandatoryIndicator");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "mandatoryIndicator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
