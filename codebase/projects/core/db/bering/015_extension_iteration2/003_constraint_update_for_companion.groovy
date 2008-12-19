class updateContsraintInStudyOrganization extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		if (databaseMatches('postgres')){
			execute("ALTER TABLE STUDY_ORGANIZATIONS drop CONSTRAINT UK_STU_ORG_DTYPE")
		}
		execute("ALTER TABLE STUDY_ORGANIZATIONS add CONSTRAINT UK_STU_ORG_DTYPE UNIQUE(study_id,hcs_id,type,comp_assoc_id)")
	}

	void down(){
		execute("ALTER TABLE STUDY_ORGANIZATIONS drop CONSTRAINT UK_STU_ORG_DTYPE")
		execute("ALTER TABLE STUDY_ORGANIZATIONS add CONSTRAINT UK_STU_ORG_DTYPE UNIQUE(study_id,hcs_id,type)")
	}
}
