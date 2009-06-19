/**
 * StudyAccrualReportType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class StudyAccrualReportType  implements java.io.Serializable {
    private java.lang.String identifier;
    private java.lang.String shortTitle;
    private gov.nih.nci.cabig.ccts.domain.AccrualType accrual;
    private gov.nih.nci.cabig.ccts.domain.DiseaseSiteAccrualReportType[] diseaseAccrualReports;

    public StudyAccrualReportType() {
    }

    public StudyAccrualReportType(
           gov.nih.nci.cabig.ccts.domain.AccrualType accrual,
           gov.nih.nci.cabig.ccts.domain.DiseaseSiteAccrualReportType[] diseaseAccrualReports,
           java.lang.String identifier,
           java.lang.String shortTitle) {
           this.identifier = identifier;
           this.shortTitle = shortTitle;
           this.accrual = accrual;
           this.diseaseAccrualReports = diseaseAccrualReports;
    }


    /**
     * Gets the identifier value for this StudyAccrualReportType.
     * 
     * @return identifier
     */
    public java.lang.String getIdentifier() {
        return identifier;
    }


    /**
     * Sets the identifier value for this StudyAccrualReportType.
     * 
     * @param identifier
     */
    public void setIdentifier(java.lang.String identifier) {
        this.identifier = identifier;
    }


    /**
     * Gets the shortTitle value for this StudyAccrualReportType.
     * 
     * @return shortTitle
     */
    public java.lang.String getShortTitle() {
        return shortTitle;
    }


    /**
     * Sets the shortTitle value for this StudyAccrualReportType.
     * 
     * @param shortTitle
     */
    public void setShortTitle(java.lang.String shortTitle) {
        this.shortTitle = shortTitle;
    }


    /**
     * Gets the accrual value for this StudyAccrualReportType.
     * 
     * @return accrual
     */
    public gov.nih.nci.cabig.ccts.domain.AccrualType getAccrual() {
        return accrual;
    }


    /**
     * Sets the accrual value for this StudyAccrualReportType.
     * 
     * @param accrual
     */
    public void setAccrual(gov.nih.nci.cabig.ccts.domain.AccrualType accrual) {
        this.accrual = accrual;
    }


    /**
     * Gets the diseaseAccrualReports value for this StudyAccrualReportType.
     * 
     * @return diseaseAccrualReports
     */
    public gov.nih.nci.cabig.ccts.domain.DiseaseSiteAccrualReportType[] getDiseaseAccrualReports() {
        return diseaseAccrualReports;
    }


    /**
     * Sets the diseaseAccrualReports value for this StudyAccrualReportType.
     * 
     * @param diseaseAccrualReports
     */
    public void setDiseaseAccrualReports(gov.nih.nci.cabig.ccts.domain.DiseaseSiteAccrualReportType[] diseaseAccrualReports) {
        this.diseaseAccrualReports = diseaseAccrualReports;
    }

    public gov.nih.nci.cabig.ccts.domain.DiseaseSiteAccrualReportType getDiseaseAccrualReports(int i) {
        return this.diseaseAccrualReports[i];
    }

    public void setDiseaseAccrualReports(int i, gov.nih.nci.cabig.ccts.domain.DiseaseSiteAccrualReportType _value) {
        this.diseaseAccrualReports[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StudyAccrualReportType)) return false;
        StudyAccrualReportType other = (StudyAccrualReportType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.identifier==null && other.getIdentifier()==null) || 
             (this.identifier!=null &&
              this.identifier.equals(other.getIdentifier()))) &&
            ((this.shortTitle==null && other.getShortTitle()==null) || 
             (this.shortTitle!=null &&
              this.shortTitle.equals(other.getShortTitle()))) &&
            ((this.accrual==null && other.getAccrual()==null) || 
             (this.accrual!=null &&
              this.accrual.equals(other.getAccrual()))) &&
            ((this.diseaseAccrualReports==null && other.getDiseaseAccrualReports()==null) || 
             (this.diseaseAccrualReports!=null &&
              java.util.Arrays.equals(this.diseaseAccrualReports, other.getDiseaseAccrualReports())));
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
        if (getIdentifier() != null) {
            _hashCode += getIdentifier().hashCode();
        }
        if (getShortTitle() != null) {
            _hashCode += getShortTitle().hashCode();
        }
        if (getAccrual() != null) {
            _hashCode += getAccrual().hashCode();
        }
        if (getDiseaseAccrualReports() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDiseaseAccrualReports());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDiseaseAccrualReports(), i);
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
        new org.apache.axis.description.TypeDesc(StudyAccrualReportType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StudyAccrualReportType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identifier");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "identifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shortTitle");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "shortTitle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accrual");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "accrual"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "AccrualType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("diseaseAccrualReports");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "diseaseAccrualReports"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "DiseaseSiteAccrualReportType"));
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
