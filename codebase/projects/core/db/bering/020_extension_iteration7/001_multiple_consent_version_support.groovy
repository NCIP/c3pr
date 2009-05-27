class MultipleConsentVersionSupport extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        createTable("consents") { t ->
        	t.addColumn("retired_indicator", "string", nullable:true)
       		t.addColumn("grid_id" , "string" , nullable:true)
       		t.addVersionColumn()
            t.addColumn("name", "string", nullable:false)
            t.addColumn("study_id", "integer", nullable:false)
        }
        
        createTable("consent_versions") { t ->
            t.addColumn("retired_indicator", "string", nullable:true)
       		t.addColumn("grid_id" , "string" , nullable:true)
       		t.addVersionColumn()
            t.addColumn("name", "string",nullable:false)
            t.addColumn("date", "date" ,nullable:false)
            t.addColumn("latest_indicator", "boolean")
             t.addColumn("consent_id", "integer",nullable:false)
        }
        
        createTable("stu_sub_cosnt_vers") { t ->
        	t.addColumn("retired_indicator", "string", nullable:true)
       		t.addColumn("grid_id" , "string" , nullable:true)
       		t.addVersionColumn()
            t.addColumn("informed_consent_signed_date", "date")
            t.addColumn("stu_sub_id", "integer",nullable:false)
            t.addColumn("consent_version_id", "integer",nullable:false)
        }
        
          addColumn("studies","consent_validity_period","string");
    }

    void down() {
        dropTable("stu_sub_cosnt_vers")
        dropTable("consent_versions")
        dropTable("consents")
        dropColumn("studies", "consent_validity_period")
    }
}