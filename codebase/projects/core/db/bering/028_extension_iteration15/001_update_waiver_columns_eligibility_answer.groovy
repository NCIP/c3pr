class AddWaiverColumnsEligibilityAnswer extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('subject_eligibility_ans', 'rs_id', 'integer');
    	execute('ALTER TABLE subject_eligibility_ans ADD CONSTRAINT FK_ELG_ANS_RS FOREIGN KEY (rs_id) REFERENCES research_staff (ID)');
    	execute('update subject_eligibility_ans set rs_id = (select research_staff.id from research_staff join study_personnel on research_staff.id = study_personnel.research_staff_id where study_personnel.id = subject_eligibility_ans.stu_personnel_id)');
    	execute('ALTER TABLE subject_eligibility_ans drop CONSTRAINT FK_ELG_ANS_STDPERSON');
    	dropColumn('subject_eligibility_ans','stu_personnel_id');
	}
	void down() {
    }
}
