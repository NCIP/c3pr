class AddStudyStatusStudySite extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	 	addColumn("STUDY_ORGANIZATIONS","study_status","string")
    }
  
    void down() {
    	dropColumn("STUDY_ORGANIZATIONS","study_status")
    }
}
