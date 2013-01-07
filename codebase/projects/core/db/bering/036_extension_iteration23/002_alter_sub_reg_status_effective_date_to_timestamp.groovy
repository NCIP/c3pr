class AlterSubRegStatusEffectiveDateToTimestamp extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
     	if (databaseMatches('oracle')){
     	// take back up of effective_date column, to be dropped later
       		execute("alter table stu_sub_reg_statuses add effective_date_as_date date");
       		execute("update stu_sub_reg_statuses set effective_date_as_date = effective_date");
       		execute("alter table stu_sub_reg_statuses modify effective_date timestamp");
       	}
	}
	
	void down() {
    }
}


