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
