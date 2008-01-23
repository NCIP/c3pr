class ConfigDefaultProperties extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		insert('configuration', [ key:"hostedMode", value:"true" ], primaryKey: false)
	}

	void down(){

	}
}