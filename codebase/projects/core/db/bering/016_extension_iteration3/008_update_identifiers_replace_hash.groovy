class UpdateCsmAuthorizationNamespace extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
	if (databaseMatches('postgres')) {
			execute("update identifiers set value=translate(value,'#','');
		 }
	 
	 if (databaseMatches('oracle')) {
			execute("update identifiers set value = replace(value,'#');
	 	}
	 	
	 if (databaseMatches('sqlserver')) {
			execute("update identifiers set value = replace(value,'#','');
	 	}
	}

	void down(){

	}
}