class ConfigDefaultProperties extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		insert('configuration', [ key:"hostedMode", value:"true" ], primaryKey: false)
		insert('configuration', [ key:"c3pr.webapp.url", value:"https://cbvapp-d1017.nci.nih.gov:28443/c3pr" ], primaryKey: false)
	}

	void down(){

	}
}