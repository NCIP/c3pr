class  AlterScheduledEpochStartDateToTimeStamp extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	 	if (databaseMatches('postgresql')) {
	    	execute("alter table scheduled_epochs alter column start_date type timestamp without time zone")
	 	}
    }
  
    void down() {
   		if (databaseMatches('postgresql')) {
    		execute("alter table scheduled_epochs alter column start_date type date")
    	}
    }
}
