class AddMultisiteConfigDefaultProperty extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			insert('configuration', [ prop:"outgoingMailUsername", value:"c3prproject@gmail.com" ], primaryKey: false)
			insert('configuration', [ prop:"outgoingMailPassword", value:"semanticbits" ], primaryKey: false)
	}

	void down(){

	}
}