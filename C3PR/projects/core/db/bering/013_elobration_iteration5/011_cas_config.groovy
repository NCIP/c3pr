class LocalNCIInstituteCode extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			insert('configuration', [ prop:"cas.base_url", value:"" ], primaryKey: false)
			insert('configuration', [ prop:"cas.cert_file", value:"" ], primaryKey: false)
	}

	void down(){
	        delete('configuration', [ prop:"cas.base_url" ])
	        delete('configuration', [ prop:"cas.cert_file" ])
	}
}
