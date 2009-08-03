class StudySubjecVersionConstraint extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		execute("alter table SCHEDULED_EPOCHS drop constraint fk_sceph_ss")
		dropColumn("scheduled_epochs","spa_id")
		addColumn('scheduled_epochs','stu_sub_stu_ver_id','integer');
		execute("ALTER TABLE SCHEDULED_EPOCHS ADD CONSTRAINT FK_SCEPH_SS FOREIGN KEY (stu_sub_stu_ver_id) REFERENCES stu_sub_stu_versions (ID)");
		
				
		//drop FK study subject to study site
		execute("alter table study_subjects drop constraint fk_spa_sto")
		//drop column study site in study subject
		dropColumn("study_subjects","sto_id")
		//add FK study site study version to study subject study version
		execute("ALTER TABLE stu_sub_stu_versions ADD CONSTRAINT FK_SSSV_SSTSV FOREIGN KEY (stu_site_ver_id) REFERENCES stu_site_stu_versions (ID)");
		//add FK study subject to study subject study version
		execute("ALTER TABLE stu_sub_stu_versions ADD CONSTRAINT FK_SSSV_SS FOREIGN KEY (spa_id) REFERENCES study_subjects (ID)");
		
		
		//add FK study subject version to study subject consent version
		execute("ALTER TABLE stu_sub_cosnt_vers ADD CONSTRAINT FK_SSCV_SSV FOREIGN KEY (stu_sub_stu_ver_id) REFERENCES stu_sub_stu_versions (ID)");
		
		//add FK consent version to study subject consent version
		execute("ALTER TABLE stu_sub_cosnt_vers ADD CONSTRAINT FK_SSCV_CV FOREIGN KEY (consent_version_id) REFERENCES consent_versions (ID)");
    }

	void down() {
		dropColumn('scheduled_epochs','stu_sub_stu_ver_id');
		dropColumn('stu_sub_stu_versions','stu_site_ver_id');
		dropColumn('stu_sub_stu_versions','spa_id');
	}
}