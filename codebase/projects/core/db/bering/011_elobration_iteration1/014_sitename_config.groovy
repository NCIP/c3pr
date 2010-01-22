class AddMultisiteConfigDefaultProperty extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			insert('configuration', [ prop:"siteName", value:"" ], primaryKey: false)
	}

	void down(){
			delete('configuration', [ prop:"siteName" ])
	}
}
