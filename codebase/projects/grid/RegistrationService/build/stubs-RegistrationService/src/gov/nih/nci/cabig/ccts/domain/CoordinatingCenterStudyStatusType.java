/**
 * CoordinatingCenterStudyStatusType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class CoordinatingCenterStudyStatusType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CoordinatingCenterStudyStatusType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _OPEN = "OPEN";
    public static final java.lang.String _PENDING = "PENDING";
    public static final java.lang.String _AMENDMENT_PENDING = "AMENDMENT_PENDING";
    public static final java.lang.String _CLOSED_TO_ACCRUAL_AND_TREATMENT = "CLOSED_TO_ACCRUAL_AND_TREATMENT";
    public static final java.lang.String _CLOSED_TO_ACCRUAL = "CLOSED_TO_ACCRUAL";
    public static final java.lang.String _TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT = "TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT";
    public static final java.lang.String _TEMPORARILY_CLOSED_TO_ACCRUAL = "TEMPORARILY_CLOSED_TO_ACCRUAL";
    public static final CoordinatingCenterStudyStatusType OPEN = new CoordinatingCenterStudyStatusType(_OPEN);
    public static final CoordinatingCenterStudyStatusType PENDING = new CoordinatingCenterStudyStatusType(_PENDING);
    public static final CoordinatingCenterStudyStatusType AMENDMENT_PENDING = new CoordinatingCenterStudyStatusType(_AMENDMENT_PENDING);
    public static final CoordinatingCenterStudyStatusType CLOSED_TO_ACCRUAL_AND_TREATMENT = new CoordinatingCenterStudyStatusType(_CLOSED_TO_ACCRUAL_AND_TREATMENT);
    public static final CoordinatingCenterStudyStatusType CLOSED_TO_ACCRUAL = new CoordinatingCenterStudyStatusType(_CLOSED_TO_ACCRUAL);
    public static final CoordinatingCenterStudyStatusType TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT = new CoordinatingCenterStudyStatusType(_TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT);
    public static final CoordinatingCenterStudyStatusType TEMPORARILY_CLOSED_TO_ACCRUAL = new CoordinatingCenterStudyStatusType(_TEMPORARILY_CLOSED_TO_ACCRUAL);
    public java.lang.String getValue() { return _value_;}
    public static CoordinatingCenterStudyStatusType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        CoordinatingCenterStudyStatusType enumeration = (CoordinatingCenterStudyStatusType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static CoordinatingCenterStudyStatusType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CoordinatingCenterStudyStatusType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "CoordinatingCenterStudyStatusType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
