class ConsentVersionTable extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        createTable("consent_history") { t ->
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn("stu_sub_id", "integer")
            t.addColumn("consent_version", "string")
            t.addColumn("consent_signed_date", "date")
        }
    }

    void down() {
        dropTable("consent_history")
    }
}
