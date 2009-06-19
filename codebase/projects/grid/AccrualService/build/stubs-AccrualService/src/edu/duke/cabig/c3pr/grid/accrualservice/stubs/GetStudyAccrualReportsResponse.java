/**
 * GetStudyAccrualReportsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package edu.duke.cabig.c3pr.grid.accrualservice.stubs;

public class GetStudyAccrualReportsResponse  implements java.io.Serializable {
    private gov.nih.nci.cabig.ccts.domain.StudyAccrualReportType[] studyAccrualReport;

    public GetStudyAccrualReportsResponse() {
    }

    public GetStudyAccrualReportsResponse(
           gov.nih.nci.cabig.ccts.domain.StudyAccrualReportType[] studyAccrualReport) {
           this.studyAccrualReport = studyAccrualReport;
    }


    /**
     * Gets the studyAccrualReport value for this GetStudyAccrualReportsResponse.
     * 
     * @return studyAccrualReport
     */
    public gov.nih.nci.cabig.ccts.domain.StudyAccrualReportType[] getStudyAccrualReport() {
        return studyAccrualReport;
    }


    /**
     * Sets the studyAccrualReport value for this GetStudyAccrualReportsResponse.
     * 
     * @param studyAccrualReport
     */
    public void setStudyAccrualReport(gov.nih.nci.cabig.ccts.domain.StudyAccrualReportType[] studyAccrualReport) {
        this.studyAccrualReport = studyAccrualReport;
    }

    public gov.nih.nci.cabig.ccts.domain.StudyAccrualReportType getStudyAccrualReport(int i) {
        return this.studyAccrualReport[i];
    }

    public void setStudyAccrualReport(int i, gov.nih.nci.cabig.ccts.domain.StudyAccrualReportType _value) {
        this.studyAccrualReport[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetStudyAccrualReportsResponse)) return false;
        GetStudyAccrualReportsResponse other = (GetStudyAccrualReportsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.studyAccrualReport==null && other.getStudyAccrualReport()==null) || 
             (this.studyAccrualReport!=null &&
              java.util.Arrays.equals(this.studyAccrualReport, other.getStudyAccrualReport())));
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
        if (getStudyAccrualReport() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStudyAccrualReport());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStudyAccrualReport(), i);
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
        new org.apache.axis.description.TypeDesc(GetStudyAccrualReportsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://accrualservice.grid.c3pr.cabig.duke.edu/AccrualService", ">GetStudyAccrualReportsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("studyAccrualReport");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "studyAccrualReport"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StudyAccrualReportType"));
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
