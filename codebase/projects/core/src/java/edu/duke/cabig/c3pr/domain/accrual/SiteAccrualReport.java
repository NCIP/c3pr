package edu.duke.cabig.c3pr.domain.accrual;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class SiteAccrualReport {
	 	private String name;
	    private String ctepId;
	    private String address = null;
	    private Double lat = null;
	    private Double lng = null;
	    private Accrual accrual;
	    private List<StudyAccrualReport> studyAccrualReports;
	    
	    public SiteAccrualReport()
	    {
	        super();
	    }
	    
	    public SiteAccrualReport(String name, String ctepId)
	    {
	        this();
	        
	        setName(name);
	        setCtepId(ctepId);
	    }
	    
	    public String getName()
	    {
	        return name;
	    }
	    public void setName(String name)
	    {
	        this.name = name;
	    }
	    public String getCtepId()
	    {
	        return ctepId;
	    }
	    public void setCtepId(String ctepId)
	    {
	        this.ctepId = ctepId;
	    }
	    public Double getLat()
	    {
	        return lat;
	    }
	    public void setLat(Double lat)
	    {
	        this.lat = lat;
	    }
	    public Double getLng()
	    {
	        return lng;
	    }
	    public void setLng(Double lng)
	    {
	        this.lng = lng;
	    }
	 
	    public String getAddress()
	    {
	        return address;
	    }

	    public void setAddress(String address)
	    {
	        this.address = address;
	    }

	    public Accrual getAccrual()
	    {
	        return accrual;
	    }

	    public void setAccrual(Accrual accrual)
	    {
	        this.accrual = accrual;
	    }

	    public List<StudyAccrualReport> getStudyAccrualReports() {
			return studyAccrualReports;
		}

		public void setStudyAccrualReports(List<StudyAccrualReport> studyAccrualReports) {
			this.studyAccrualReports = studyAccrualReports;
		}

		public StudyAccrualReport getStudyAccrualReport(String identifier)
	    {
			List<StudyAccrualReport> studyAccrualReports = getStudyAccrualReports();
	        if (studyAccrualReports == null) return null;
	        for (StudyAccrualReport studyAccrualReport : studyAccrualReports) if (identifier.equals(studyAccrualReport.getIdentifier())) return studyAccrualReport;
	        return null;
	    }

	    @Override
	    protected Object clone()
	    {
	        SiteAccrualReport obj = new SiteAccrualReport(getName(), getCtepId());
	        obj.setLat(getLat());
	        obj.setLng(getLat());
	        obj.setAddress(getAddress());
	        Accrual totalAccrual = getAccrual();
	        if (totalAccrual != null) obj.setAccrual((Accrual) totalAccrual.clone());
	        obj.setStudyAccrualReports(StudyAccrualReport.clone(getStudyAccrualReports()));
	        return obj;
	    }

	    public static List<SiteAccrualReport> clone(List<SiteAccrualReport> siteAccrualReports)   {
	    	 List<SiteAccrualReport> siteAccrualReportObjects = new ArrayList<SiteAccrualReport>();
	         for (int i = 0; i < siteAccrualReports.size(); i++) {
	        	 SiteAccrualReport siteAccrualReportObject = null;
	        	 siteAccrualReportObject = (SiteAccrualReport) siteAccrualReports.get(i).clone();
	        	 siteAccrualReportObjects.add(siteAccrualReportObject);
	         }
	         return siteAccrualReportObjects;
	    }

	    public void countAccrual()
	    {
	        int accrual = 0;
	        List<StudyAccrualReport> studyAccrualReports = getStudyAccrualReports();
	        if (studyAccrualReports != null) {
	            for (StudyAccrualReport studyAccrualReport : studyAccrualReports) {
	                studyAccrualReport.countAccrual();
	                accrual += studyAccrualReport.getAccrual().getValue();

	           }
	        }
	        setAccrual(new Accrual(accrual));
	    }

	    public void filterForDiseaseSite(DiseaseSiteAccrualReport diseaseSite)
	    {
	        if (diseaseSite == null || diseaseSite.getName() == null) return;
	        
	        List<StudyAccrualReport> studyAccrualReports = getStudyAccrualReports();
	        for (StudyAccrualReport studyAccrualReport : studyAccrualReports) {
	            studyAccrualReport.filterForDiseaseSite(diseaseSite);
	        }
	    }

	    public void filterForStudy(StudyAccrualReport studyAccrualReport)
	    {
	        if (studyAccrualReport == null || studyAccrualReport.getIdentifier() == null) return;
	        List<StudyAccrualReport> filteredStudyAccrualReports = new ArrayList<StudyAccrualReport>();
	        for (StudyAccrualReport myStudyAccrualReport : getStudyAccrualReports()) {
	            if (studyAccrualReport.getIdentifier().equals(myStudyAccrualReport.getIdentifier())) {
	            	filteredStudyAccrualReports.add(myStudyAccrualReport);
	                return;
	            }
	        }
	        this.setStudyAccrualReports(filteredStudyAccrualReports);
	   }

	    public List<DiseaseSiteAccrualReport> findDiseaseSiteAccrualReports()   {
	    	List<DiseaseSiteAccrualReport> foundDiseaseSiteAccrualReports = new ArrayList<DiseaseSiteAccrualReport>();
	        List<StudyAccrualReport> studyAccrualReports = getStudyAccrualReports();
	        if (studyAccrualReports == null) return new ArrayList<DiseaseSiteAccrualReport>();

	        Hashtable<String,DiseaseSiteAccrualReport> dsTable = new Hashtable<String,DiseaseSiteAccrualReport>();
	        for (StudyAccrualReport studyAccrualReport : studyAccrualReports) {
	            List<DiseaseSiteAccrualReport> diseaseSiteAccrualReports = studyAccrualReport.getDiseaseSiteAccrualReports();
	            if (diseaseSiteAccrualReports == null) continue;
	            for (DiseaseSiteAccrualReport diseaseSite : diseaseSiteAccrualReports) {
	                dsTable.put(diseaseSite.getName(), diseaseSite);
	            }
	        }
	        
	        foundDiseaseSiteAccrualReports.addAll(dsTable.values());
	        return foundDiseaseSiteAccrualReports;
	    }
	    
	    public void print(PrintStream out, int indent)
	    {
	        char[] tabChars = new char[indent];
	        for (int i = 0; i < indent; i++) tabChars[i] = ' ';
	        String tab = String.valueOf(tabChars);
	        
	        out.println(tab + "name=" + getName());
	        out.println(tab + "ctepId=" + getCtepId());
	        out.println(tab + "address=" + getAddress());
	        out.println(tab + "lat=" + getLat());
	        out.println(tab + "lng=" + getLng());

	        Accrual acc = getAccrual();
	        if (acc == null) {
	            out.println(tab + "accrual=null");
	        } else {
	            out.println(tab + "accrual=");
	            acc.print(out, indent+3);
	        }
	        
	        List<StudyAccrualReport> studyAccrualReports = getStudyAccrualReports();
	        if (studyAccrualReports == null) {
	            out.println(tab + "studyAccrualReports=null");
	        } else {
	            out.println(tab + "studyAccrualReports=");    
	            for (StudyAccrualReport studyAccrualReport : studyAccrualReports) {
	                studyAccrualReport.print(out, indent+3);
	            }
	        }
	    }


}
