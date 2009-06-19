package edu.duke.cabig.c3pr.domain.accrual;

import java.io.PrintStream;

import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

public class Accrual extends AbstractMutableDomainObject implements Cloneable 
{
    protected int value;
    
    public Accrual()
    {
        super();
    }
    
    public Accrual(int value)
    {
        this();
        
        setValue(value);
    }
    
    public int getValue()
    {
        return value;
    }
    
    public void setValue(int value)
    {
        this.value = value;
    }
    
    @Override
    protected Object clone()
    {
        return new Accrual(getValue());
    }
    
    public void print(PrintStream out, int indent)
    {
        char[] tabChars = new char[indent];
        for (int i = 0; i < indent; i++) tabChars[i] = ' ';
        String tab = String.valueOf(tabChars);
        
        out.println(tab + "value=" + getValue());
    }
}

