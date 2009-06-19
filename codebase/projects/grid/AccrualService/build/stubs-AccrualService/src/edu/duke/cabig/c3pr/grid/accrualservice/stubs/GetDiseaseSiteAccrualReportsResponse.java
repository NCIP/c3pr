/**
 * GetDiseaseSiteAccrualReportsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package edu.duke.cabig.c3pr.grid.accrualservice.stubs;

public class GetDiseaseSiteAccrualReportsResponse  implements java.io.Serializable {
    private gov.nih.nci.cabig.ccts.domain.DiseaseSiteAccrualReportType[] diseaseSiteAccrualReport;

    public GetDiseaseSiteAccrualReportsResponse() {
    }

    public GetDiseaseSiteAccrualReportsResponse(
           gov.nih.nci.cabig.ccts.domain.DiseaseSiteAccrualReportType[] diseaseSiteAccrualReport) {
           this.diseaseSiteAccrualReport = diseaseSiteAccrualReport;
    }


    /**
     * Gets the diseaseSiteAccrualReport value for this GetDiseaseSiteAccrualReportsResponse.
     * 
     * @return diseaseSiteAccrualReport
     */
    public gov.nih.nci.cabig.ccts.domain.DiseaseSiteAccrualReportType[] getDiseaseSiteAccrualReport() {
        return diseaseSiteAccrualReport;
    }


    /**
     * Sets the diseaseSiteAccrualReport value for this GetDiseaseSiteAccrualReportsResponse.
     * 
     * @param diseaseSiteAccrualReport
     */
    public void setDiseaseSiteAccrualReport(gov.nih.nci.cabig.ccts.domain.DiseaseSiteAccrualReportType[] diseaseSiteAccrualReport) {
        this.diseaseSiteAccrualReport = diseaseSiteAccrualReport;
    }

    public gov.nih.nci.cabig.ccts.domain.DiseaseSiteAccrualReportType getDiseaseSiteAccrualReport(int i) {
        return this.diseaseSiteAccrualReport[i];
    }

    public void setDiseaseSiteAccrualReport(int i, gov.nih.nci.cabig.ccts.domain.DiseaseSiteAccrualReportType _value) {
        this.diseaseSiteAccrualReport[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetDiseaseSiteAccrualReportsResponse)) return false;
        GetDiseaseSiteAccrualReportsResponse other = (GetDiseaseSiteAccrualReportsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.diseaseSiteAccrualReport==null && other.getDiseaseSiteAccrualReport()==null) || 
             (this.diseaseSiteAccrualReport!=null &&
              java.util.Arrays.equals(this.diseaseSiteAccrualReport, other.getDiseaseSiteAccrualReport())));
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
        if (getDiseaseSiteAccrualReport() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDiseaseSiteAccrualReport());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDiseaseSiteAccrualReport(), i);
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
        new org.apache.axis.description.TypeDesc(GetDiseaseSiteAccrualReportsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://accrualservice.grid.c3pr.cabig.duke.edu/AccrualService", ">GetDiseaseSiteAccrualReportsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("diseaseSiteAccrualReport");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "diseaseSiteAccrualReport"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "DiseaseSiteAccrualReportType"));
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
