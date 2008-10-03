class AddMultisiteConfigDefaultProperty extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			insert('configuration', [ prop:"multisiteEnable", value:"false" ], primaryKey: false)
	}

	void down(){

	}
}