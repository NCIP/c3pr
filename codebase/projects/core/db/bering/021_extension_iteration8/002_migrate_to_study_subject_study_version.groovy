class migrateToStudySubjectStudyVersion extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
			execute("insert into study_subject_versions (id,version,study_site_ver_id,spa_id) (select id,version,sto_id,id from study_subjects)");
			execute("select consents.id from consents,study_versions where consents.stu_version_id = study_versions.id and study_versions.id in (select distinct study_versions.id from study_versions,study_site_versions,study_subject_versions where	study_subject_versions.study_site_ver_id = study_site_versions.id and study_site_versions.stu_version_id=study_versions.id)");
			execute("insert into study_subject_consents (id,version,informed_consent_signed_date,study_subject_ver_id) (select id,version,informed_consent_signed_date,id from study_subjects)");
    }

	void down() {
	
	}
}