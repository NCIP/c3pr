package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.domain.CaDSRData;

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
		List list = new ArrayList();
		if (("disease").equals(id))
			list = getDiseaseCodeList();
		if (("monitor").equals(id))
			list = getMonitorCodeList();
		if (("phase").equals(id))
			list = getPhaseCodeList();
		if (("sponsor").equals(id))
			list = getSponsorCodeList();
		if (("status").equals(id))
			list = getStatusList();
		if (("type").equals(id))
			list = getTypeCodeList();
		else		
			list = createDefaultList();
		return list;
		
	}
	
	private List<CaDSRData> getDiseaseCodeList(){
		CaDSRData dataCol = new CaDSRData();
		
		dataCol.addData("A", "AIDS");
		dataCol.addData("B", "Benign");
		dataCol.addData("C", "Cancer");		
    	
    	return dataCol.getData();
	}

	private List<CaDSRData> getMonitorCodeList(){
		CaDSRData dataCol = new CaDSRData();
		
		dataCol.addData("CTEP", "Cancer Therapy Evaluation Program");
		dataCol.addData("Pharma", "Genetic Non-therapeutic");
		dataCol.addData("Internal", "Genetic Therapeutic");
		dataCol.addData("CTEP - CTMS", "CTEP - Clinical Data Update System Complete");
		dataCol.addData("CTEP - CDUS Abbreviated", "CTEP - Clinical Data Update System Abbreviated");	
    	
    	return dataCol.getData();
	}		
	
	private List<CaDSRData> getTypeCodeList(){		
		CaDSRData dataCol = new CaDSRData();
		
		dataCol.addData("D", "Diagnostic");
		dataCol.addData("GN", "Genetic Non-therapeutic");
		dataCol.addData("GT", "Genetic Therapeutic");
		dataCol.addData("P", "Primary Treatment");
		dataCol.addData("S", "Supportive");
		dataCol.addData("T", "Preventive");
    	
    	return dataCol.getData();
	}
	
	private List<CaDSRData> getMonitorList(){
		CaDSRData dataCol = new CaDSRData();
		
		dataCol.addData("I", "Phase I Trial");
		dataCol.addData("I/II", "Phase I/II Trial");
		dataCol.addData("III", "Phase III Trial");
		dataCol.addData("IV", "Phase IV Trial");
		dataCol.addData("IV", "Phase IV Trial");
    	
    	return dataCol.getData();
	}
	
	private List<CaDSRData> getPhaseCodeList(){
		CaDSRData dataCol = new CaDSRData();
		
		dataCol.addData("I", "Phase I Trial");
		dataCol.addData("I/II", "Phase I/II Trial");
		dataCol.addData("III", "Phase III Trial");
		dataCol.addData("IV", "Phase IV Trial");
		dataCol.addData("IV", "Phase IV Trial");
    	
    	return dataCol.getData();
	}
	
	private List<CaDSRData> getSponsorCodeList(){
		CaDSRData dataCol = new CaDSRData();
		
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
	
	private List<CaDSRData> getStatusList(){
		CaDSRData dataCol = new CaDSRData();
		
		dataCol.addData("AC", "Active - Trial is open to accrual");
		dataCol.addData("AD", "Administratively Complete");
		dataCol.addData("AP", "AP");
		dataCol.addData("CB", "Closed to Accrual & Treatment");
		dataCol.addData("CL", "Closed to Accrual");
		dataCol.addData("TB", "Temporarily Closed to Accrual & Treatment");
		dataCol.addData("TC", "Temporarily Closed to Accrual -");	
		
    	return dataCol.getData();
	}

	private List<CaDSRData> createDefaultList(){
		List<CaDSRData> col = new ArrayList<CaDSRData>();
		CaDSRData CaDSRData1 = new CaDSRData("D", "Default-List");
		col.add(CaDSRData1);
    	return col;		
	}

}
