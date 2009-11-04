class AddCoppaSwitchConfig extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			insert('configuration', [ prop:"coppaEnable", value:"true" ], primaryKey: false)			
	}

	void down(){
	        delete('configuration', [ prop:"coppaEnable" ])
	}
}
