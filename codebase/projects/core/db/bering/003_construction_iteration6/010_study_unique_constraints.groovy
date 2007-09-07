class StudyUniqueConstraints extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
    
    	execute("ALTER TABLE EPOCHS add CONSTRAINT UK_STU_EPH UNIQUE(name, stu_id)")
   		execute("ALTER TABLE ARMS add CONSTRAINT UK_EPH_ARM UNIQUE(name, eph_id)")
	 	execute("ALTER TABLE IDENTIFIERS add CONSTRAINT UK_ORG_STU_TYPE UNIQUE(hcs_id,stu_id,type,dtype)")
	 	execute("ALTER TABLE IDENTIFIERS add CONSTRAINT UK_SYS_STU_TYPE UNIQUE(system_name,stu_id,type,dtype)")
    	execute("ALTER TABLE STUDY_ORGANIZATIONS add CONSTRAINT UK_STU_ORG_DTYPE UNIQUE(study_id,hcs_id,type)")
    	if (databaseMatches('postgres')) {
   		execute("CREATE UNIQUE INDEX UI_IDENTIFIERS_ORG_VALUE ON IDENTIFIERS(hcs_id,value) where type='Protocol Authority Identifier'")
   		}
   		execute("ALTER TABLE INVESTIGATORS add CONSTRAINT UK_INV UNIQUE(nci_identifier,first_name,last_name,maiden_name)")
   		execute("ALTER TABLE RESEARCH_STAFF add CONSTRAINT UK_RSF UNIQUE(nci_identifier,first_name,last_name,maiden_name)")
   		execute("ALTER TABLE HC_SITE_INVESTIGATORS add CONSTRAINT UK_ORG_INV UNIQUE(hcs_id,inv_id)")
   		execute("ALTER TABLE STUDY_INVESTIGATORS add CONSTRAINT UK_STU_INV UNIQUE(hsi_id,sto_id,role_code)")
   		 		
   		
   	 }

    void down() {
    
   		execute(" ALTER TABLE STUDY_INVESTIGATORS drop CONSTRAINT UK_STU_INV")
   		execute("ALTER TABLE HC_SITE_INVESTIGATORS drop CONSTRAINT UK_ORG")
   		execute("ALTER TABLE RESEARCH_STAFF drop CONSTRAINT UK_RSF")
   		execute("ALTER TABLE INVESTIGATORS drop CONSTRAINT UK_INV")
    	execute("ALTER TABLE IDENTIFIERS drop CONSTRAINT UK_ORG_DTYPE_TYPE_VALUE")
    	execute("ALTER TABLE STUDY_ORGANIZATIONS drop CONSTRAINT UK_STU_ORG_DTYPE")
    	execute("ALTER TABLE IDENTIFIERS drop CONSTRAINT UK_SYS_STU_TYPE")
       	execute("ALTER TABLE IDENTIFIERS drop CONSTRAINT UK_ORG_STU_TYPE")
    	execute("ALTER TABLE ARMS drop CONSTRAINT UK_EPH_ARM")
    	execute("ALTER TABLE EPOCHS drop CONSTRAINT UK_STU_EPH")	
   	
   			}
	
}