class TrackStudyVersion extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        
        // new table to store research staff - health care site many to many association.
        createTable('rs_hc_site_assocn') { t ->
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('rs_id', 'integer', nullable:false)
            t.addColumn('hcs_id', 'integer', nullable:false)
        }
        if (databaseMatches('oracle')) {
		   	execute("RENAME SEQ_rs_hc_site_assocn_ID to rs_hc_site_assocn_ID_SEQ");
	 	}
        //foreign key associations for new table
		execute("ALTER TABLE rs_hc_site_assocn ADD CONSTRAINT FK_RS_HCS_ASSOCN_RS FOREIGN KEY (rs_id) REFERENCES research_staff (ID)");
		execute("ALTER TABLE rs_hc_site_assocn ADD CONSTRAINT FK_RS_HCS_ASSOCN_HC_SITE FOREIGN KEY (hcs_id) REFERENCES organizations (ID)");
		
		//data migration from research staff table
		if (databaseMatches('postgres')){
	    	execute('insert into "rs_hc_site_assocn"( "rs_id", "version", "hcs_id") select "id", 0, "hcs_id" from research_staff') ;
    	}
		if (databaseMatches('oracle')){
    		execute('insert into rs_hc_site_assocn(rs_id, version, hcs_id) select id, 0, hcs_id from research_staff') ;
    	}	
		//column drop from research staff table
		dropColumn('research_staff', 'hcs_id');
    }

	void down() {
	    
    }
}