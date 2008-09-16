class LocalNCIInstituteCode extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		insert('configuration', [ key:"localNciInstituteCode", value:"CRB" ], primaryKey: false)
	}

	void down(){
         delete('configuration', [ key:"localNciInstituteCode" ])
	}
}
