class scheduledEmailNotifications extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {

    	if (databaseMatches('oracle')) {
	    	execute("rename notifications_id_seq to seq_planned_notfns_id")
	 	}       
	 	renameTable('notifications', 'planned_notfns') 
        if (databaseMatches('oracle')) {
	    	execute("rename seq_planned_notfns_id to planned_notfns_ID_SEQ")
	 	}
    	renameColumn('recipients','notifications_id','planned_notfns_id')
    	
    	addColumn("planned_notfns", "organizations_id", "integer")
    	
    	addColumn("planned_notfns", "event_name", "string")
    	addColumn("planned_notfns", "delivery_mechanism", "string") 
    	addColumn("planned_notfns", "frequency", "string")
        addColumn("planned_notfns", "message", "string")
		addColumn("planned_notfns", "title", "string")
		setNullable('planned_notfns','stu_id', true);
		setNullable('planned_notfns','threshold', true);
    		
    	execute("ALTER TABLE planned_notfns ADD CONSTRAINT FK_ORG_NOT FOREIGN KEY (organizations_id) REFERENCES ORGANIZATIONS(ID)");
    	
    	createTable("schld_notfns") { t ->
            t.addColumn("planned_notfns_id", "integer")
            
            t.addColumn("message", "string")
            t.addColumn("title", "string")    
            t.addColumn("date_sent","timestamp")                  
            
            t.addColumn("version", "integer", defaultValue: 0, nullable: false)
			t.addColumn("grid_id", "string")
			t.addColumn("retired_indicator", 'string' );
        }        
        execute("ALTER TABLE schld_notfns ADD CONSTRAINT FK_NOT_SCNOT FOREIGN KEY (planned_notfns_id) REFERENCES planned_notfns(ID)");

        createTable("rpt_schld_notfns") { t ->
            t.addColumn("schld_notfns_id", "integer")
            t.addColumn("recipients_id", "integer")
            
            t.addColumn("date_read", "timestamp")
            t.addColumn("is_read","boolean")
            t.addColumn("delivery_status","string")
            
            t.addColumn("version", "integer", defaultValue: 0, nullable: false)
			t.addColumn("grid_id", "string")
			t.addColumn("retired_indicator", 'string' );
        }
        execute("ALTER TABLE rpt_schld_notfns ADD CONSTRAINT FK_SCNOT_RSN FOREIGN KEY (schld_notfns_id) REFERENCES schld_notfns(ID)");
        execute("ALTER TABLE rpt_schld_notfns ADD CONSTRAINT FK_RPTS_RSN FOREIGN KEY (recipients_id) REFERENCES recipients(ID)");        
        
        addColumn("contact_mechanisms", "recipients_id", "integer")
        execute("ALTER TABLE contact_mechanisms ADD CONSTRAINT FK_RPTS_CM FOREIGN KEY (recipients_id) REFERENCES recipients(ID)");   
        
        addColumn("recipients", "research_staff_id", "integer")
        addColumn("recipients", "investigators_id", "integer")
        execute("ALTER TABLE recipients ADD CONSTRAINT FK_RS_RPT FOREIGN KEY (research_staff_id) REFERENCES research_staff(ID)");   
        execute("ALTER TABLE recipients ADD CONSTRAINT FK_INV_RPT FOREIGN KEY (investigators_id) REFERENCES investigators(ID)");    
        
        dropColumn('recipients','email_address'); 
    }
  
    void down() {
    	dropColumn("planned_notfns", "frequency")
    	dropColumn("planned_notfns", "organizations_id")
    	dropColumn("planned_notfns", "event_name")
    	dropColumn("planned_notfns", "title")
    	dropColumn("planned_notfns", "message")
    	dropColumn("planned_notfns", "dtype")    	
    	execute(" ALTER TABLE planned_notfns drop CONSTRAINT FK_ORG_NOT")
    	   
       	execute(" ALTER TABLE schld_notfns drop CONSTRAINT FK_NOT_SCNOT")    	
    	dropTable("schld_notfns")
    	 	
    	execute(" ALTER TABLE rpt_schld_notfns drop CONSTRAINT FK_SCNOT_RSN")
    	execute(" ALTER TABLE rpt_schld_notfns drop CONSTRAINT FK_RPTS_RSN")
    	dropTable("rpt_schld_notfns")    	
    }
}
