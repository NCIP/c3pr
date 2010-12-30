class CreateDefaultPlannedNotificationForMasterSubjectRecordUpdate extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('postgres')){
       		execute("insert into role_privileges (id,version,role_name,object_id,privilege) values(nextval('role_privileges_ID_SEQ'),0,'person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.PersonOrUser','READ')");
       		execute("insert into role_privileges (id,version,role_name,object_id,privilege) values(nextval('role_privileges_ID_SEQ'),0,'person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.PersonOrUser','CREATE')");
       		execute("insert into role_privileges (id,version,role_name,object_id,privilege) values(nextval('role_privileges_ID_SEQ'),0,'person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.PersonOrUser','UPDATE')");

			execute("insert into role_privileges (id,version,role_name,object_id,privilege) values(nextval('role_privileges_ID_SEQ'),0,'user_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.PersonOrUser','READ')");
       		execute("insert into role_privileges (id,version,role_name,object_id,privilege) values(nextval('role_privileges_ID_SEQ'),0,'user_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.PersonOrUser','CREATE')");
       		execute("insert into role_privileges (id,version,role_name,object_id,privilege) values(nextval('role_privileges_ID_SEQ'),0,'user_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.PersonOrUser','UPDATE')");
    		
       	}

     	if (databaseMatches('oracle')){
       		execute("insert into role_privileges (id,version,role_name,object_id,privilege) values(role_privileges_ID_SEQ.NEXTVAL,0,'person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.PersonOrUser','READ')");
       		execute("insert into role_privileges (id,version,role_name,object_id,privilege) values(role_privileges_ID_SEQ.NEXTVAL,0,'person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.PersonOrUser','CREATE')");
       		execute("insert into role_privileges (id,version,role_name,object_id,privilege) values(role_privileges_ID_SEQ.NEXTVAL,0,'person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.PersonOrUser','UPDATE')");

			execute("insert into role_privileges (id,version,role_name,object_id,privilege) values(role_privileges_ID_SEQ.NEXTVAL,0,'user_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.PersonOrUser','READ')");
       		execute("insert into role_privileges (id,version,role_name,object_id,privilege) values(role_privileges_ID_SEQ.NEXTVAL,0,'user_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.PersonOrUser','CREATE')");
       		execute("insert into role_privileges (id,version,role_name,object_id,privilege) values(role_privileges_ID_SEQ.NEXTVAL,0,'user_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.PersonOrUser','UPDATE')");
       	}
       	
       	execute("update role_privileges set object_id='edu.duke.cabig.c3pr.domain.PersonUser' where object_id='edu.duke.cabig.c3pr.domain.ResearchStaff'")
        execute("delete from role_privileges where role_name='user_administrator' and object_id='edu.duke.cabig.c3pr.utils.web.navigation.Task.ResearchStaff' and privilege='UPDATE'");
       		
	}
	
	void down() {
    }
}



