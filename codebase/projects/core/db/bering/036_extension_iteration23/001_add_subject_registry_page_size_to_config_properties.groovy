class AddSubjectRegistryPageSizeToConfigProperties extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			execute("INSERT INTO configuration(prop, value) values ('subjectRegistryService.page_size', 40)")
	}

	void down(){

	}
}