package edu.duke.cabig.c3pr.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;

public class XMLUtils {

	public static String toXml(StudyParticipantAssignment studyParticipantAssignment)throws RuntimeException{
		String ns="http://semanticbits.com/registration.xsd";
		String xml="";
		Element rootElement=new Element("registration","p1", ns);
		rootElement.setAttribute("healthCareSiteGridId", studyParticipantAssignment.getStudySite().getSite().getGridId());
		rootElement.setAttribute("studyGridId", studyParticipantAssignment.getStudySite().getStudy().getGridId());
//		String siteGridId=StringUtils.getBlankIfNull(studyParticipantAssignment.getStudySite().getGridId());
//		String studyGridId=StringUtils.getBlankIfNull(studyParticipantAssignment.getStudySite().getStudy().getGridId());
		
//		rootElement.setAttribute("healthCareSiteGridId", "gridSite");
//		rootElement.setAttribute("studyGridId", "gridStudy");
		
		rootElement.addContent(new Element("informedConsentFormSignedDate", "p1",ns).setText(new SimpleDateFormat("yyyy-MM-dd").format(studyParticipantAssignment.getInformedConsentSignedDate())));	
		rootElement.addContent(new Element("enrollmentDate", "p1",ns).setText(new SimpleDateFormat("yyyy-MM-dd").format(studyParticipantAssignment.getStartDate())));		
		rootElement.addContent(new Element("studyParticipantIdentifier", "p1",ns).setText(StringUtils.getBlankIfNull(studyParticipantAssignment.getGridId())));
		rootElement.addContent(new Element("eligibilityIndicator", "p1",ns).setText(StringUtils.getBlankIfNull(studyParticipantAssignment.getEligibilityIndicator())));
		
		Participant stPart=studyParticipantAssignment.getParticipant();
		Element participant=new Element("participant", "p1",ns);
		participant.setAttribute("participantGridId", StringUtils.getBlankIfNull(stPart.getGridId()));
		participant.addContent(new Element("administrativeGenderCode", "p1",ns).setText(StringUtils.getBlankIfNull(stPart.getAdministrativeGenderCode())));
		participant.addContent(new Element("birthDate", "p1",ns).setText(new SimpleDateFormat("yyyy-MM-dd").format(stPart.getBirthDate())));
		participant.addContent(new Element("ethnicGroupCode", "p1",ns).setText(StringUtils.getBlankIfNull(stPart.getEthnicGroupCode())));
		participant.addContent(new Element("firstName", "p1",ns).setText(StringUtils.getBlankIfNull(stPart.getFirstName())));
		participant.addContent(new Element("lastName", "p1",ns).setText(StringUtils.getBlankIfNull(stPart.getLastName())));
		participant.addContent(new Element("maritalStatusCode", "p1",ns).setText(""));
		participant.addContent(new Element("raceCode", "p1",ns).setText(StringUtils.getBlankIfNull(stPart.getRaceCode())));
		List<Identifier> identifiers= stPart.getIdentifiers();
		if(identifiers.size()==0){
			System.out.println("Participant Identifiers size is 0");
			Element idTemp=new Element("identifier", "p1",ns);
			idTemp.addContent(new Element("source", "p1",ns).setText("NONE"));
			idTemp.addContent(new Element("type", "p1",ns).setText("NONE"));
			idTemp.addContent(new Element("value", "p1",ns).setText("NONE"));
			idTemp.addContent(new Element("isprimary", "p1",ns).setText("true"));
			participant.addContent(idTemp);
		}		
		for(int i=0 ; i<identifiers.size() ; i++){
			Identifier id=identifiers.get(i);
			Element idTemp=new Element("identifier", "p1",ns);
			idTemp.addContent(new Element("source", "p1",ns).setText(StringUtils.getBlankIfNull(id.getSource())));
			idTemp.addContent(new Element("type", "p1",ns).setText(StringUtils.getBlankIfNull(id.getType())));
			idTemp.addContent(new Element("value", "p1",ns).setText(StringUtils.getBlankIfNull(id.getValue())));
			idTemp.addContent(new Element("isprimary", "p1",ns).setText(StringUtils.getBlankIfNull(id.getPrimaryIndicator())));
			participant.addContent(idTemp);
		}
		Address add=stPart.getAddress();
		Element address=new Element("address", "p1",ns);
		address.addContent(new Element("city", "p1",ns).setText(StringUtils.getBlankIfNull(add.getCity())));
		address.addContent(new Element("countryCode", "p1",ns).setText(StringUtils.getBlankIfNull(add.getCountryCode())));
		address.addContent(new Element("postalCode", "p1",ns).setText(StringUtils.getBlankIfNull(add.getPostalCode())));
		address.addContent(new Element("stateCode", "p1",ns).setText(StringUtils.getBlankIfNull(add.getStateCode())));
		address.addContent(new Element("streetAddress", "p1",ns).setText(StringUtils.getBlankIfNull(add.getStreetAddress())));
		participant.addContent(address);
		rootElement.addContent(participant);
		
		Study st=studyParticipantAssignment.getStudySite().getStudy();
		Element study=new Element("study", "p1",ns);
		identifiers= st.getIdentifiers();
		if(identifiers.size()==0){
			System.out.println("Study Identifiers size is 0");
			Element idTemp=new Element("identifier", "p1",ns);
			idTemp.addContent(new Element("source", "p1",ns).setText("NONE"));
			idTemp.addContent(new Element("type", "p1",ns).setText("NONE"));
			idTemp.addContent(new Element("value", "p1",ns).setText("NONE"));
			idTemp.addContent(new Element("isprimary", "p1",ns).setText("true"));
			study.addContent(idTemp);
		}
		for(int i=0 ; i<identifiers.size() ; i++){
			Identifier id=identifiers.get(i);
			Element idTemp=new Element("identifier", "p1",ns);
			idTemp.addContent(new Element("source", "p1",ns).setText(StringUtils.getBlankIfNull(id.getSource())));
			idTemp.addContent(new Element("type", "p1",ns).setText(StringUtils.getBlankIfNull(id.getType())));
			idTemp.addContent(new Element("value", "p1",ns).setText(StringUtils.getBlankIfNull(id.getValue())));
			idTemp.addContent(new Element("isprimary", "p1",ns).setText(StringUtils.getBlankIfNull(id.getPrimaryIndicator())));
			study.addContent(idTemp);
		}
		rootElement.addContent(study);
		
		Element identifier=new Element("identifier", "p1",ns);
		identifier.addContent(new Element("source", "p1",ns).setText("c3pr"));
		identifier.addContent(new Element("type", "p1",ns).setText("Grid Identifier"));
		identifier.addContent(new Element("value", "p1",ns).setText(StringUtils.getBlankIfNull(studyParticipantAssignment.getGridId())));
		identifier.addContent(new Element("isprimary", "p1",ns).setText("false"));
		rootElement.addContent(identifier);
		identifiers=studyParticipantAssignment.getIdentifiers();
		for(int i=0 ; i<identifiers.size() ; i++){
			Identifier id=identifiers.get(i);
			Element idTemp=new Element("identifier", "p1",ns);
			idTemp.addContent(new Element("source", "p1",ns).setText(StringUtils.getBlankIfNull(id.getSource())));
			idTemp.addContent(new Element("type", "p1",ns).setText(StringUtils.getBlankIfNull(id.getType())));
			idTemp.addContent(new Element("value", "p1",ns).setText(StringUtils.getBlankIfNull(id.getValue())));
			idTemp.addContent(new Element("isprimary", "p1",ns).setText(StringUtils.getBlankIfNull(id.getPrimaryIndicator())));
			rootElement.addContent(idTemp);
		}
		
		Document document= new Document(rootElement);
		try {
			xml=new XMLOutputter().outputString(document);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}
}
