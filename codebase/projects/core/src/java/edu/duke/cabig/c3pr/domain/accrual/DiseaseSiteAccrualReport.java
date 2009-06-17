package edu.duke.cabig.c3pr.domain.accrual;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;


public class DiseaseSiteAccrualReport extends AbstractMutableDeletableDomainObject implements Cloneable
{
    protected String name;
    protected Accrual accrual;

    public DiseaseSiteAccrualReport() 
    {
        super();
    }
    
    public DiseaseSiteAccrualReport(String name)
    {
        this();
        
        setName(name);
    }
    
    public DiseaseSiteAccrualReport(String name, Accrual accrual)
    {
        this(name);
        
        setAccrual(accrual);
    }
   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Accrual getAccrual()
    {
        return accrual;
    }

    public void setAccrual(Accrual accrual)
    {
        this.accrual = accrual;
    }
    
    @Override
    public Object clone()
    {
    	DiseaseSiteAccrualReport obj = new DiseaseSiteAccrualReport(getName());
        Accrual child = getAccrual();
        if (child != null) obj.setAccrual((Accrual) child.clone());
        return obj;
    }

    public static  List<DiseaseSiteAccrualReport> clone( List<DiseaseSiteAccrualReport> diseaseSites)  {
        List<DiseaseSiteAccrualReport> diseaseSiteAccrualReportObjects = new ArrayList<DiseaseSiteAccrualReport>();
        for (int i = 0; i < diseaseSites.size(); i++) {
        	DiseaseSiteAccrualReport diseaseSiteAccrualReportObject = null;
        	diseaseSiteAccrualReportObject = (DiseaseSiteAccrualReport) diseaseSites.get(i).clone();
        	diseaseSiteAccrualReportObjects.add(diseaseSiteAccrualReportObject);
        }
        return diseaseSiteAccrualReportObjects;
    }
    
    
    public void print(PrintStream out, int indent)
    {
        char[] tabChars = new char[indent];
        for (int i = 0; i < indent; i++) tabChars[i] = ' ';
        String tab = String.valueOf(tabChars);
        
        out.println(tab + "name=" + getName());

        Accrual acc = getAccrual();
        if (acc == null) {
            out.println(tab + "accrual=null");
        } else {
            out.println(tab + "accrual=");
            acc.print(out, indent+3);
        }
    }
}
