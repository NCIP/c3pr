class CreateICD9DiseaseSites extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	 createTable('icd9_disease_sites') { t ->
        	t.addColumn('name', 'string', nullable:false)
        	t.addColumn('grid_id' , 'string' , nullable:true)
        	t.addVersionColumn()
        	t.addColumn('code', 'numeric', nullable:true)
        	t.addColumn('description_text', 'string', nullable:true)
        	t.addColumn('depth', 'string', nullable:false)
        	t.addColumn('selectable', 'boolean', nullable:false)
        	t.addColumn('parent_id', 'integer', nullable:true)
        	t.addColumn('summ3_rep_disease_site_id', 'integer', nullable:true)
        }
		
	    if (databaseMatches('oracle')) {
		   	execute('drop sequence SEQ_icd9_disease_sites_ID');
	 	}
	 	if (databaseMatches('postgres')) {
		   	execute('drop sequence icd9_disease_sites_ID_SEQ cascade');
	 	}
	 	
	 	execute("alter table icd9_disease_sites add constraint FK_ICD9_Summ3 FOREIGN KEY (summ3_rep_disease_site_id) REFERENCES summ3_rep_disease_sites(ID)")
    }

	void down() {
		execute("alter table icd9_disease_sites drop constraint FK_ICD9_Summ3");
		dropTable('icd9_disease_sites');
	}
}