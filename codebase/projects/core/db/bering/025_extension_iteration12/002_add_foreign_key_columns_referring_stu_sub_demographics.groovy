class AddForeignKeyColumnsReferringStudySubjectDemographics extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
         if (databaseMatches('postgres')) {
		   	execute('alter table custom_fields add column stu_sub_dmgphcs_id integer');
		   	execute('alter table contact_mechanisms add column stu_sub_dmgphcs_id integer');
		   	execute('alter table identifiers add column stu_sub_dmgphcs_id integer');
		   	execute('alter table study_subjects add column stu_sub_dmgphcs_id integer');
	 	}
	 	if (databaseMatches('oracle')) {
		   	execute('alter table custom_fields add stu_sub_dmgphcs_id integer');
		   	execute('alter table contact_mechanisms add stu_sub_dmgphcs_id integer');
		   	execute('alter table identifiers add stu_sub_dmgphcs_id integer');
		   	execute('alter table study_subjects add stu_sub_dmgphcs_id integer');
	 	}
	 	
	 	execute('ALTER TABLE custom_fields ADD CONSTRAINT FK_CUSTFLDS_PRTSNPSHTS FOREIGN KEY (stu_sub_dmgphcs_id) REFERENCES stu_sub_demographics (ID)');
	 	execute('ALTER TABLE contact_mechanisms ADD CONSTRAINT FK_CONTMECHSMS_PRTSNPSHTS FOREIGN KEY (stu_sub_dmgphcs_id) REFERENCES stu_sub_demographics (ID)');
	 	execute('ALTER TABLE identifiers ADD CONSTRAINT FK_IDNTFS_PRTSNPSHTS FOREIGN KEY (stu_sub_dmgphcs_id) REFERENCES stu_sub_demographics (ID)');
	 	execute('ALTER TABLE study_subjects ADD CONSTRAINT FK_STUSUBS_PRTSNPSHTS FOREIGN KEY (stu_sub_dmgphcs_id) REFERENCES stu_sub_demographics (ID)');
	 	
	 	
	}
	void down() {
		 if (databaseMatches('postgres')) {
		   	execute('alter table custom_fields drop stu_sub_dmgphcs_id');
		   	execute('alter table contact_mechanisms drop stu_sub_dmgphcs_id');
		   	execute('alter table identifiers drop stu_sub_dmgphcs_id');
		   	execute('alter table study_subjects drop stu_sub_dmgphcs_id');
	 	}
	 	if (databaseMatches('oracle')) {
		   	execute('alter table custom_fields drop column stu_sub_dmgphcs_id');
		   	execute('alter table contact_mechanisms drop column stu_sub_dmgphcs_id');
		   	execute('alter table identifiers drop column stu_sub_dmgphcs_id');
		   	execute('alter table study_subjects drop column stu_sub_dmgphcs_id');
	 	}
	 	
    }
}
