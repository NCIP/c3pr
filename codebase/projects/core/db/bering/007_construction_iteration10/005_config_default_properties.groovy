class ConfigDefaultProperties extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
	    //clear out existing configuration
	    execute("delete from configuration");
	    // ewyles@uams.edu -- had to change this because sqlserver doesn't like "key" as a column name
		if (databaseMatches('sqlserver')) {		
			insert('configuration', [ key_ms:"pscBaseUrl", value:"https://cbvapp-d1017.nci.nih.gov:28443/psc/pages/cal/schedule" ], primaryKey: false)
			insert('configuration', [ key_ms:"caaersBaseUrl", value:"https://cbvapp-d1017.nci.nih.gov:28443/caaers/pages/ae/list" ], primaryKey: false)
			insert('configuration', [ key_ms:"c3dViewerBaseUrl", value:"https://octrials-train.nci.nih.gov/opa45/rdclaunch.htm" ], primaryKey: false)
			insert('configuration', [ key_ms:"esbUrl", value:"https://cbvapp-d1017.nci.nih.gov:28445/wsrf/services/cagrid/CaXchangeRequestProcessor" ], primaryKey: false)
			insert('configuration', [ key_ms:"authorizationSwitch", value:"true" ], primaryKey: false)
			insert('configuration', [ key_ms:"esbEnable", value:"false" ], primaryKey: false)
			insert('configuration', [ key_ms:"outgoingMailServer", value:"smtp.comcast.net" ], primaryKey: false)
			insert('configuration', [ key_ms:"outgoingMailServerPort", value:"25" ], primaryKey: false)
			insert('configuration', [ key_ms:"outgoingMailAuth", value:"true" ], primaryKey: false)
			insert('configuration', [ key_ms:"ccts.websso.base_url", value:"https://cbvapp-d1017.nci.nih.gov:48443/cas" ], primaryKey: false)
			insert('configuration', [ key_ms:"ccts.websso.cert_file", value:"" ], primaryKey: false)
			insert('configuration', [ key_ms:"hostCertificate", value:"/Users/kherm/certs/manav.local-cert.pem" ], primaryKey: false)
			insert('configuration', [ key_ms:"hostKey", value:"/Users/kherm/certs/manav.local-key.pem" ], primaryKey: false)
			insert('configuration', [ key_ms:"authenticationMode", value:"local" ], primaryKey: false)
		} else {
		insert('configuration', [ key:"pscBaseUrl", value:"https://cbvapp-d1017.nci.nih.gov:28443/psc/pages/cal/schedule" ], primaryKey: false)
		insert('configuration', [ key:"caaersBaseUrl", value:"https://cbvapp-d1017.nci.nih.gov:28443/caaers/pages/ae/list" ], primaryKey: false)
		insert('configuration', [ key:"c3dViewerBaseUrl", value:"https://octrials-train.nci.nih.gov/opa45/rdclaunch.htm" ], primaryKey: false)
		insert('configuration', [ key:"esbUrl", value:"https://cbvapp-d1017.nci.nih.gov:28445/wsrf/services/cagrid/CaXchangeRequestProcessor" ], primaryKey: false)
		insert('configuration', [ key:"authorizationSwitch", value:"true" ], primaryKey: false)
		insert('configuration', [ key:"esbEnable", value:"false" ], primaryKey: false)
		insert('configuration', [ key:"outgoingMailServer", value:"smtp.comcast.net" ], primaryKey: false)
		insert('configuration', [ key:"outgoingMailServerPort", value:"25" ], primaryKey: false)
		insert('configuration', [ key:"outgoingMailAuth", value:"true" ], primaryKey: false)
		insert('configuration', [ key:"ccts.websso.base_url", value:"https://cbvapp-d1017.nci.nih.gov:48443/cas" ], primaryKey: false)
		insert('configuration', [ key:"ccts.websso.cert_file", value:"" ], primaryKey: false)
		insert('configuration', [ key:"hostCertificate", value:"/Users/kherm/certs/manav.local-cert.pem" ], primaryKey: false)
		insert('configuration', [ key:"hostKey", value:"/Users/kherm/certs/manav.local-key.pem" ], primaryKey: false)
		insert('configuration', [ key:"authenticationMode", value:"local" ], primaryKey: false)
	}
	}

	void down(){

	}
}