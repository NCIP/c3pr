package edu.duke.cabig.c3pr.grid.service;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.service.StudyXMLImporterService;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.ccts.grid.common.StudyConsumerI;
import gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException;
import gov.nih.nci.ccts.grid.studyconsumer.stubs.types.StudyCreationException;
import org.apache.log4j.Logger;

import javax.xml.namespace.QName;
import java.io.StringReader;
import java.io.StringWriter;
import java.rmi.RemoteException;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class C3PRStudyConsumer implements StudyConsumerI {

    private XmlMarshaller studyMarshaller;
    private StudyXMLImporterService studyXMLImporterService;
    Logger log = Logger.getLogger(C3PRStudyConsumer.class);


    public void createStudy(gov.nih.nci.ccts.grid.Study study) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.StudyCreationException {
        Study marshalledStudy = null;
        try {
            StringWriter studyXMLWriter = new StringWriter();
            Utils.serializeObject(study,new QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain","study"),studyXMLWriter);
            marshalledStudy  = (Study)studyMarshaller.fromXML(new StringReader(studyXMLWriter.toString()));
            log.debug("Saving study with gridId" + study.getGridId());


        } catch (Exception e) {
            log.error(e.getMessage());
            InvalidStudyException ex =  new InvalidStudyException();
            ex.setFaultDetailString(e.getMessage());

        }

        try {
            log.debug("Saving study with gridId" + study.getGridId());
            studyXMLImporterService.importStudy(marshalledStudy);
        } catch (Exception e) {
            log.error(e.getMessage());
            StudyCreationException ex =  new StudyCreationException();
            ex.setFaultDetailString(e.getMessage());
        }
    }

    public void commit(gov.nih.nci.ccts.grid.Study study) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException {
        throw new UnsupportedOperationException("Transaction support not implemented");
    }

    public void rollback(gov.nih.nci.ccts.grid.Study study) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException {
        throw new UnsupportedOperationException("Transaction support not implemented");
    }


    public XmlMarshaller getStudyMarshaller() {
        return studyMarshaller;
    }

    public void setStudyMarshaller(XmlMarshaller studyMarshaller) {
        this.studyMarshaller = studyMarshaller;
    }

    public StudyXMLImporterService getStudyXMLImporterService() {
        return studyXMLImporterService;
    }

    public void setStudyXMLImporterService(StudyXMLImporterService studyXMLImporterService) {
        this.studyXMLImporterService = studyXMLImporterService;
    }
}
