package edu.duke.cabig.c3pr.domain.accrual;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

public class StudyAccrualReport extends AbstractMutableDeletableDomainObject implements Cloneable {
    protected String identifier;
    protected String shortTitle;
    protected Accrual accrual;
    protected List<DiseaseSiteAccrualReport> diseaseSiteAccrualReports;

    public List<DiseaseSiteAccrualReport> getDiseaseSiteAccrualReports() {
		return diseaseSiteAccrualReports;
	}

	public void setDiseaseSiteAccrualReports(
			List<DiseaseSiteAccrualReport> diseaseSiteAccrualReports) {
		this.diseaseSiteAccrualReports = diseaseSiteAccrualReports;
	}

	public StudyAccrualReport()
    {
        super();
    }

    public StudyAccrualReport(String identifier, String shortTitle)
    {
        this();

        setIdentifier(identifier);
        setShortTitle(shortTitle);
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public Accrual getAccrual()
    {
        return accrual;
    }

    public void setAccrual(Accrual accrual)
    {
        this.accrual = accrual;
    }


	public DiseaseSiteAccrualReport getDiseaseSite(String name)
    {
        List<DiseaseSiteAccrualReport> diseaseSites = getDiseaseSiteAccrualReports();
        if (diseaseSites == null) return null;
        for (DiseaseSiteAccrualReport diseaseSite : diseaseSites) if (name.equals(diseaseSite.getName())) return diseaseSite;
        return null;
    }

    @Override
    public Object clone()
    {
        StudyAccrualReport obj = new StudyAccrualReport(getIdentifier(), getShortTitle());
        Accrual child = getAccrual();
        if (child != null) obj.setAccrual((Accrual) child.clone());
        obj.setDiseaseSiteAccrualReports(DiseaseSiteAccrualReport.clone(getDiseaseSiteAccrualReports()));
        return obj;
    }

    public static List<StudyAccrualReport> clone(List<StudyAccrualReport> studyAccrualReports)
    {
    	 List<StudyAccrualReport> studyAccrualReportObjects = new ArrayList<StudyAccrualReport>();
         for (int i = 0; i < studyAccrualReports.size(); i++) {
        	 StudyAccrualReport studyAccrualReportObject = null;
        	 studyAccrualReportObject = (StudyAccrualReport) studyAccrualReports.get(i).clone();
        	 studyAccrualReportObjects.add(studyAccrualReportObject);
         }
         return studyAccrualReportObjects;
    }
    
    public void countAccrual()
    {
        int accrual = 0;
        List<DiseaseSiteAccrualReport> diseaseSites = getDiseaseSiteAccrualReports();
        if (diseaseSites != null) {
            for (DiseaseSiteAccrualReport diseaseSite : diseaseSites) {
                accrual += diseaseSite.getAccrual().getValue();
            }
        }
        setAccrual(new Accrual(accrual));
    }

    public void filterForDiseaseSite(DiseaseSiteAccrualReport diseaseSiteAccrualReport)
    {
        if (diseaseSiteAccrualReport == null || diseaseSiteAccrualReport.getName() == null) return;
        
        List<DiseaseSiteAccrualReport> filteredDiseaseSiteAccrualReports = new ArrayList<DiseaseSiteAccrualReport>();
        
        for (DiseaseSiteAccrualReport myDiseaseSiteAccrualReport : getDiseaseSiteAccrualReports()) {
            if (diseaseSiteAccrualReport.getName().equals(myDiseaseSiteAccrualReport.getName())) {
            	filteredDiseaseSiteAccrualReports.add(myDiseaseSiteAccrualReport);
                return;
            }
        }
        this.setDiseaseSiteAccrualReports(filteredDiseaseSiteAccrualReports);
    }
    
    public void print(PrintStream out, int indent)
    {
        char[] tabChars = new char[indent];
        for (int i = 0; i < indent; i++) tabChars[i] = ' ';
        String tab = String.valueOf(tabChars);
        
        out.println(tab + "identifier=" + getIdentifier());
        out.println(tab + "shortTitle=" + getShortTitle());

        Accrual acc = getAccrual();
        if (acc == null) {
            out.println(tab + "accrual=null");
        } else {
            out.println(tab + "accrual=");
            acc.print(out, indent+3);
        }
        
        List<DiseaseSiteAccrualReport> diseaseSiteAccrualReports = getDiseaseSiteAccrualReports();
        if (diseaseSiteAccrualReports == null) {
            out.println(tab + "diseaseSites=null");
        } else {
            out.println(tab + "diseaseSites=");    
            for (DiseaseSiteAccrualReport diseaseSite : diseaseSiteAccrualReports) {
                diseaseSite.print(out, indent+3);
            }
        }
    }
    
    public void addDiseaseSiteAccrualReport(DiseaseSiteAccrualReport diseaseSiteAccrualReport){
    	if(this.getDiseaseSiteAccrualReports()!=null){
    		this.getDiseaseSiteAccrualReports().add(diseaseSiteAccrualReport);
    	}
    }
}
