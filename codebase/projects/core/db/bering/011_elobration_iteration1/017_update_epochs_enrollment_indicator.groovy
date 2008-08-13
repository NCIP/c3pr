class UpdateEpochsEnrollmentIndicator extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
	if (databaseMatches('postgresql')){
		execute("update epochs set enrollment_indicator='true' where display_role='Treatment'or display_role='TREATMENT' ");
		}
		
	if (databaseMatches('oracle')){
		execute("update epochs set enrollment_indicator='yes' where display_role='Treatment'or display_role='TREATMENT' ");
		}
	}

	void down(){
	}
}
