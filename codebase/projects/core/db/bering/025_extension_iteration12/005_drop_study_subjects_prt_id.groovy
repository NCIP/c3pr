class DropStudySubjectsPrtId extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    
         dropColumn("study_subjects","prt_id");
	 	
	}
	
	void down() {
    }
}
