class AddMultisiteConfigDefaultProperty extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			insert('configuration', [ prop:"siteName", value:"<SITE BANNER URL>" ], primaryKey: false)
	}

	void down(){
			delete('configuration', [ prop:"siteName" ])
	}
}
