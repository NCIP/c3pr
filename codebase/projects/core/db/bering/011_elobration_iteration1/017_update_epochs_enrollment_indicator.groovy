class UpdateEpochsEnrollmentIndicator extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		execute("update epochs set enrollment_indicator='true' where display_role='Treatment'or display_role='TREATMENT' ");
	}

	void down(){
	}
}
