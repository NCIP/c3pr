package edu.duke.cabig.c3pr.grid.service;

import java.rmi.RemoteException;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.1
 * 
 */
public class C3PRStudyConsumerImpl extends C3PRStudyConsumerImplBase {

	
	public C3PRStudyConsumerImpl() throws RemoteException {
		super();
	}
	
  public void createOrUpdate(edu.duke.cabig.c3pr.domain.Study study) throws RemoteException, edu.duke.cabig.c3pr.grid.stubs.types.InvalidStudyException, edu.duke.cabig.c3pr.grid.stubs.types.StudyCreateOrUpdateException {
      System.out.println("Received study with gridId " + study.getGridId());
  }

}

