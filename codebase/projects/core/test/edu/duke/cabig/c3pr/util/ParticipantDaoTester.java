package edu.duke.cabig.c3pr.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Participant;

public class ParticipantDaoTester {
	
	
	private ParticipantDao dao;
	private ApplicationContext context;
   
    public ParticipantDaoTester() {
        context = ContextTools.createDeployedApplicationContext();
        init();
        
    }
    
    private void init(){
        dao = (ParticipantDao)context.getBean("participantDao");
    }

    public void testSaveParticipant() {
        Integer savedId;
        //Date birthDate = new Date(1952,12,2);
        
        Participant newParticipant = new Participant();
        newParticipant.setAdministrativeGenderCode("F");
      //  newParticipant.setBirthDate(birthDate);
        newParticipant.setFirstName("Grace");
        newParticipant.setLastName("Emily");
        newParticipant.setRaceCode("Black");
       
        dao.save(newParticipant);

        savedId = newParticipant.getId();
        System.out.println("Id for saved participant is: "+savedId);

    }
    
    public void testGetParticipantById(int participantId){
        Participant retrievedParticipant = (Participant)dao.getById(participantId);
        System.out.println("Name of retrieved participant is "+retrievedParticipant.getName());
    }
    
    public void testGetAllParticipants(){
        List<Participant>  participantList =  new ArrayList<Participant>();
        participantList = dao.getAll();
        Participant participant;
        Iterator<Participant> participantIter = participantList.iterator(); 
        while(participantIter.hasNext()){
        	participant = participantIter.next();
        	System.out.println("Id for participant is "+participant.getId());
        	System.out.println("LastName of participant is "+participant.getLastName());
        //	System.out.println(" D.O.B of participant is " + participant.getBirthDate());
        	       	
        }
        
    }
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ParticipantDaoTester participantDaoTester = new ParticipantDaoTester();
	//	participantDaoTester.testSaveParticipant();
		participantDaoTester.testGetAllParticipants();

	}

}
