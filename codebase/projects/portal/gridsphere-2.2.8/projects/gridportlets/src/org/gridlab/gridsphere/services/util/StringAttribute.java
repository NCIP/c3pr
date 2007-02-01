package org.gridlab.gridsphere.services.util;

import java.util.*;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: StringAttribute.java,v 1.1.1.1 2007-02-01 20:42:24 kherm Exp $
 * <p>
 * Implements the resource attribute interface.
 */

public class StringAttribute {

    protected String name = "";
    protected String value = null;

    /**
     * Default constructor
     */
    public StringAttribute() {

    }

    public StringAttribute(StringAttribute attribute) {
        name = attribute.name;
        value = attribute.value;
    }

    public StringAttribute(String nameEqualsValue) {
        setNameEqualsValue(nameEqualsValue);
    }

    public StringAttribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Returns the name of this attribute.
     * @return The attribute name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this attribute.
     * @param name The attribute name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the string value of this attribute.
     * @return The attribute value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the string value of this attribute.
     * @param value The attribute value
     */
    public void setValue(String value)  {
        this.value = value;
    }

    /**
     * Returns a string in the form "<attribute-name>=<attribute-value>"
     * @return "<attribute-name>=<attribute-value>"
     */
    public String getNameEqualsValue() {
        if (name == null) return null;
        if (value == null) return name + '=';
        return name + '=' + value;
    }

    /**
     * Sets the name and value of this attribute
     * @param nameEqualsValue A string in the form of "<attribute-name>=<attribute-value>"
     */
    public void setNameEqualsValue(String nameEqualsValue) {
        if (nameEqualsValue == null) return;
        int index = nameEqualsValue.indexOf("=");
        if (index <= 0) return;
        name = nameEqualsValue.substring(0,index).trim();
        if (index+1 == nameEqualsValue.length()) {
            value = "";
            return;
        }
        value = nameEqualsValue.substring(index+1).trim();
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass().equals(StringAttribute.class)) {
            StringAttribute attribute = (StringAttribute)o;
            return getNameEqualsValue().equals(attribute.getNameEqualsValue());
        }
        return false;
    }

    public int hashCode() {
        return getNameEqualsValue().hashCode();
    }

    public String[] getValueAsArray() {
        return getValueAsArray(",");
    }

    public String[] getValueAsArray(String delim) {
        ArrayList arrayList = new ArrayList();
        Enumeration enumeration = getValueAsEnumeration(delim);
        while (enumeration.hasMoreElements()) {
            String value = (String)enumeration.nextElement();
            arrayList.add(value);
        }
        return (String[])arrayList.toArray(new String[0]);
    }

    public int getValueAsInt() {
        return getValueAsInt(0);
    }

    public int getValueAsInt(int defaultValue) {
        if (value == null)
            return defaultValue;
        if (value.equals(""))
            return defaultValue;
        try {
            return (new Integer(value)).intValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public int[] getValueAsIntArray() {
        return getValueAsIntArray(",");
    }

    public int[] getValueAsIntArray(String delim) {
        String values[] = getValueAsArray(delim);
        if (values == null) {
            return new int[0];
        } else {
            int objs[] = new int[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Integer(value)).intValue();
                } catch (Exception e) {
                    objs[ii] = 0;
                }
            }
            return objs;
        }
    }

    public long getValueAsLng() {
        return getValueAsLng(0);
    }

    public long getValueAsLng(long defaultValue) {
        if (value == null)
            return defaultValue;
        if (value.equals(""))
            return defaultValue;
        try {
            return (new Long(value)).longValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public long[] getValueAsLngArray() {
        return getValueAsLngArray(",");
    }

    public long[] getValueAsLngArray(String delim) {
        String values[] = getValueAsArray(delim);
        if (values == null) {
            return new long[0];
        } else {
            long objs[] = new long[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Long(value)).longValue();
                } catch (Exception e) {
                    objs[ii] = 0;
                }
            }
            return objs;
        }
    }

    public float getValueAsFlt() {
        return getValueAsFlt((float)0.0);
    }

    public float getValueAsFlt(float defaultValue) {
        if (value == null)
            return defaultValue;
        if (value.equals(""))
            return defaultValue;
        try {
            return (new Float(value)).floatValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public float[] getValueAsFltArray() {
        return getValueAsFltArray(",");
    }

    public float[] getValueAsFltArray(String delim) {
        String values[] = getValueAsArray(delim);
        if (values == null) {
            return new float[0];
        } else {
            float objs[] = new float[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Float(value)).floatValue();
                } catch (Exception e) {
                    objs[ii] = 0;
                }
            }
            return objs;
        }
    }

    public double getValueAsDbl() {
        return getValueAsDbl(0.0);
    }

    public double getValueAsDbl(double defaultValue) {
        if (value == null)
            return defaultValue;
        if (value.equals(""))
            return defaultValue;
        try {
            return (new Double(value)).doubleValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public double[] getValueAsDblArray() {
        return getValueAsDblArray(",");
    }

    public double[] getValueAsDblArray(String delim) {
        String values[] = getValueAsArray(delim);
        if (values == null) {
            return new double[0];
        } else {
            double objs[] = new double[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Double(value)).doubleValue();
                } catch (Exception e) {
                    objs[ii] = 0;
                }
            }
            return objs;
        }
    }

    public boolean getValueAsBool() {
        if (value == null) return false;
        if (value.equals("")) return false;
        if (value.equals("true")) return true;
        return false;
    }

    public boolean[] getValueAsBoolArray() {
        return getValueAsBoolArray(",");
    }

    public boolean[] getValueAsBoolArray(String delim) {
        String values[] = getValueAsArray(delim);
        if (values == null) {
            return new boolean[0];
        } else {
            boolean objs[] = new boolean[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                if (value.equals("")) objs[ii] = false;
                if (value.equals("true")) objs[ii] = true;
                objs[ii] = false;
            }
            return objs;
        }
    }

    public Integer getValueAsInteger() {
        if (value == null)
            return (new Integer(0));
        if (value.equals("")) value = "0";
        try {
            return (new Integer(value));
        } catch (Exception e) {
            return (new Integer(0));
        }
    }

    public Integer[] getValueAsIntegerArray() {
        return getValueAsIntegerArray(",");
    }

    public Integer[] getValueAsIntegerArray(String delim) {
        String values[] = getValueAsArray(delim);
        if (values == null) {
            return new Integer[0];
        } else {
            Integer objs[] = new Integer[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Integer(value));
                } catch (Exception e) {
                    objs[ii] = (new Integer(0));
                }
            }
            return objs;
        }
    }

    public Float getValueAsFloat() {
        if (value == null)
            return (new Float(0.0));
        if (value.equals("")) value = "0";
        try {
            return (new Float(value));
        } catch (Exception e) {
            return (new Float(0.0));
        }
    }

    public Float[] getValueAsFloatArray() {
        return getValueAsFloatArray(",");
    }

    public Float[] getValueAsFloatArray(String delim) {
        String values[] = getValueAsArray(delim);
        if (values == null) {
            return new Float[0];
        } else {
            Float objs[] = new Float[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Float(value));
                } catch (Exception e) {
                    objs[ii] = (new Float(0.0));
                }
            }
            return objs;
        }
    }

    public Double getValueAsDouble() {
        if (value == null)
            return (new Double(0.0));
        if (value.equals("")) value = "0";
        try {
            return (new Double(value));
        } catch (Exception e) {
            return (new Double(0.0));
        }
    }

    public Double[] getValueAsDoubleArray() {
        return getValueAsDoubleArray(",");
    }

    public Double[] getValueAsDoubleArray(String delim) {
        String values[] = getValueAsArray(delim);
        if (values == null) {
            return new Double[0];
        } else {
            Double objs[] = new Double[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Double(value));
                } catch (Exception e) {
                    objs[ii] = (new Double(0.0));
                }
            }
            return objs;
        }
    }

    public Boolean getValueAsBoolean() {
        if (value == null) return Boolean.valueOf(false);
        if (value.equals("")) return Boolean.valueOf(false);
        if (value.equals("true")) return Boolean.valueOf(true);
        return Boolean.valueOf(false);
    }

    public Boolean[] getValueAsBooleanArray() {
        return getValueAsBooleanArray(",");
    }

    public Boolean[] getValueAsBooleanArray(String delim) {
        String values[] = getValueAsArray(delim);
        if (values == null) {
            return new Boolean[0];
        } else {
            Boolean objs[] = new Boolean[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                if (value.equals("")) objs[ii] = Boolean.valueOf(false);
                if (value.equals("true")) objs[ii] = Boolean.valueOf(true);
                objs[ii] = Boolean.valueOf(false);
            }
            return objs;
        }
    }

    public List getValueAsList() {
        return getValueAsList(",");
    }

    public List getValueAsList(String delim) {
        ArrayList arrayList = new ArrayList();
        Enumeration enumeration = getValueAsEnumeration(delim);
        while (enumeration.hasMoreElements()) {
            String value = (String)enumeration.nextElement();
            arrayList.add(value);
        }
        return arrayList;
    }

    public void setValueAsList(List valueList) {
        setValueAsList(valueList, ",");
    }

    public void setValueAsList(List valueList, String delim) {
        value = null;
        StringBuffer valueBuffer = new StringBuffer();
        boolean moreThanOne = true;
        for (Iterator values = valueList.iterator(); values.hasNext();) {
            String nextValue = (String) values.next();
            if (moreThanOne) valueBuffer.append(delim);
            valueBuffer.append(nextValue);
        }
        value = valueBuffer.toString();
    }

    public Enumeration getValueAsEnumeration(String delim) {
        StringTokenizer tokenizer = null;
        if (value == null) {
            tokenizer = new StringTokenizer("", delim);
        } else {
            tokenizer = new StringTokenizer(value, delim);
        }
        return tokenizer;
    }
}
