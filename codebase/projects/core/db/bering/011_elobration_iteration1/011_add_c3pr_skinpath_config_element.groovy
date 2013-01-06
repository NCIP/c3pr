class AddMultisiteConfigDefaultProperty extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			insert('configuration', [ prop:"skinPath", value:"blue" ], primaryKey: false)
	}

	void down(){
			delete('configuration', [ prop:"skinPath" ])
	}
}
