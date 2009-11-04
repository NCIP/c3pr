class AddIndexOrganizations extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		execute("create index organizations_idx on organizations(name,nci_institute_code)");
	}

	void down(){
		execute("drop index organizations_idx");
	}
}