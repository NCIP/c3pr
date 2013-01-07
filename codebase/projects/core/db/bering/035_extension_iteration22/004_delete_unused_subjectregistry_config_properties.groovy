class DeleteUnusedSRSConfigProperties extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			execute("delete from configuration where prop like 'subjectRegistryService.loadSubjectIdentifier'")
			execute("delete from configuration where prop like 'subjectRegistryService.loadStudySiteIdentifier'")
	}

	void down(){

	}
}