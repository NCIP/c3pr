class AddMultisiteConfigDefaultProperty extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		insert('configuration', [ key:"multisiteEnable", value:"false" ], primaryKey: false)
	}

	void down(){

	}
}