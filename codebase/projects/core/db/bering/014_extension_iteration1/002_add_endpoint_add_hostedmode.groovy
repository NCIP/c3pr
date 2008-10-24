class AddEndpointAndHostedMode extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {

    	createTable("ENDPOINT_PROPS") { t ->
            t.addColumn("url", "string",nullable: false)
            t.addColumn("end_point_type", "string",nullable: false)
            t.addColumn("version", "integer", defaultValue: 0, nullable: false)
            t.addColumn("grid_id", "string")
			t.addColumn("retired_indicator", 'string' )
            t.addColumn("is_authentication_required", "boolean")
        }      
    	createTable("ENDPOINTS") { t ->
            t.addColumn("service_name", "string",nullable: false)
            t.addColumn("api_name", "string",nullable: false)
            t.addColumn("version", "integer", defaultValue: 0, nullable: false)
            t.addColumn("grid_id", "string")
			t.addColumn("retired_indicator", 'string' )
            t.addColumn("endpoint_prop_id", "integer", nullable: false)
            t.addColumn("sto_id", "integer")
            t.addColumn("stu_id", "integer")
            t.addColumn("stu_sub_id", "integer")
            t.addColumn("dtype", "string", nullable: false)
            t.addColumn("status", "string")
        }
        createTable("ERRORS") { t ->
            t.addColumn("error_source", "string",nullable: false)
            t.addColumn("error_code", "string",nullable: false)
            t.addColumn("version", "integer", defaultValue: 0, nullable: false)
            t.addColumn("grid_id", "string")
			t.addColumn("retired_indicator", 'string' )
            t.addColumn("error_message", "string")
            t.addColumn("error_date", "date")
            t.addColumn("endpoint_id", "integer")
        }   
        if (databaseMatches('oracle')) {
	    	execute("rename ENDPOINTS_PROPS_ID_SEQ to seq_ENDPOINTS_PROPS_ID")
	    	execute("rename ENDPOINTS_ID_SEQ to seq_ENDPOINTS_ID")
	    	execute("rename ERRORS_ID_SEQ to seq_ERRORS_ID")
	 	}     
	 	addColumn("organizations","study_endpoint_props_id","integer")
	 	addColumn("organizations","reg_endpoint_props_id","integer")
        execute("ALTER TABLE organizations ADD CONSTRAINT FK_STUDY_ENDPOINT_ORG FOREIGN KEY (study_endpoint_props_id) REFERENCES ENDPOINT_PROPS(ID)")
        execute("ALTER TABLE organizations ADD CONSTRAINT FK_REG_ENDPOINT_ORG FOREIGN KEY (reg_endpoint_props_id) REFERENCES ENDPOINT_PROPS(ID)")
        execute("ALTER TABLE endpoints ADD CONSTRAINT FK_STUDY_ENDPOINT_ORG FOREIGN KEY (stu_id) REFERENCES STUDIES(ID)")
        execute("ALTER TABLE endpoints ADD CONSTRAINT FK_STUDY_ORG_ENDPOINT FOREIGN KEY (sto_id) REFERENCES STUDY_ORGANIZATIONS(ID)")
        execute("ALTER TABLE endpoints ADD CONSTRAINT FK_STUDY_SUB_ENDPOINT FOREIGN KEY (stu_id) REFERENCES STUDY_SUBJECTS(ID)")
        execute("ALTER TABLE errors ADD CONSTRAINT FK_ENDPOINT_ERROR FOREIGN KEY (endpoint_id) REFERENCES ENDPOINTS(ID)")
        addColumn("studies","hosted_mode","boolean")
        addColumn('STUDY_ORGANIZATIONS','CCTS_ERROR_STRING','string')
        addColumn('STUDY_ORGANIZATIONS','MULTISITE_WORKFLOW_STATUS','string');
        addColumn('STUDY_ORGANIZATIONS','CCTS_WORKFLOW_STATUS','string');
        addColumn('STUDY_ORGANIZATIONS','MULTISITE_ERROR_STRING','string')
        insert('configuration', [ prop:"idp.url", value:"https://dorian.training.cagrid.org:8443/wsrf/services/cagrid/Dorian" ], primaryKey: false)
        insert('configuration', [ prop:"ifs.url", value:"https://dorian.training.cagrid.org:8443/wsrf/services/cagrid/Dorian" ], primaryKey: false)
    }
  
    void down() {
    	dropTable("ENDPOINT_PROPS")
    	dropColumn("organizations","endpoint_props_id")
    	dropColumn('STUDY_ORGANIZATIONS','CCTS_ERROR_STRING')
    	dropColumn('STUDY_ORGANIZATIONS','MULTISITE_WORKFLOW_STATUS')
    	dropColumn('STUDY_ORGANIZATIONS','CCTS_WORKFLOW_STATUS')
    }
}
