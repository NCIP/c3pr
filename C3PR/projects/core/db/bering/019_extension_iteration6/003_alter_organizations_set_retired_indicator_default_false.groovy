class AlterOrganizationsSetRetiredIndicatorDefaultFalse extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
	
		if (databaseMatches('postgres')) {
				execute("alter table organizations alter column retired_indicator set default 'false'")
			}
		 if (databaseMatches('oracle')){
			execute("alter table organizations modify retired_indicator default 'false'")
			}
	}

	void down(){
	}
}
