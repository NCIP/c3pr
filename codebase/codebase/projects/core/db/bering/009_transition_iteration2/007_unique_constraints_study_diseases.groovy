class StudyDiseasesUniqueConstraints extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
    	execute("ALTER TABLE STUDY_DISEASES add CONSTRAINT UK_STU_DTM UNIQUE(study_id,disease_term_id)")
   	 }

    void down() {
   		execute(" ALTER TABLE STUDY_DISEASES drop CONSTRAINT UK_STU_DTM")
   	 }
}