class AddBypassAdvancedQueryIndicatorConfigProperties extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			execute("INSERT INTO configuration(prop, value) values ('subjectRegistryService.enableCustomHQL', 'false')")
	}

	void down(){

	}
}