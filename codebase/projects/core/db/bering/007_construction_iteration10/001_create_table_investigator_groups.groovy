class CreateTableInvestigatorGroups extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        createTable("investigator_groups") { t ->
            t.addVersionColumn()
            t.addColumn('name', 'string', nullable:false)
          	t.addColumn('description_text', 'string', nullable:true)
	    	t.addColumn('start_date', 'date', nullable:true)
	   		t.addColumn('end_date', 'date', nullable:true)
	   		t.addColumn('hcs_id', 'integer', nullable:false)
	   		t.addColumn('retired_indicator', 'string', nullable:true)
	   		t.addColumn('grid_id' , 'string' , nullable:true)
        }
    }

    void down() {
        dropTable("investigator_groups")
    }
}
