class AddMultisiteConfigDefaultProperty extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		insert('configuration', [ key:"skinPath", value:"blue" ], primaryKey: false)
	}

	void down(){
         delete('configuration', [ key:"skinPath" ])
	}
}
