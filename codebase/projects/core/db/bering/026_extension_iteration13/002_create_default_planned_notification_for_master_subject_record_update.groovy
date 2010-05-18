class CreateDefaultPlannedNotificationForMasterSubjectRecordUpdate extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('postgres')){
       		execute("insert into planned_notfns (id,version,retired_indicator,title,event_name, frequency) values(nextval('planned_notfns_ID_SEQ'),0,'false','Subject record updated','MASTER_SUBJECT_UPDATED_EVENT','IMMEDIATE')");
       		execute("insert into recipients (id,version,dtype,retired_indicator,role,planned_notfns_id) values (nextval('recipients_ID_SEQ'),0,'RR','false','REGISTRAR',currval('planned_notfns_ID_SEQ'))");
       	}
     	if (databaseMatches('oracle')){
       		execute("insert into planned_notfns (id,version,retired_indicator,title,event_name, frequency) values(planned_notfns_ID_SEQ.NEXTVAL,0,'false','Subject record updated','MASTER_SUBJECT_UPDATED_EVENT','IMMEDIATE')");
       		execute("insert into recipients (id,version,dtype,retired_indicator,role,planned_notfns_id) values (recipients_ID_SEQ.NEXTVAL,0,'RR','false','REGISTRAR',planned_notfns_ID_SEQ.currval)");
       	}
	}
	
	void down() {
    }
}



