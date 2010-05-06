class AddWaiverColumnsEligibilityAnswer extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('subject_eligibility_ans', 'allow_waiver', 'boolean');
    	addColumn('subject_eligibility_ans', 'waiver_id', 'string');
    	addColumn('subject_eligibility_ans', 'waiver_reason', 'string');
    	addColumn('subject_eligibility_ans', 'stu_personnel_id', 'integer');
    	execute('ALTER TABLE subject_eligibility_ans ADD CONSTRAINT FK_ELG_ANS_STDPERSON FOREIGN KEY (stu_personnel_id) REFERENCES study_personnel (ID)');
    	if (databaseMatches('postgres')) {
				execute("alter table subject_eligibility_ans alter column allow_waiver set default false")
			}
		 if (databaseMatches('oracle')){
			execute("alter table subject_eligibility_ans modify allow_waiver default 0")
			}
	}
	void down() {
    }
}
