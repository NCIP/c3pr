class ConstraintRemovalSQLServer extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
           if (databaseMatches('sqlserver')) {
            	execute("alter table study_organizations drop constraint UK_STU_ORG_DTYPE");
            	execute("alter table identifiers drop constraint UK_ORG_STU_TYPE");
            	execute("alter table identifiers drop constraint UK_SYS_STU_TYPE");
           }
    }

    void down() {
    	 if (databaseMatches('sqlserver')) {
		        execute("alter table identifiers add constraint UK_ORG_STU_TYPE UNIQUE(hcs_id,stu_id,type,dtype)");
			 	execute("alter table identifiers add constraint UK_SYS_STU_TYPE UNIQUE(system_name,stu_id,type,dtype)");
			 	execute("alter table study_organizations add constraint UK_STU_ORG_DTYPE UNIQUE(study_id,hcs_id,type)");
		 	}
    }
}