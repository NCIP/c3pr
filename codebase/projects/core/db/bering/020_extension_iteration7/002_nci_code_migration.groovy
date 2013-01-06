class NciCodeMigration extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	
    	if (databaseMatches('postgres')) {
    		execute("insert into addresses(\"id\", \"version\", \"street_address\", \"city\", \"country_code\", \"postal_code\", \"state_code\", \"retired_indicator\") values(19917, 0, '9000 Rockville Pike', 'Bethesda', 'USA', 20892, 'MD', 'false')")
    		execute("insert into addresses(\"id\", \"version\", \"street_address\", \"city\", \"country_code\", \"postal_code\", \"state_code\", \"retired_indicator\") values(19918, 0, '6116 Executive Boulevard', 'Bethesda', 'USA', 20892, 'MD', 'false')")
    	
	    	execute("insert into organizations(\"id\", \"version\", \"nci_institute_code\", \"dtype\", \"name\", \"retired_indicator\", \"address_id\") values(19917, 0, 'CTEP', 'Local', 'Cancer Therapy Evaluation Program', 'false', 19917)")
    		execute("insert into organizations(\"id\", \"version\", \"nci_institute_code\", \"dtype\", \"name\", \"retired_indicator\", \"address_id\") values(19918, 0, 'NCI', 'Local', 'National Cancer Institute', 'false', 19918)")
    	
    		execute("insert into identifiers (\"id\", \"version\", \"retired_indicator\", \"dtype\", \"type\", \"primary_indicator\", \"value\", \"org_id\", \"hcs_id\") select nextval('identifiers_ID_SEQ'::regclass) as id, 0, 'false', 'OAI', 'CTEP', 'TRUE', \"nci_institute_code\",\"id\", 19917 from organizations");
    	}
        
        if (databaseMatches('oracle')) {
        
        // added after testing Baylor Oracle Migration
        	execute("alter table participants DISABLE constraint FK_PRT_ADD");
        	execute("alter table organizations DISABLE constraint FK_ORG_ADD");
       	    execute("update addresses set id = -id where id >=19917");
       	    execute("update participants set add_id = -add_id where add_id>=19917");
       	    execute("alter table participants ENABLE constraint FK_PRT_ADD;");
       	    execute("update organizations set address_id = -address_id where address_id>=19917;");
        	execute("alter table organizations ENABLE constraint FK_ORG_ADD;");
       	    execute("alter table research_staff DISABLE constraint FK_RS_ORG;");
       	    execute("alter table identifiers DISABLE constraint FK_IDN_HCS;");
       	    execute("alter table HC_SITE_INVESTIGATORS DISABLE constraint FK_HSI_ORG;");
       	    execute("alter table STUDY_ORGANIZATIONS DISABLE constraint FK_STO_ORG;");
        	execute("update organizations set id = -id where id>=19917");
       	    execute("update research_staff set hcs_id = -hcs_id  where hcs_id>=19917");
       	    execute("update identifiers set hcs_id = -hcs_id  where hcs_id>=19917");
       	    execute("UPDATE HC_SITE_INVESTIGATORS SET hcs_id = -hcs_id WHERE hcs_id>=19917");
       	    execute("UPDATE STUDY_ORGANIZATIONS SET hcs_id = -hcs_id WHERE hcs_id>=19917");
        	execute("alter table research_staff ENABLE constraint FK_RS_ORG;");
       	    execute("alter table identifiers ENABLE constraint FK_IDN_HCS;");
       	    execute("alter table HC_SITE_INVESTIGATORS ENABLE constraint FK_HSI_ORG;");
       	    execute("alter table STUDY_ORGANIZATIONS ENABLE constraint FK_STO_ORG;");
       	    
        	execute("insert into addresses(id, version, street_address, city, country_code, postal_code, state_code, retired_indicator) values(19917, 0, '9000 Rockville Pike', 'Bethesda', 'USA', 20892, 'MD', 'false')")
    		execute("insert into addresses(id, version, street_address, city, country_code, postal_code, state_code, retired_indicator) values(19918, 0, '6116 Executive Boulevard', 'Bethesda', 'USA', 20892, 'MD', 'false')")
    	
    		execute("insert into organizations(id, version, nci_institute_code, dtype, name, retired_indicator, address_id) values(19917, 0, 'CTEP', 'Local', 'Cancer Therapy Evaluation Program', 'false', 19917)")
    		execute("insert into organizations(id, version, nci_institute_code, dtype, name, retired_indicator, address_id) values(19918, 0, 'NCI', 'Local', 'National Cancer Institute', 'false', 19918)")
    	
        	execute("INSERT INTO identifiers (id, version, retired_indicator,dtype,type,primary_indicator,value,org_id,hcs_id) select identifiers_ID_SEQ.nextval, 0, 'false', 'OAI', 'CTEP', '1', nci_institute_code, id, 19917 from organizations");
        }
    }

    void down() {
    	
	}
  }	
