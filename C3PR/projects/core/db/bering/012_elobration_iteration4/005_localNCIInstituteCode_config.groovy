class LocalNCIInstituteCode extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			insert('configuration', [ prop:"localNciInstituteCode", value:"CRB" ], primaryKey: false)
	}

	void down(){
			delete('configuration', [ prop:"localNciInstituteCode" ])
	}
}
