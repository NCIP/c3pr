class StudySubjecVersionConstraint extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		execute("alter table SCHEDULED_EPOCHS drop constraint fk_sceph_ss")
		dropColumn("scheduled_epochs","spa_id")
		addColumn('scheduled_epochs','study_subject_ver_id','integer');
		execute("ALTER TABLE SCHEDULED_EPOCHS ADD CONSTRAINT FK_SCEPH_SS FOREIGN KEY (study_subject_ver_id) REFERENCES study_subject_versions (ID)");


		//drop FK study subject to study site
		execute("alter table study_subjects drop constraint fk_spa_sto")
		//drop column study site in study subject
		dropColumn("study_subjects","sto_id")
		//add FK study site study version to study subject study version
		execute("ALTER TABLE study_subject_versions ADD CONSTRAINT FK_SSV_SSSV FOREIGN KEY (study_site_ver_id) REFERENCES study_site_versions (ID)");
		//add FK study subject to study subject study version
		execute("ALTER TABLE study_subject_versions ADD CONSTRAINT FK_SSV_SS FOREIGN KEY (spa_id) REFERENCES study_subjects (ID)");


		//add FK study subject version to study subject consent version
		execute("ALTER TABLE study_subject_consents ADD CONSTRAINT FK_SSC_SSV FOREIGN KEY (study_subject_ver_id) REFERENCES study_subject_versions (ID)");

		//add FK consent version to study subject consent version
		execute("ALTER TABLE study_subject_consents ADD CONSTRAINT FK_SSC_CV FOREIGN KEY (consent_id) REFERENCES consents (ID)");
		
		//dropColumn('study_subjects', 'informed_consent_signed_date');
		//dropColumn('study_subjects', 'informed_consent_version');
    }

	void down() {
		dropColumn('scheduled_epochs','study_subject_ver_id');
	}
}