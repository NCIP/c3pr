class DropColumnSratumGroupNumberStudySubject extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	dropColumn('study_subjects','stratum_group_number')
    } 

	void down() {
	}
}