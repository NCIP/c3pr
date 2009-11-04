class CustomFieldTables extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        createTable("cust_field_defns") { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn("display_label", "string")
            t.addColumn("display_name", "string")
            t.addColumn("mandatory_indicator", "boolean")
            t.addColumn("max_length", "integer")
            t.addColumn("field_type", "string")
            t.addColumn("data_type", "string")
            t.addColumn("display_page", "string")
            t.addColumn("domain_class", "string")
            t.addColumn("stu_id", "integer")
            t.addColumn("org_id", "integer")
        }
        
        createTable("cust_field_perm_values") { t ->
            t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn("display_name", "string")
            t.addColumn("value", "string")
            t.addColumn("cust_field_def_id", "integer")
        }
        
        createTable("custom_fields") { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn("integer_value", "integer")
            t.addColumn("date_value", "date")
            t.addColumn("string_value", "string")
            t.addColumn("boolean_value", "boolean")
            t.addColumn("field_data_type", "string")
            t.addColumn("stu_id", "integer")
            t.addColumn("stu_sub_id", "integer")
            t.addColumn("sub_id", "integer")
            t.addColumn("cust_field_def_id", "integer")
        }
    }

    void down() {
        dropTable("custom_fields")
        dropTable("cust_field_perm_values")
        dropTable("cust_field_defns")
    }
}
