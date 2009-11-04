class AddColumnsStudyOrganizations extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('STUDY_ORGANIZATIONS','TARGET_ACCRUAL_NUMBER','integer');
    }

    void down() {
    	dropColumn('STUDY_ORGANIZATIONS','TARGET_ACCRUAL_NUMBER');
    }
}