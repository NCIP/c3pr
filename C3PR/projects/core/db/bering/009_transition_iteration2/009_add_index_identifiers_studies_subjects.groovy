class AddIndexOrganizations extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		execute("create index identifiers_idx on identifiers(value)");
		execute("create index studies_idx on studies(short_title_text,status)");
		execute("create index participants_idx on participants(first_name,last_name)");
	}

	void down(){
		execute("drop index participants_idx");
		execute("drop index studies_idx");
		execute("drop index identifiers_idx");
	}
}