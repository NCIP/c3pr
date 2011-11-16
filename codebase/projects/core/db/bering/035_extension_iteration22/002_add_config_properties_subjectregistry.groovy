class AddSRSConfigDefaultProperty extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			execute("INSERT INTO configuration(prop, value) values ('subjectRegistryService.loadStudy', 'false')")
			execute("INSERT INTO configuration(prop, value) values ('subjectRegistryService.loadStudySite', 'false')")
			execute("INSERT INTO configuration(prop, value) values ('subjectRegistryService.loadSubjectIdentifier', 'false')")
			execute("INSERT INTO configuration(prop, value) values ('subjectRegistryService.loadStudySiteIdentifier', 'false')")
			execute("INSERT INTO configuration(prop, value) values ('subjectRegistryService.loadStudyIdentifier', 'false')")
	}

	void down(){

	}
}