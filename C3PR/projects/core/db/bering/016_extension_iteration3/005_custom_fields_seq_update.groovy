class CustomFieldTablesUpdate extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	    if (databaseMatches('oracle')) {
	    	execute("rename SEQ_cust_field_defns_ID to cust_field_defns_ID_SEQ")
	    	execute("rename SEQ_custom_fields_ID to custom_fields_ID_SEQ")
	 	} 
    }

    void down() {
    }
}
