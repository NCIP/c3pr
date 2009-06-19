/**
 * GetSiteAccrualReportResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package edu.duke.cabig.c3pr.grid.accrualservice.stubs;

public class GetSiteAccrualReportResponse  implements java.io.Serializable {
    private gov.nih.nci.cabig.ccts.domain.SiteAcccrualReportType siteAccrualReport;

    public GetSiteAccrualReportResponse() {
    }

    public GetSiteAccrualReportResponse(
           gov.nih.nci.cabig.ccts.domain.SiteAcccrualReportType siteAccrualReport) {
           this.siteAccrualReport = siteAccrualReport;
    }


    /**
     * Gets the siteAccrualReport value for this GetSiteAccrualReportResponse.
     * 
     * @return siteAccrualReport
     */
    public gov.nih.nci.cabig.ccts.domain.SiteAcccrualReportType getSiteAccrualReport() {
        return siteAccrualReport;
    }


    /**
     * Sets the siteAccrualReport value for this GetSiteAccrualReportResponse.
     * 
     * @param siteAccrualReport
     */
    public void setSiteAccrualReport(gov.nih.nci.cabig.ccts.domain.SiteAcccrualReportType siteAccrualReport) {
        this.siteAccrualReport = siteAccrualReport;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetSiteAccrualReportResponse)) return false;
        GetSiteAccrualReportResponse other = (GetSiteAccrualReportResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.siteAccrualReport==null && other.getSiteAccrualReport()==null) || 
             (this.siteAccrualReport!=null &&
              this.siteAccrualReport.equals(other.getSiteAccrualReport())));
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
        if (getSiteAccrualReport() != null) {
            _hashCode += getSiteAccrualReport().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetSiteAccrualReportResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://accrualservice.grid.c3pr.cabig.duke.edu/AccrualService", ">GetSiteAccrualReportResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("siteAccrualReport");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "siteAccrualReport"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "SiteAcccrualReportType"));
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
