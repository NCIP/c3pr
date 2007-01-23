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
		rootElement.setAttribute("healthCareSiteGridId", "");
		rootElement.setAttribute("studyGridId", "");
		rootElement.addContent(new Element("eligibilityWaiverReasonText", "p1",ns).setText(studyParticipantAssignment.getEligibilityWaiverReasonText()));
		rootElement.addContent(new Element("informedConsentFormSignedDate", "p1",ns).setText(new SimpleDateFormat("yyyy-MM-dd").format(studyParticipantAssignment.getInformedConsentSignedDate())));	
		rootElement.addContent(new Element("offStudyDate", "p1",ns).setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));		
		rootElement.addContent(new Element("offStudyReasonCode", "p1",ns).setText(""));		
		rootElement.addContent(new Element("offStudyReasonOtherText", "p1",ns).setText(""));		
		rootElement.addContent(new Element("startDate", "p1",ns).setText(new SimpleDateFormat("yyyy-MM-dd").format(studyParticipantAssignment.getStartDate())));		
		rootElement.addContent(new Element("studyParticipantIdentifier", "p1",ns).setText(studyParticipantAssignment.getStudyParticipantIdentifier()));		
		rootElement.addContent(new Element("eligibilityIndicator", "p1",ns).setText(studyParticipantAssignment.getEligibilityIndicator().toString()));	
		
		Participant stPart=studyParticipantAssignment.getParticipant();
		Element participant=new Element("participant", "p1",ns);
		participant.setAttribute("participantGridId", "");
		participant.addContent(new Element("administrativeGenderCode", "p1",ns).setText(stPart.getAdministrativeGenderCode()));
		participant.addContent(new Element("birthDate", "p1",ns).setText(new SimpleDateFormat("yyyy-MM-dd").format(stPart.getBirthDate())));
		participant.addContent(new Element("ethnicGroupCode", "p1",ns).setText(stPart.getEthnicGroupCode()));
		participant.addContent(new Element("firstName", "p1",ns).setText(stPart.getFirstName()));
		participant.addContent(new Element("lastName", "p1",ns).setText(stPart.getLastName()));
		participant.addContent(new Element("maritalStatusCode", "p1",ns).setText(""));
		participant.addContent(new Element("raceCode", "p1",ns).setText(stPart.getRaceCode()));
		
		List<Identifier> identifiers= stPart.getIdentifiers();
		if(identifiers.size()==0){
			System.out.println("Participant Identifiers size is 0");
			Element idTemp=new Element("identifier", "p1",ns);
			idTemp.addContent(new Element("source", "p1",ns));
			idTemp.addContent(new Element("type", "p1",ns));
			idTemp.addContent(new Element("value", "p1",ns));
			idTemp.addContent(new Element("isprimary", "p1",ns));
			participant.addContent(idTemp);
		}		
		for(int i=0 ; i<identifiers.size() ; i++){
			Identifier id=identifiers.get(i);
			Element idTemp=new Element("identifier", "p1",ns);
			idTemp.addContent(new Element("source", "p1",ns).setText(id.getSource()));
			idTemp.addContent(new Element("type", "p1",ns).setText(id.getType()));
			idTemp.addContent(new Element("value", "p1",ns).setText(id.getValue()));
			idTemp.addContent(new Element("isprimary", "p1",ns).setText(id.getPrimaryIndicator()==null?"":id.getPrimaryIndicator().toString()));
			participant.addContent(idTemp);
		}
		Address add=stPart.getAddress();
		Element address=new Element("address", "p1",ns);
		address.addContent(new Element("city", "p1",ns).setText(add.getCity()));
		address.addContent(new Element("countryCode", "p1",ns).setText(add.getCountryCode()));
		address.addContent(new Element("postalCode", "p1",ns).setText(add.getPostalCode()));
		address.addContent(new Element("stateCode", "p1",ns).setText(add.getStateCode()));
		address.addContent(new Element("streetAddress", "p1",ns).setText(add.getStreetAddress()));
		participant.addContent(address);
		rootElement.addContent(participant);
		
		Study st=studyParticipantAssignment.getStudySite().getStudy();
		Element study=new Element("study", "p1",ns);
		identifiers= st.getIdentifiers();
		if(identifiers.size()==0){
			System.out.println("Study Identifiers size is 0");
			Element idTemp=new Element("identifier", "p1",ns);
			idTemp.addContent(new Element("source", "p1",ns));
			idTemp.addContent(new Element("type", "p1",ns));
			idTemp.addContent(new Element("value", "p1",ns));
			idTemp.addContent(new Element("isprimary", "p1",ns));
			study.addContent(idTemp);
		}
		for(int i=0 ; i<identifiers.size() ; i++){
			Identifier id=identifiers.get(i);
			Element idTemp=new Element("identifier", "p1",ns);
			idTemp.addContent(new Element("source", "p1",ns).setText(id.getSource()));
			idTemp.addContent(new Element("type", "p1",ns).setText(id.getType()));
			idTemp.addContent(new Element("value", "p1",ns).setText(id.getValue()));
			idTemp.addContent(new Element("isprimary", "p1",ns).setText(id.getPrimaryIndicator()==null?"":id.getPrimaryIndicator().toString()));
			study.addContent(idTemp);
		}
		rootElement.addContent(study);
		
		Element identifier=new Element("identifier", "p1",ns);
		identifier.addContent(new Element("source", "p1",ns).setText("c3pr"));
		identifier.addContent(new Element("type", "p1",ns).setText("GridId"));
		identifier.addContent(new Element("value", "p1",ns).setText(studyParticipantAssignment.getGridId()));
		identifier.addContent(new Element("isprimary", "p1",ns));
		rootElement.addContent(identifier);
		identifiers=studyParticipantAssignment.getIdentifiers();
		for(int i=0 ; i<identifiers.size() ; i++){
			Identifier id=identifiers.get(i);
			Element idTemp=new Element("identifier", "p1",ns);
			idTemp.addContent(new Element("source", "p1",ns).setText(id.getSource()));
			idTemp.addContent(new Element("type", "p1",ns).setText(id.getType()));
			idTemp.addContent(new Element("value", "p1",ns).setText(id.getValue()));
			idTemp.addContent(new Element("isprimary", "p1",ns).setText(id.getPrimaryIndicator()==null?"":id.getPrimaryIndicator().toString()));
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
