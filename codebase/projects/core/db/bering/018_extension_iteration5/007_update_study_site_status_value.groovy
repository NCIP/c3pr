class UpdateStudySiteStatusValue extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			execute("update study_organizations set site_study_status='PENDING' where site_study_status='APPROVED_FOR_ACTIVTION'");			
	}

	void down(){
	}
}
