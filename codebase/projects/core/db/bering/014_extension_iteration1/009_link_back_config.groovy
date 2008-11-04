class LocalNCIInstituteCode extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			insert('configuration', [ prop:"notification.link_back", value:"true" ], primaryKey: false)			
	}

	void down(){
	        delete('configuration', [ prop:"notification.link_back" ])
	}
}
