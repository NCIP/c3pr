class CustomFieldTablesUpdate extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	    dropColumn('cust_field_defns','domain_class');
	    dropColumn('custom_fields','field_data_type');
	    addColumn('cust_field_defns', "DTYPE", "string")
	    addColumn('custom_fields', "DTYPE", "string")
    }

    void down() {
        dropColumn('cust_field_defns','DTYPE');
	    dropColumn('custom_fields','DTYPE');
	    addColumn('cust_field_defns', "domain_class", "string")
	    addColumn('custom_fields', "field_data_type", "string")
    }
}
