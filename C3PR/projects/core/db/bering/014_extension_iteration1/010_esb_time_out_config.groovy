class EsbTimeOutConfig extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			insert('configuration', [ prop:"esb.timeout", value:"120" ], primaryKey: false)			
	}

	void down(){
	        delete('configuration', [ prop:"notification.link_back" ])
	}
}
