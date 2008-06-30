class AddMultisiteConfigDefaultProperty extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		insert('configuration', [ key:"siteName", value:"<SITE NAME>" ], primaryKey: false)
	}

	void down(){
         delete('configuration', [ key:"siteName" ])
	}
}
