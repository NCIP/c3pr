class CreateTableReasonStudySubRegStatusAssociation extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	createTable("REGISTRY_REASONS_ASSN") { t ->
            t.addColumn("STU_SUB_REG_ST_ID", "integer", nullable: false)
            t.addColumn("REASON_ID", "integer", nullable: false)
         }
         
		
		
		execute('ALTER TABLE REGISTRY_REASONS_ASSN ADD CONSTRAINT FK_REASON FOREIGN KEY (REASON_ID) REFERENCES REASONS(ID)');
		execute('ALTER TABLE REGISTRY_REASONS_ASSN ADD CONSTRAINT FK_REGISTRY_REASONS_ASSN FOREIGN KEY (STU_SUB_REG_ST_ID) REFERENCES STU_SUB_REG_STATUSES(ID)');
		
		execute('Insert into REGISTRY_REASONS_ASSN(REASON_ID, STU_SUB_REG_ST_ID) select ID, STU_SUB_REG_ST_ID from reasons where STU_SUB_REG_ST_ID is not null')
		
		dropColumn("REASONS", "STU_SUB_REG_ST_ID")
		dropColumn('REGISTRY_REASONS_ASSN','id');
		execute('alter table REGISTRY_REASONS_ASSN add PRIMARY KEY(STU_SUB_REG_ST_ID,REASON_ID)');
		
		//fixing the error in 002_create_table_relationships.groovy and 003_create_table_correspondences.groovy in the sequence name for oracle
		if (databaseMatches('oracle')){
			execute("RENAME SEQ_relationships_id to relationships_id_SEQ");
			execute("RENAME SEQ_correspondences_id to correspondences_id_SEQ");
		}
	}
	void down() {
    }
}
