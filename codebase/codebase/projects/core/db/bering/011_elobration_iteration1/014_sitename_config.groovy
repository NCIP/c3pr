class AddMultisiteConfigDefaultProperty extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		insert('configuration', [ key:"siteName", value:"<SITE BANNER URL>" ], primaryKey: false)
	}

	void down(){
         delete('configuration', [ key:"siteName" ])
	}
}
