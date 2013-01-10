/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.axis.message.MessageElement;
import org.easymock.classextension.EasyMock;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.Response;
import gov.nih.nci.caxchange.TargetResponseMessage;

public class MedidataPatientPositionResponseHandlerTestCase extends AbstractTestCase{

	private StudySubjectRepository studySubjectRepository;
	
	private MedidataPatientPositionResponseHandler medidataPatientPositionResponseHandler;
	
	public MedidataPatientPositionResponseHandlerTestCase() {
		studySubjectRepository = registerMockFor(StudySubjectRepository.class);
		medidataPatientPositionResponseHandler = new MedidataPatientPositionResponseHandler();
		medidataPatientPositionResponseHandler.setStudySubjectRepositoryNew(studySubjectRepository);
	}
	
	public void testCaXchangeResponseUnMarshalling(){
		Response response = new Response();
		TargetResponseMessage targetResponseMessage = new TargetResponseMessage();
		response.setTargetResponse(new TargetResponseMessage[]{targetResponseMessage});
		targetResponseMessage.setTargetServiceIdentifier(MedidataPatientPositionResponseHandler.MEDIDATA_SERVICE_IDENTIFIER);
		MessagePayload messagePayload = new MessagePayload();
		messagePayload.set_any(createMessageElementsFromXML("caXchangeResponseMessage.xml"));
		targetResponseMessage.setTargetBusinessMessage(messagePayload);
		studySubjectRepository.assignMedidataIdentifier(EasyMock.isA(StudySubject.class), EasyMock.isA(String.class));
		replayMocks();
		medidataPatientPositionResponseHandler.processResponse("some grid id", response);
		verifyMocks();
	}
	
	public void testCaXchangeResponseUnMarshallingMedidataIdenifierAbsent(){
		Response response = new Response();
		TargetResponseMessage targetResponseMessage = new TargetResponseMessage();
		response.setTargetResponse(new TargetResponseMessage[]{targetResponseMessage});
		targetResponseMessage.setTargetServiceIdentifier(MedidataPatientPositionResponseHandler.MEDIDATA_SERVICE_IDENTIFIER);
		MessagePayload messagePayload = new MessagePayload();
		messagePayload.set_any(createMessageElementsFromXML("caXchangeResponseMessage_absentpatientpositions.xml"));
		targetResponseMessage.setTargetBusinessMessage(messagePayload);
		replayMocks();
		medidataPatientPositionResponseHandler.processResponse("some grid id", response);
		verifyMocks();
	}
	
	private MessageElement[] createMessageElementsFromXML(String filename) {
		try {
			MessageElement[] m = new MessageElement[1];
			Document XMLDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new java.io.InputStreamReader(Thread.currentThread()
					.getContextClassLoader().getResourceAsStream(filename))));
			Element element = XMLDoc.getDocumentElement();
			m[0] = new MessageElement(element);
			return m;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
