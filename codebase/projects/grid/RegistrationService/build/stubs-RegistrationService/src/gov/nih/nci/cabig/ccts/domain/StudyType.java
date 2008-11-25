/**
 * StudyType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class StudyType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected StudyType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "Diagnostic";
    public static final java.lang.String _value2 = "Genetic Non-therapeutic";
    public static final java.lang.String _value3 = "Genetic Therapeutic";
    public static final java.lang.String _value4 = "Primary Treatment";
    public static final java.lang.String _value5 = "Supportive";
    public static final java.lang.String _value6 = "Preventive";
    public static final StudyType value1 = new StudyType(_value1);
    public static final StudyType value2 = new StudyType(_value2);
    public static final StudyType value3 = new StudyType(_value3);
    public static final StudyType value4 = new StudyType(_value4);
    public static final StudyType value5 = new StudyType(_value5);
    public static final StudyType value6 = new StudyType(_value6);
    public java.lang.String getValue() { return _value_;}
    public static StudyType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        StudyType enumeration = (StudyType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static StudyType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(StudyType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "studyType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
