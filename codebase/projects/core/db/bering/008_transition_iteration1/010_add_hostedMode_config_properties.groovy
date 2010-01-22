class ConfigDefaultProperties extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		// ewyles@uams.edu -- had to change this because sqlserver doesn't like "key" as a column name
		if (databaseMatches('sqlserver')) {		
			insert('configuration', [ key_ms:"hostedMode", value:"true" ], primaryKey: false)
			insert('configuration', [ key_ms:"c3pr.webapp.url", value:"" ], primaryKey: false)
		} else {
		insert('configuration', [ key:"hostedMode", value:"true" ], primaryKey: false)
		insert('configuration', [ key:"c3pr.webapp.url", value:"" ], primaryKey: false)
	}
	}

	void down(){

	}
}