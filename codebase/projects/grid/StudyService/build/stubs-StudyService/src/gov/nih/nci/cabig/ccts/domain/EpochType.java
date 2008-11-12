/**
 * EpochType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class EpochType  implements java.io.Serializable {
    private java.lang.String name;
    private java.lang.String descriptionText;
    private gov.nih.nci.cabig.ccts.domain.ArmType[] arm;
    private java.lang.Integer accrualCeiling;
    private java.lang.String accrualIndicator;
    private java.lang.String reservationIndicator;
    private java.lang.String treatmentIndicator;
    private java.lang.String enrollmentIndicator;
    private gov.nih.nci.cabig.ccts.domain.EligibilityCriteriaType[] eligibilityCriteria;
    private gov.nih.nci.cabig.ccts.domain.StratificationCriterionType[] stratificationCriteria;
    private java.lang.String gridId;  // attribute

    public EpochType() {
    }

    public EpochType(
           java.lang.Integer accrualCeiling,
           java.lang.String accrualIndicator,
           gov.nih.nci.cabig.ccts.domain.ArmType[] arm,
           java.lang.String descriptionText,
           gov.nih.nci.cabig.ccts.domain.EligibilityCriteriaType[] eligibilityCriteria,
           java.lang.String enrollmentIndicator,
           java.lang.String gridId,
           java.lang.String name,
           java.lang.String reservationIndicator,
           gov.nih.nci.cabig.ccts.domain.StratificationCriterionType[] stratificationCriteria,
           java.lang.String treatmentIndicator) {
           this.name = name;
           this.descriptionText = descriptionText;
           this.arm = arm;
           this.accrualCeiling = accrualCeiling;
           this.accrualIndicator = accrualIndicator;
           this.reservationIndicator = reservationIndicator;
           this.treatmentIndicator = treatmentIndicator;
           this.enrollmentIndicator = enrollmentIndicator;
           this.eligibilityCriteria = eligibilityCriteria;
           this.stratificationCriteria = stratificationCriteria;
           this.gridId = gridId;
    }


    /**
     * Gets the name value for this EpochType.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this EpochType.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the descriptionText value for this EpochType.
     * 
     * @return descriptionText
     */
    public java.lang.String getDescriptionText() {
        return descriptionText;
    }


    /**
     * Sets the descriptionText value for this EpochType.
     * 
     * @param descriptionText
     */
    public void setDescriptionText(java.lang.String descriptionText) {
        this.descriptionText = descriptionText;
    }


    /**
     * Gets the arm value for this EpochType.
     * 
     * @return arm
     */
    public gov.nih.nci.cabig.ccts.domain.ArmType[] getArm() {
        return arm;
    }


    /**
     * Sets the arm value for this EpochType.
     * 
     * @param arm
     */
    public void setArm(gov.nih.nci.cabig.ccts.domain.ArmType[] arm) {
        this.arm = arm;
    }

    public gov.nih.nci.cabig.ccts.domain.ArmType getArm(int i) {
        return this.arm[i];
    }

    public void setArm(int i, gov.nih.nci.cabig.ccts.domain.ArmType _value) {
        this.arm[i] = _value;
    }


    /**
     * Gets the accrualCeiling value for this EpochType.
     * 
     * @return accrualCeiling
     */
    public java.lang.Integer getAccrualCeiling() {
        return accrualCeiling;
    }


    /**
     * Sets the accrualCeiling value for this EpochType.
     * 
     * @param accrualCeiling
     */
    public void setAccrualCeiling(java.lang.Integer accrualCeiling) {
        this.accrualCeiling = accrualCeiling;
    }


    /**
     * Gets the accrualIndicator value for this EpochType.
     * 
     * @return accrualIndicator
     */
    public java.lang.String getAccrualIndicator() {
        return accrualIndicator;
    }


    /**
     * Sets the accrualIndicator value for this EpochType.
     * 
     * @param accrualIndicator
     */
    public void setAccrualIndicator(java.lang.String accrualIndicator) {
        this.accrualIndicator = accrualIndicator;
    }


    /**
     * Gets the reservationIndicator value for this EpochType.
     * 
     * @return reservationIndicator
     */
    public java.lang.String getReservationIndicator() {
        return reservationIndicator;
    }


    /**
     * Sets the reservationIndicator value for this EpochType.
     * 
     * @param reservationIndicator
     */
    public void setReservationIndicator(java.lang.String reservationIndicator) {
        this.reservationIndicator = reservationIndicator;
    }


    /**
     * Gets the treatmentIndicator value for this EpochType.
     * 
     * @return treatmentIndicator
     */
    public java.lang.String getTreatmentIndicator() {
        return treatmentIndicator;
    }


    /**
     * Sets the treatmentIndicator value for this EpochType.
     * 
     * @param treatmentIndicator
     */
    public void setTreatmentIndicator(java.lang.String treatmentIndicator) {
        this.treatmentIndicator = treatmentIndicator;
    }


    /**
     * Gets the enrollmentIndicator value for this EpochType.
     * 
     * @return enrollmentIndicator
     */
    public java.lang.String getEnrollmentIndicator() {
        return enrollmentIndicator;
    }


    /**
     * Sets the enrollmentIndicator value for this EpochType.
     * 
     * @param enrollmentIndicator
     */
    public void setEnrollmentIndicator(java.lang.String enrollmentIndicator) {
        this.enrollmentIndicator = enrollmentIndicator;
    }


    /**
     * Gets the eligibilityCriteria value for this EpochType.
     * 
     * @return eligibilityCriteria
     */
    public gov.nih.nci.cabig.ccts.domain.EligibilityCriteriaType[] getEligibilityCriteria() {
        return eligibilityCriteria;
    }


    /**
     * Sets the eligibilityCriteria value for this EpochType.
     * 
     * @param eligibilityCriteria
     */
    public void setEligibilityCriteria(gov.nih.nci.cabig.ccts.domain.EligibilityCriteriaType[] eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    public gov.nih.nci.cabig.ccts.domain.EligibilityCriteriaType getEligibilityCriteria(int i) {
        return this.eligibilityCriteria[i];
    }

    public void setEligibilityCriteria(int i, gov.nih.nci.cabig.ccts.domain.EligibilityCriteriaType _value) {
        this.eligibilityCriteria[i] = _value;
    }


    /**
     * Gets the stratificationCriteria value for this EpochType.
     * 
     * @return stratificationCriteria
     */
    public gov.nih.nci.cabig.ccts.domain.StratificationCriterionType[] getStratificationCriteria() {
        return stratificationCriteria;
    }


    /**
     * Sets the stratificationCriteria value for this EpochType.
     * 
     * @param stratificationCriteria
     */
    public void setStratificationCriteria(gov.nih.nci.cabig.ccts.domain.StratificationCriterionType[] stratificationCriteria) {
        this.stratificationCriteria = stratificationCriteria;
    }

    public gov.nih.nci.cabig.ccts.domain.StratificationCriterionType getStratificationCriteria(int i) {
        return this.stratificationCriteria[i];
    }

    public void setStratificationCriteria(int i, gov.nih.nci.cabig.ccts.domain.StratificationCriterionType _value) {
        this.stratificationCriteria[i] = _value;
    }


    /**
     * Gets the gridId value for this EpochType.
     * 
     * @return gridId
     */
    public java.lang.String getGridId() {
        return gridId;
    }


    /**
     * Sets the gridId value for this EpochType.
     * 
     * @param gridId
     */
    public void setGridId(java.lang.String gridId) {
        this.gridId = gridId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EpochType)) return false;
        EpochType other = (EpochType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.descriptionText==null && other.getDescriptionText()==null) || 
             (this.descriptionText!=null &&
              this.descriptionText.equals(other.getDescriptionText()))) &&
            ((this.arm==null && other.getArm()==null) || 
             (this.arm!=null &&
              java.util.Arrays.equals(this.arm, other.getArm()))) &&
            ((this.accrualCeiling==null && other.getAccrualCeiling()==null) || 
             (this.accrualCeiling!=null &&
              this.accrualCeiling.equals(other.getAccrualCeiling()))) &&
            ((this.accrualIndicator==null && other.getAccrualIndicator()==null) || 
             (this.accrualIndicator!=null &&
              this.accrualIndicator.equals(other.getAccrualIndicator()))) &&
            ((this.reservationIndicator==null && other.getReservationIndicator()==null) || 
             (this.reservationIndicator!=null &&
              this.reservationIndicator.equals(other.getReservationIndicator()))) &&
            ((this.treatmentIndicator==null && other.getTreatmentIndicator()==null) || 
             (this.treatmentIndicator!=null &&
              this.treatmentIndicator.equals(other.getTreatmentIndicator()))) &&
            ((this.enrollmentIndicator==null && other.getEnrollmentIndicator()==null) || 
             (this.enrollmentIndicator!=null &&
              this.enrollmentIndicator.equals(other.getEnrollmentIndicator()))) &&
            ((this.eligibilityCriteria==null && other.getEligibilityCriteria()==null) || 
             (this.eligibilityCriteria!=null &&
              java.util.Arrays.equals(this.eligibilityCriteria, other.getEligibilityCriteria()))) &&
            ((this.stratificationCriteria==null && other.getStratificationCriteria()==null) || 
             (this.stratificationCriteria!=null &&
              java.util.Arrays.equals(this.stratificationCriteria, other.getStratificationCriteria()))) &&
            ((this.gridId==null && other.getGridId()==null) || 
             (this.gridId!=null &&
              this.gridId.equals(other.getGridId())));
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
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getDescriptionText() != null) {
            _hashCode += getDescriptionText().hashCode();
        }
        if (getArm() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getArm());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getArm(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAccrualCeiling() != null) {
            _hashCode += getAccrualCeiling().hashCode();
        }
        if (getAccrualIndicator() != null) {
            _hashCode += getAccrualIndicator().hashCode();
        }
        if (getReservationIndicator() != null) {
            _hashCode += getReservationIndicator().hashCode();
        }
        if (getTreatmentIndicator() != null) {
            _hashCode += getTreatmentIndicator().hashCode();
        }
        if (getEnrollmentIndicator() != null) {
            _hashCode += getEnrollmentIndicator().hashCode();
        }
        if (getEligibilityCriteria() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEligibilityCriteria());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEligibilityCriteria(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStratificationCriteria() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStratificationCriteria());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStratificationCriteria(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGridId() != null) {
            _hashCode += getGridId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EpochType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "EpochType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("gridId");
        attrField.setXmlName(new javax.xml.namespace.QName("", "gridId"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descriptionText");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "descriptionText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("arm");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "arm"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "ArmType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accrualCeiling");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "accrualCeiling"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accrualIndicator");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "accrualIndicator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reservationIndicator");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "reservationIndicator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("treatmentIndicator");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "treatmentIndicator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enrollmentIndicator");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "enrollmentIndicator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eligibilityCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "eligibilityCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "EligibilityCriteriaType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stratificationCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "stratificationCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StratificationCriterionType"));
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
