package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.domain.Lov;

/**
 * Temporary domain object to hold values of static data loaded from CADsr
 * @author Priyatam
 *
 */
//TODO Remove this class when a service for loading static data from CADsr exists
public class CaDSRDataDao {
	
	/**
	 * Returns a list of CaDSRDatas based on the Id passed
	 * @param code
	 * @param desc
	 * @return the list of values (presumably from CADsr :))
	 */
	public List getCADsrData(String id)
	{
		if (("diseaseCode").equals(id))
			return getDiseaseCodeList();
		if (("monitorCode").equals(id))
			return getMonitorCodeList();
		if (("phaseCode").equals(id))
			return getPhaseCodeList();
		if (("sponsorCode").equals(id))
			return getSponsorCodeList();
		if (("status").equals(id))
			return getStatusList();
		if (("type").equals(id))
			return getTypeCodeList();
		else		
			return createDefaultList();			
	}
	
	private List<Lov> getDiseaseCodeList(){
		Lov dataCol = new Lov();
		
		dataCol.addData("A", "AIDS");
		dataCol.addData("B", "Benign");
		dataCol.addData("C", "Cancer");		
    	
    	return dataCol.getData();
	}

	private List<Lov> getMonitorCodeList(){
		Lov dataCol = new Lov();
		
		dataCol.addData("CTEP", "Cancer Therapy Evaluation Program");
		dataCol.addData("Pharma", "Genetic Non-therapeutic");
		dataCol.addData("Internal", "Genetic Therapeutic");
		dataCol.addData("CTEP - CTMS", "CTEP - Clinical Data Update System Complete");
		dataCol.addData("CTEP - CDUS Abbreviated", "CTEP - Clinical Data Update System Abbreviated");	
    	
    	return dataCol.getData();
	}		
	
	private List<Lov> getTypeCodeList(){		
		Lov dataCol = new Lov();
		
		dataCol.addData("D", "Diagnostic");
		dataCol.addData("GN", "Genetic Non-therapeutic");
		dataCol.addData("GT", "Genetic Therapeutic");
		dataCol.addData("P", "Primary Treatment");
		dataCol.addData("S", "Supportive");
		dataCol.addData("T", "Preventive");
    	
    	return dataCol.getData();
	}
	
	private List<Lov> getMonitorList(){
		Lov dataCol = new Lov();
		
		dataCol.addData("I", "Phase I Trial");
		dataCol.addData("I/II", "Phase I/II Trial");
		dataCol.addData("III", "Phase III Trial");
		dataCol.addData("IV", "Phase IV Trial");
		dataCol.addData("IV", "Phase IV Trial");
    	
    	return dataCol.getData();
	}
	
	private List<Lov> getPhaseCodeList(){
		Lov dataCol = new Lov();
		
		dataCol.addData("I", "Phase I Trial");
		dataCol.addData("I/II", "Phase I/II Trial");
		dataCol.addData("III", "Phase III Trial");
		dataCol.addData("IV", "Phase IV Trial");
		dataCol.addData("IV", "Phase IV Trial");
    	
    	return dataCol.getData();
	}
	
	private List<Lov> getSponsorCodeList(){
		Lov dataCol = new Lov();
		
		dataCol.addData("AB", "Abbott Labs");
		dataCol.addData("AL", "Alkermes, Inc.");
		dataCol.addData("APH", "Angiotech");
		dataCol.addData("AM", "Amgen");
		dataCol.addData("THN", "Therion");
		dataCol.addData("TW", "Thomas Waldmann, MD");
		dataCol.addData("TX", "Texcellon");
		dataCol.addData("US", "US Biosciences");
		dataCol.addData("VI", "Vion Pharmaceuticals");
		dataCol.addData("WCE", "W.C. Eckelman, MD");
    	
    	return dataCol.getData();
	}
	
	private List<Lov> getStatusList(){
		Lov dataCol = new Lov();
		
		dataCol.addData("AC", "Active - Trial is open to accrual");
		dataCol.addData("AD", "Administratively Complete");
		dataCol.addData("AP", "AP");
		dataCol.addData("CB", "Closed to Accrual & Treatment");
		dataCol.addData("CL", "Closed to Accrual");
		dataCol.addData("TB", "Temporarily Closed to Accrual & Treatment");
		dataCol.addData("TC", "Temporarily Closed to Accrual -");	
		
    	return dataCol.getData();
	}

	private List<Lov> createDefaultList(){
		List<Lov> col = new ArrayList<Lov>();
		Lov CaDSRData1 = new Lov("D", "Default-List");
		col.add(CaDSRData1);
    	return col;		
	}

}
