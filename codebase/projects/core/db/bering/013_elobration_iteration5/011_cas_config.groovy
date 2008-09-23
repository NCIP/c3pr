class LocalNCIInstituteCode extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		insert('configuration', [ key:"cas.base_url", value:"" ], primaryKey: false)
		insert('configuration', [ key:"cas.cert_file", value:"" ], primaryKey: false)
	}

	void down(){
         delete('configuration', [ key:"cas.base_url" ])
         delete('configuration', [ key:"cas.cert_file" ])
	}
}
