class CreateTableOrganizationInvestigatorGroupAffiliations extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
     createTable("org_inv_gr_affiliations") { t ->
     		t.addVersionColumn()
	    	t.addColumn('start_date', 'date', nullable:true)
	   		t.addColumn('end_date', 'date', nullable:true)
       		t.addColumn('ing_id', 'integer', nullable:false)
       		t.addColumn('hsi_id', 'integer', nullable:false)
       		t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
    	}
   	}

    void down() {
        dropTable("org_inv_gr_affiliations")
    }
}
