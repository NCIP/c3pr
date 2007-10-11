package edu.duke.cabig.c3pr.grid.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import java.rmi.RemoteException;

import edu.duke.cabig.c3pr.service.StudyXMLImporterService;
import edu.duke.cabig.c3pr.grid.stubs.types.StudyCreateOrUpdateException;

/**
 *
 */
public class C3PRStudyConsumerImpl extends C3PRStudyConsumerImplBase {
    private StudyXMLImporterService service;

    public C3PRStudyConsumerImpl() throws RemoteException {
        super();
        // glue code       
        ApplicationContext ctx =    new ClassPathXmlApplicationContext (new String[] {
                "classpath*:edu/duke/cabig/c3pr/applicationContext-core.xml",
                "classpath*:edu/duke/cabig/c3pr/applicationContext-core-db.xml",
                "classpath*:edu/duke/cabig/c3pr/applicationContext-csm.xml",

        });
        service = (StudyXMLImporterService) ctx.getBean("studyXMLImporterService");

    }

    public void createOrUpdate(edu.duke.cabig.c3pr.domain.Study study) throws RemoteException, edu.duke.cabig.c3pr.grid.stubs.types.InvalidStudyException, edu.duke.cabig.c3pr.grid.stubs.types.StudyCreateOrUpdateException {
        System.out.println("Received study with gridId " + study.getGridId());
        try {
            service.importStudy(study);
        } catch (Exception e) {
            StudyCreateOrUpdateException exception =  new StudyCreateOrUpdateException();
            exception.setFaultDetailString(e.getMessage());
            throw exception;
        }

    }

}

