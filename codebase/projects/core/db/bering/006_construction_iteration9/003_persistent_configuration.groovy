class PersistentConfiguration extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        createTable("configuration") { t ->
            t.includePrimaryKey = false
            // ewyles@uams.edu -- had to change this because sqlserver doesn't like "key" as a column name
			if (databaseMatches('sqlserver')) {
				t.addColumn("key_ms", "string", primaryKey: true)
			} else {
            	t.addColumn("key", "string", primaryKey: true)
            }
            t.addColumn("value", "string")
            t.addVersionColumn()
        }
    }

    void down() {
        dropTable("configuration", primaryKey: false)
    }
}
