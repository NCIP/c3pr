/**
 * GetSiteAccrualReportRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package edu.duke.cabig.c3pr.grid.accrualservice.stubs;

public class GetSiteAccrualReportRequest  implements java.io.Serializable {
    private java.lang.String diseaseSiteName;
    private java.lang.String studyShortTitleText;
    private edu.duke.cabig.c3pr.grid.accrualservice.stubs.GetSiteAccrualReportRequestStartDate startDate;
    private edu.duke.cabig.c3pr.grid.accrualservice.stubs.GetSiteAccrualReportRequestEndDate endDate;

    public GetSiteAccrualReportRequest() {
    }

    public GetSiteAccrualReportRequest(
           java.lang.String diseaseSiteName,
           edu.duke.cabig.c3pr.grid.accrualservice.stubs.GetSiteAccrualReportRequestEndDate endDate,
           edu.duke.cabig.c3pr.grid.accrualservice.stubs.GetSiteAccrualReportRequestStartDate startDate,
           java.lang.String studyShortTitleText) {
           this.diseaseSiteName = diseaseSiteName;
           this.studyShortTitleText = studyShortTitleText;
           this.startDate = startDate;
           this.endDate = endDate;
    }


    /**
     * Gets the diseaseSiteName value for this GetSiteAccrualReportRequest.
     * 
     * @return diseaseSiteName
     */
    public java.lang.String getDiseaseSiteName() {
        return diseaseSiteName;
    }


    /**
     * Sets the diseaseSiteName value for this GetSiteAccrualReportRequest.
     * 
     * @param diseaseSiteName
     */
    public void setDiseaseSiteName(java.lang.String diseaseSiteName) {
        this.diseaseSiteName = diseaseSiteName;
    }


    /**
     * Gets the studyShortTitleText value for this GetSiteAccrualReportRequest.
     * 
     * @return studyShortTitleText
     */
    public java.lang.String getStudyShortTitleText() {
        return studyShortTitleText;
    }


    /**
     * Sets the studyShortTitleText value for this GetSiteAccrualReportRequest.
     * 
     * @param studyShortTitleText
     */
    public void setStudyShortTitleText(java.lang.String studyShortTitleText) {
        this.studyShortTitleText = studyShortTitleText;
    }


    /**
     * Gets the startDate value for this GetSiteAccrualReportRequest.
     * 
     * @return startDate
     */
    public edu.duke.cabig.c3pr.grid.accrualservice.stubs.GetSiteAccrualReportRequestStartDate getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this GetSiteAccrualReportRequest.
     * 
     * @param startDate
     */
    public void setStartDate(edu.duke.cabig.c3pr.grid.accrualservice.stubs.GetSiteAccrualReportRequestStartDate startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the endDate value for this GetSiteAccrualReportRequest.
     * 
     * @return endDate
     */
    public edu.duke.cabig.c3pr.grid.accrualservice.stubs.GetSiteAccrualReportRequestEndDate getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value for this GetSiteAccrualReportRequest.
     * 
     * @param endDate
     */
    public void setEndDate(edu.duke.cabig.c3pr.grid.accrualservice.stubs.GetSiteAccrualReportRequestEndDate endDate) {
        this.endDate = endDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetSiteAccrualReportRequest)) return false;
        GetSiteAccrualReportRequest other = (GetSiteAccrualReportRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.diseaseSiteName==null && other.getDiseaseSiteName()==null) || 
             (this.diseaseSiteName!=null &&
              this.diseaseSiteName.equals(other.getDiseaseSiteName()))) &&
            ((this.studyShortTitleText==null && other.getStudyShortTitleText()==null) || 
             (this.studyShortTitleText!=null &&
              this.studyShortTitleText.equals(other.getStudyShortTitleText()))) &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.endDate==null && other.getEndDate()==null) || 
             (this.endDate!=null &&
              this.endDate.equals(other.getEndDate())));
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
        if (getDiseaseSiteName() != null) {
            _hashCode += getDiseaseSiteName().hashCode();
        }
        if (getStudyShortTitleText() != null) {
            _hashCode += getStudyShortTitleText().hashCode();
        }
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetSiteAccrualReportRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://accrualservice.grid.c3pr.cabig.duke.edu/AccrualService", ">GetSiteAccrualReportRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("diseaseSiteName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://accrualservice.grid.c3pr.cabig.duke.edu/AccrualService", "diseaseSiteName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("studyShortTitleText");
        elemField.setXmlName(new javax.xml.namespace.QName("http://accrualservice.grid.c3pr.cabig.duke.edu/AccrualService", "studyShortTitleText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://accrualservice.grid.c3pr.cabig.duke.edu/AccrualService", "startDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://accrualservice.grid.c3pr.cabig.duke.edu/AccrualService", ">>GetSiteAccrualReportRequest>startDate"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://accrualservice.grid.c3pr.cabig.duke.edu/AccrualService", "endDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://accrualservice.grid.c3pr.cabig.duke.edu/AccrualService", ">>GetSiteAccrualReportRequest>endDate"));
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
