class CreateSummary3ReportDiseaseSites extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	 createTable('summ3_rep_disease_sites') { t ->
        	t.addColumn('name', 'string', nullable:false)
        	t.addColumn('grid_id' , 'string' , nullable:true)
        	t.addVersionColumn()
        }
		
	 	if (databaseMatches('oracle')) {
		   	execute('drop sequence SEQ_SUMM3_REP_DISEASE_SITES_ID');
	 	}
	 	if (databaseMatches('postgres')) {
		   	execute('drop sequence summ3_rep_disease_sites_ID_SEQ cascade');
	 	}
    }

	void down() {
		dropTable('summ3_rep_disease_sites');
	}
}