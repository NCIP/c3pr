package edu.duke.cabig.c3pr.web.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

public class AdvanceSearchResultsExportController extends AbstractCommandController{
	
	private static final String XLS_SEARCH_RESULTS_FILENAME = "xlsSearchResults.xls";
	
	private void generateOutput(HttpServletResponse response)throws Exception{
		
		String tempDir = System.getProperty("java.io.tmpdir");
		File file = new File(tempDir+File.separator+XLS_SEARCH_RESULTS_FILENAME);
		FileInputStream fileIn = new FileInputStream(file);
		
		response.setContentType("application/x-download");
		response.setHeader("Content-Disposition", "attachment; filename=" + XLS_SEARCH_RESULTS_FILENAME);
		response.setHeader("Content-length", String.valueOf(file.length()));
		response.setHeader("Pragma", "private");
		response.setHeader("Cache-control","private, must-revalidate");
		
		OutputStream out = response.getOutputStream();
		
		byte[] buffer = new byte[2048];
		int bytesRead = fileIn.read(buffer);
		while (bytesRead >= 0) {
		  if (bytesRead > 0)
		    out.write(buffer, 0, bytesRead);
		    bytesRead = fileIn.read(buffer);
		}
		out.flush();
		out.close();
		fileIn.close();
		
	}
	
	@Override
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object arg2, BindException arg3) throws Exception {
		AdvancedSearchCommand command = (AdvancedSearchCommand) arg2;
		//initializeCommandObject(command);
		//createQueryAndFetchData(command);
		List<AdvancedSearchRow> searchResultsRowList = (List<AdvancedSearchRow>) request.getSession().getAttribute("searchResultsRowList");
		List<ViewColumn> resultsViewColumnList = (List<ViewColumn>) request.getSession().getAttribute("resultsViewColumnList");
		createExcelFile(searchResultsRowList, resultsViewColumnList);
		generateOutput(response);
		return null;
	}
	
	private void createExcelFile(List<AdvancedSearchRow> searchResultsRowList, List<ViewColumn> resultsViewColumnList) throws Exception{
		String tempDir = System.getProperty("java.io.tmpdir");
		HSSFWorkbook wb = new HSSFWorkbook();
	    HSSFSheet sheet = wb.createSheet("Search Results");

	    // Create a row and put some cells in it. Rows are 0 based.
	    HSSFRow row = sheet.createRow((short)0);

	    int viewColumnCount = 0;
	    for(ViewColumn viewColumn: resultsViewColumnList){
	    	row.createCell((short) viewColumnCount).setCellValue(viewColumn.getColumnTitle());
	    	viewColumnCount++;
	    }
	    
	    int rowCount = 1;
	    for(AdvancedSearchRow searchRow: searchResultsRowList){
	    	int columnCount = 0;
	    	row = sheet.createRow((short) rowCount++);
	    	for(AdvancedSearchColumn searchColumn: searchRow.getColumnList()){
	    		if(searchColumn.getValue() != null)
	    			row.createCell((short) columnCount++).setCellValue(searchColumn.getValue().toString());
	    		else
	    			row.createCell((short) columnCount++).setCellValue("");
	    	}
	    }

	    // Write the output to a file
	    FileOutputStream fileOut = new FileOutputStream(tempDir + File.separator + XLS_SEARCH_RESULTS_FILENAME);
	    wb.write(fileOut);
	    fileOut.close();
	}
}
