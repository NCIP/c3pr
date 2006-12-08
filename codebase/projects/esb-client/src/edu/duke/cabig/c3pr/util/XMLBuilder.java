package edu.duke.cabig.c3pr.util;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import edu.duke.cabig.c3pr.domain.Study;

public class XMLBuilder {
	public String buildCreateProtocolXML(Study studyObj){
		String xml="";
		Element study=new Element("study");
		Document document=new Document(study);
		study.addContent(new Element("id").setText(studyObj.getId()+""));		
		study.addContent(new Element("blinded-indicator").setText(studyObj.getBlindedIndicator()));
		study.addContent(new Element("description-text").setText(studyObj.getDescriptionText()));
		study.addContent(new Element("disease-code").setText(studyObj.getDiseaseCode()));
		study.addContent(new Element("long-title-text").setText(studyObj.getLongTitleText()));
		study.addContent(new Element("monitor-code").setText(studyObj.getMonitorCode()));
		study.addContent(new Element("nci-identifier").setText(studyObj.getNciIdentifier()));
		study.addContent(new Element("phase-code").setText(studyObj.getPhaseCode()));
		study.addContent(new Element("precis-text").setText(studyObj.getPrecisText()));
		study.addContent(new Element("short-title-text").setText(studyObj.getShortTitleText()));
		study.addContent(new Element("sponsor-code").setText(studyObj.getSponsorCode()));
		study.addContent(new Element("status").setText(studyObj.getStatus()));
		study.addContent(new Element("type").setText(studyObj.getType()));
		study.addContent(new Element("target-accrual-number").setText(""+studyObj.getTargetAccrualNumber()));
		
		try {
		    xml=new XMLOutputter().outputString(document);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		System.out.println("Study XML built as-");
		System.out.println(xml);
		return xml;
	}
}
