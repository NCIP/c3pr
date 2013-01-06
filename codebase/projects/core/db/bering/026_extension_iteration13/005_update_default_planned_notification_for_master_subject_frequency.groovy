class UpdateDefaultPlannedNotificationForMasterSubjectFrequency extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       		execute("update planned_notfns set frequency = 'END_OF_THE_DAY' where event_name like 'MASTER_SUBJECT_UPDATED_EVENT'");
	}
	
	void down() {
    }
}



