/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
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

public class C3DPatientPositionResponseHandlerTestCase extends AbstractTestCase{

	private StudySubjectRepository studySubjectRepository;
	
	private C3DPatientPositionResponseHandler c3dPatientPositionResponseHandler;
	
	public C3DPatientPositionResponseHandlerTestCase() {
		studySubjectRepository = registerMockFor(StudySubjectRepository.class);
		c3dPatientPositionResponseHandler = new C3DPatientPositionResponseHandler();
		c3dPatientPositionResponseHandler.setStudySubjectRepositoryNew(studySubjectRepository);
	}
	
	public void testCaXchangeResponseUnMarshalling(){
		Response response = new Response();
		TargetResponseMessage targetResponseMessage = new TargetResponseMessage();
		response.setTargetResponse(new TargetResponseMessage[]{targetResponseMessage});
		targetResponseMessage.setTargetServiceIdentifier(C3DPatientPositionResponseHandler.C3D_SERVICE_IDENTIFIER);
		MessagePayload messagePayload = new MessagePayload();
		messagePayload.set_any(createMessageElementsFromXML("caXchangeResponseMessage.xml"));
		targetResponseMessage.setTargetBusinessMessage(messagePayload);
		studySubjectRepository.assignC3DIdentifier(EasyMock.isA(StudySubject.class), EasyMock.isA(String.class));
		replayMocks();
		c3dPatientPositionResponseHandler.processResponse("some grid id", response);
		verifyMocks();
	}
	
	public void testCaXchangeResponseUnMarshallingC3DIdenifierAbsent(){
		Response response = new Response();
		TargetResponseMessage targetResponseMessage = new TargetResponseMessage();
		response.setTargetResponse(new TargetResponseMessage[]{targetResponseMessage});
		targetResponseMessage.setTargetServiceIdentifier(C3DPatientPositionResponseHandler.C3D_SERVICE_IDENTIFIER);
		MessagePayload messagePayload = new MessagePayload();
		messagePayload.set_any(createMessageElementsFromXML("caXchangeResponseMessage_absentpatientpositions.xml"));
		targetResponseMessage.setTargetBusinessMessage(messagePayload);
		replayMocks();
		c3dPatientPositionResponseHandler.processResponse("some grid id", response);
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
