class CreateNotNullableConstraintsStuSubDemographics extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
         if (databaseMatches('postgres')) {
		   	execute('alter table study_subjects alter column stu_sub_dmgphcs_id set not null');
	 	}
	 	if (databaseMatches('oracle')) {
		   	execute('ALTER TABLE study_subjects MODIFY(stu_sub_dmgphcs_id not null)');
	 	}
	}
	void down() {
    }
}
