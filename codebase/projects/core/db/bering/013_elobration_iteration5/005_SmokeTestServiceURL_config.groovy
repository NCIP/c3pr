class LocalNCIInstituteCode extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		insert('configuration', [ key:"smokeTestURL", value:"https://cbvapp-d1017.nci.nih.gov:28445/wsrf/cagrid/SmokeTestServcie" ], primaryKey: false)
	}

	void down(){
         delete('configuration', [ key:"smokeTestURL" ])
	}
}
