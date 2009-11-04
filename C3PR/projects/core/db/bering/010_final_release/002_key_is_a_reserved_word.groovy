// KEY is a reserved word on MS-SQL, MySQL, and probably others.
// We shouldn't use it as a column name.  Instead, we'll use "prop",
// short for property (which is also a reserved word in some systems).
class KeyIsAReservedWord extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		if (databaseMatches('sqlserver')) {
			renameColumn("configuration", "key_ms", "prop")
		} else {
			renameColumn("configuration", "key", "prop")
		}
    }

    void down() {
		if (databaseMatches('sqlserver')) {	
			renameColumn("configuration", "prop", "key_ms")
		} else {
			renameColumn("configuration", "prop", "key")
		}
    }
}