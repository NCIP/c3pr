package edu.duke.cabig.c3pr.grid.service;

import java.rmi.RemoteException;

/**
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 *
 * @created by Introduce Toolkit version 1.0
 *
 */
public class C3PRStudyConsumerImpl extends C3PRStudyConsumerImplBase {


    public C3PRStudyConsumerImpl() throws RemoteException {
        super();
    }


    public edu.duke.cabig.c3pr.domain.Study createOrUpdate(edu.duke.cabig.c3pr.domain.Study study) throws  edu.duke.cabig.c3pr.grid.stubs.types.StudyValidationException, edu.duke.cabig.c3pr.grid.stubs.types.StudyCreateOrUpdateException {
        System.out.println("Received Study with grid id " + study.getGridId() );
        return null;
    }

}

