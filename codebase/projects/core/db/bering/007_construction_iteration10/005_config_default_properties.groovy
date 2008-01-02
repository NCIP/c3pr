class ConfigDefaultProperties extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		insert('configuration', [ key:"pscBaseUrl", value:"https://cbvapp-d1017.nci.nih.gov:28443/psc/pages/schedule" ], primaryKey: false)
		insert('configuration', [ key:"caaersBaseUrl", value:"https://cbvapp-d1017.nci.nih.gov:28443/caaers/pages/ae/list" ], primaryKey: false)
		insert('configuration', [ key:"c3dViewerBaseUrl", value:"https://octrials-train.nci.nih.gov/opa45/rdclaunch.htm" ], primaryKey: false)
		insert('configuration', [ key:"esbUrl", value:"https://cbvapp-d1017.nci.nih.gov:28445/wsrf/cagrid/CaXchangeRequestProcessor" ], primaryKey: false)
		insert('configuration', [ key:"authorizationSwitch", value:"false" ], primaryKey: false)
		insert('configuration', [ key:"esbEnable", value:"false" ], primaryKey: false)
		insert('configuration', [ key:"outgoingMailServer", value:"smtp.comcast.net" ], primaryKey: false)
		insert('configuration', [ key:"outgoingMailServerPort", value:"25" ], primaryKey: false)
	}

	void down(){

	}
}