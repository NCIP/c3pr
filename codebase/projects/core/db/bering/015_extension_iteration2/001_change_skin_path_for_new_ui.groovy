class setNewSkinPath extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		delete('configuration', [ prop:"skinPath" ])
		insert('configuration', [ prop:"skinPath", value:"mocha" ], primaryKey: false)
	}

	void down(){
			delete('configuration', [ prop:"skinPath" ])
			insert('configuration', [ prop:"skinPath", value:"blue" ], primaryKey: false)
	}
}
}
