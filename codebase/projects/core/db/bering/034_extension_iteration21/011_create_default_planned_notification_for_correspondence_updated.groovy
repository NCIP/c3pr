class CreateDefaultPlannedNotificationForCorrespondenceUpdate extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('postgres')){
       		execute("insert into planned_notfns (id,version,retired_indicator,title,event_name, frequency) values(nextval('planned_notfns_ID_SEQ'),0,'false','Correspondence created or updated','CORRESPONDENCE_CREATED_OR_UPDATED_EVENT','IMMEDIATE')");
       	}
     	if (databaseMatches('oracle')){
       		execute("insert into planned_notfns (id,version,retired_indicator,title,event_name, frequency) values(planned_notfns_ID_SEQ.NEXTVAL,0,'false','Correspondence created or updated','CORRESPONDENCE_CREATED_OR_UPDATED_EVENT','IMMEDIATE')");
       	}
	}
	
	void down() {
    }
}



