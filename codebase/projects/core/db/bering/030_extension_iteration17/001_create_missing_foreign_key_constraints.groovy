class CreateMissingForeignKeyConstraints extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	    execute("ALTER TABLE study_versions ADD CONSTRAINT FK_STUVER_STU FOREIGN KEY (study_id) REFERENCES studies(ID)");
		execute("ALTER TABLE study_site_versions ADD CONSTRAINT FK_STUSITVER_STUORG FOREIGN KEY (sto_id) REFERENCES study_organizations (ID)");
		execute("ALTER TABLE study_site_versions ADD CONSTRAINT FK_STUSITVER_STUVER FOREIGN KEY (stu_version_id) REFERENCES study_versions (ID)");
		execute("ALTER TABLE study_subjects ADD CONSTRAINT FK_STUSUB_STUINV FOREIGN KEY (sti_id) REFERENCES study_investigators (ID)");
		 	
	}
	void down() {
		execute("ALTER TABLE study_versions DROP CONSTRAINT FK_STUVER_STU");
		execute("ALTER TABLE study_site_versions DROP CONSTRAINT FK_STUSITVER_STUORG");
		execute("ALTER TABLE study_site_versions DROP CONSTRAINT FK_STUSITVER_STUVER");
		execute("ALTER TABLE study_subjects DROP CONSTRAINT FK_STUSUB_STUINV");
    }
}
