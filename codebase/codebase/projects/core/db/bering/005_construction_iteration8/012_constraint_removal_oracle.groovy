class ConstraintRemovalOracle extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
           if (databaseMatches('oracle')) {
            	execute("alter table study_organizations drop constraint uk_stu_org_dtype");
            	execute("alter table identifiers drop constraint uk_org_stu_type");
            	execute("alter table identifiers drop constraint UK_SYS_STU_TYPE");
           }
    }

    void down() {
        execute("ALTER TABLE IDENTIFIERS add CONSTRAINT UK_ORG_STU_TYPE UNIQUE(hcs_id,stu_id,type,dtype)");
	 	execute("ALTER TABLE IDENTIFIERS add CONSTRAINT UK_SYS_STU_TYPE UNIQUE(system_name,stu_id,type,dtype)");
	 	execute("ALTER TABLE STUDY_ORGANIZATIONS add CONSTRAINT UK_STU_ORG_DTYPE UNIQUE(study_id,hcs_id,type)");
    }
}