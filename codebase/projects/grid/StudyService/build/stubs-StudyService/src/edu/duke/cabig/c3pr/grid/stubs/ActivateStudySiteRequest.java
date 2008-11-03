/**
 * ActivateStudySiteRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package edu.duke.cabig.c3pr.grid.stubs;

public class ActivateStudySiteRequest  implements java.io.Serializable {
    private edu.duke.cabig.c3pr.grid.stubs.ActivateStudySiteRequestMessage message;

    public ActivateStudySiteRequest() {
    }

    public ActivateStudySiteRequest(
           edu.duke.cabig.c3pr.grid.stubs.ActivateStudySiteRequestMessage message) {
           this.message = message;
    }


    /**
     * Gets the message value for this ActivateStudySiteRequest.
     * 
     * @return message
     */
    public edu.duke.cabig.c3pr.grid.stubs.ActivateStudySiteRequestMessage getMessage() {
        return message;
    }


    /**
     * Sets the message value for this ActivateStudySiteRequest.
     * 
     * @param message
     */
    public void setMessage(edu.duke.cabig.c3pr.grid.stubs.ActivateStudySiteRequestMessage message) {
        this.message = message;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ActivateStudySiteRequest)) return false;
        ActivateStudySiteRequest other = (ActivateStudySiteRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage())));
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
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ActivateStudySiteRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://grid.c3pr.cabig.duke.edu/StudyService", ">ActivateStudySiteRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://grid.c3pr.cabig.duke.edu/StudyService", "message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://grid.c3pr.cabig.duke.edu/StudyService", ">>ActivateStudySiteRequest>message"));
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
