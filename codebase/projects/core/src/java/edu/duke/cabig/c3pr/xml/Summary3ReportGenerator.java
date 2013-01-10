/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFTextbox;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.util.CellRangeAddress;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.duke.cabig.c3pr.utils.XsltTransformer;

public class Summary3ReportGenerator {

	protected final Log log = LogFactory.getLog(getClass());

	private String xslFOXsltFile = "summary3Report.xslt";

	public void generatePdf(String summary3ReportXml, String pdfOutFileName)
			throws Exception {
		XsltTransformer xsltTrans = new XsltTransformer();
		xsltTrans.toPdf(summary3ReportXml, addExtension(pdfOutFileName, "PDF"), xslFOXsltFile);
	}

	public void generateExcel(String summary3ReportXml, String file)
			throws Exception {

		// creating the workbook and the spreadsheet
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			String fileName = "";

			fileName = file;

			InputStream inputStream = new FileInputStream((fileName + ".xml"));
			Document document = builder.parse(inputStream);
			
			HSSFWorkbook wb = new HSSFWorkbook();

			HSSFSheet spreadSheet = wb.createSheet("Sumary 3 Report");

			PrintSetup printSetup = spreadSheet.getPrintSetup();
			printSetup.setLandscape(true);
			spreadSheet.setFitToPage(true);
			spreadSheet.setHorizontallyCenter(true);

			spreadSheet.setColumnWidth((short) 0, (short) (60 * 256));
			spreadSheet.setColumnWidth((short) 1, (short) (15 * 256));
			spreadSheet.setColumnWidth((short) 2, (short) (30 * 256));

			HSSFRow titleRow = spreadSheet.createRow(0);
			HSSFCell titleCell = titleRow.createCell((short) 0);
			titleRow.setHeightInPoints(40);

			HSSFCellStyle titleCellStyle = wb.createCellStyle();
			titleCellStyle.setAlignment(titleCellStyle.ALIGN_CENTER_SELECTION);
			titleCellStyle.setVerticalAlignment(titleCellStyle.VERTICAL_CENTER);
			titleCell.setCellStyle(titleCellStyle);

			String nullSafeGrantNumber = (document.getElementsByTagName("grantNumber").item(0)!= null && document.getElementsByTagName("grantNumber").item(0).getFirstChild() != null )? (document.getElementsByTagName("grantNumber")).item(0).getFirstChild().getNodeValue():"";
			titleCell
					.setCellValue("Summary 3: Reportable Patients/Participation "
							+ "in Therapeutic Protocols" + "                      " + nullSafeGrantNumber);


			HSSFRow orgRow = spreadSheet.createRow(1);
			orgRow.setHeightInPoints(30);
			HSSFCell organizationCell = orgRow.createCell((short) 0);

			HSSFCellStyle orgCellStyle = wb.createCellStyle();
			orgCellStyle.setAlignment(titleCellStyle.ALIGN_CENTER_SELECTION);
			HSSFFont font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			orgCellStyle.setFont(font);
			organizationCell.setCellStyle(orgCellStyle);
			organizationCell.setCellValue(((Element) (document
					.getElementsByTagName("reportingOrganization").item(0)))
					.getElementsByTagName("name").item(0).getFirstChild()
					.getNodeValue());

			HSSFRow reportingPeriodRow = spreadSheet.createRow(2);
			HSSFFont reportingPeriodFont = wb.createFont();
			reportingPeriodFont.setFontHeightInPoints((short) 9);
			HSSFCellStyle reportingPeriodStyle = wb.createCellStyle();
			reportingPeriodStyle.setFont(reportingPeriodFont);

			reportingPeriodRow.setHeightInPoints(20);
			HSSFCell reportingPeriodCell = reportingPeriodRow
					.createCell((short) 0);
			reportingPeriodCell.setCellStyle(titleCellStyle);

			reportingPeriodCell.setCellValue("Reporting Period "
					+ (document.getElementsByTagName("startDate").item(0)
							.getFirstChild().getNodeValue())
					+ " - "
					+ (document.getElementsByTagName("endDate").item(0)
							.getFirstChild().getNodeValue()));

			// creating the first row of table the table header
			HSSFRow row = spreadSheet.createRow(3);
			HSSFCell tableHeaderCell1 = row.createCell((short) 0);
			HSSFCellStyle tableHeaderCellStyle1 = wb.createCellStyle();
			tableHeaderCellStyle1.setWrapText(true);

			tableHeaderCellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);
			tableHeaderCellStyle1.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
			tableHeaderCellStyle1.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
			tableHeaderCellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);

			tableHeaderCellStyle1.setFont(font);
			tableHeaderCell1.setCellStyle(tableHeaderCellStyle1);
			tableHeaderCell1.setCellValue("Disease Site");

			// creating table header 2nd & 3rd cells

			HSSFCell tableHeaderCell2 = row.createCell((short) 1);
			HSSFCellStyle tableHeaderCellStyle2 = wb.createCellStyle();

			tableHeaderCellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			tableHeaderCellStyle2.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
			tableHeaderCellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			tableHeaderCellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			tableHeaderCellStyle2.setWrapText(true);

			tableHeaderCell2.setCellStyle(tableHeaderCellStyle2);
			tableHeaderCell2.setCellValue("Newly Registered Patients");

			HSSFCell tableHeaderCell3 = row.createCell((short) 2);

			HSSFCellStyle tableHeaderCellStyle3 = wb.createCellStyle();

			tableHeaderCellStyle3.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
			tableHeaderCellStyle3.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
			tableHeaderCellStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			tableHeaderCellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			tableHeaderCellStyle3.setWrapText(true);
			tableHeaderCell3.setCellStyle(tableHeaderCellStyle3);
			tableHeaderCell3
					.setCellValue("Total patients newly enrolled in therapeutic protocols");

			NodeList nodeList = document.getElementsByTagName("reportData");

			HSSFCellStyle tableCellStyle = wb.createCellStyle();
			tableCellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
			tableCellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
			tableCellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
			tableCellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);

			spreadSheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$C$1"));
			spreadSheet.addMergedRegion(CellRangeAddress.valueOf("$A$2:$C$2"));
			spreadSheet.addMergedRegion(CellRangeAddress.valueOf("$A$3:$C$3"));
			for (int i = 4; i < nodeList.getLength() + 4; i++) {

				row = spreadSheet.createRow(i);
				HSSFCell cell = row.createCell((short) 0);
				if (i == (4 + nodeList.getLength() - 1)) {
					HSSFCellStyle totalCellStyle = wb.createCellStyle();
					totalCellStyle.setFont(font);
					totalCellStyle.setRightBorderColor((short) 10);
					totalCellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
					tableCellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
					totalCellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
					totalCellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
					cell.setCellStyle(totalCellStyle);
				} else {
					cell.setCellStyle(tableCellStyle);
				}
				cell.setCellValue(((Element) ((Element) (nodeList.item(i - 4)))
						.getElementsByTagName("key").item(0))
						.getAttribute("name"));

				cell = row.createCell((short) 1);
				cell.setCellStyle(tableCellStyle);

				cell.setCellValue("");

				cell = row.createCell((short) 2);
				cell.setCellStyle(tableCellStyle);

				cell
						.setCellValue(((Element) (((Element) (nodeList
								.item(i - 4))).getElementsByTagName("value")
								.item(3))).getFirstChild().getNodeValue());
			}
			FileOutputStream output = new FileOutputStream(new File(addExtension(file, "Excel")));
			wb.write(output);
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	public String addExtension(String file, String format) {
		if (format.equals("PDF")) {
			file = file + ".pdf";
		} else if (format.equals("Excel")) {
			file = file + ".xls";
		}

		return file;
	}

}
