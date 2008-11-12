/**
 * Studies.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class Studies  implements java.io.Serializable {
    private gov.nih.nci.cabig.ccts.domain.Study[] study;

    public Studies() {
    }

    public Studies(
           gov.nih.nci.cabig.ccts.domain.Study[] study) {
           this.study = study;
    }


    /**
     * Gets the study value for this Studies.
     * 
     * @return study
     */
    public gov.nih.nci.cabig.ccts.domain.Study[] getStudy() {
        return study;
    }


    /**
     * Sets the study value for this Studies.
     * 
     * @param study
     */
    public void setStudy(gov.nih.nci.cabig.ccts.domain.Study[] study) {
        this.study = study;
    }

    public gov.nih.nci.cabig.ccts.domain.Study getStudy(int i) {
        return this.study[i];
    }

    public void setStudy(int i, gov.nih.nci.cabig.ccts.domain.Study _value) {
        this.study[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Studies)) return false;
        Studies other = (Studies) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.study==null && other.getStudy()==null) || 
             (this.study!=null &&
              java.util.Arrays.equals(this.study, other.getStudy())));
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
        if (getStudy() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStudy());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStudy(), i);
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
        new org.apache.axis.description.TypeDesc(Studies.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", ">studies"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("study");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "study"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "Study"));
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
