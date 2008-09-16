class LoadingCooperativeGroups extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
    
    	m0()
        m1()
        m2()
        m3()
        m4()
        m5()
        m6()
        m7()
        m8()
        m9()
        }
     
        void m0()
        {	
        	insert('addresses', [ id:10006,version:0,street_address:"2400 Pratt Street",city:"Durham",state_code:"NC",country_code:"USA",postal_code:"27705"], primaryKey: false)
            insert('organizations', [ id:10006,version:0,name:"American College of Surgeons Oncology Trials Group",address_id:10006,nci_institute_code:"ACOSOG"], primaryKey: false)
      	 }
      	 void m1()
        {
        	insert('addresses', [ id:10007,version:0,street_address:"230 West Monroe",city:"Chicago",state_code:"IL",country_code:"USA",postal_code:"60606"], primaryKey: false)
            insert('organizations', [ id:10007,version:0,name: "Cancer and Leukemia Group B",address_id:10007,nci_institute_code:"CALGB"], primaryKey: false)
      	 }
      	 void m2()
        {
        	insert('addresses', [ id:10008,version:0,street_address:"900 Commonwealth Avenue",city:"Boston",state_code:"MA",country_code:"USA",postal_code:"02215"], primaryKey: false)
            insert('organizations', [ id:10008,version:0,name: "Eastern Cooperative Oncology Group",address_id:10008,nci_institute_code:"ECOG"], primaryKey: false)
      	 }
      	 void m3()
        {
        	insert('addresses', [ id:10009,version:0,street_address:"JOHN F. KENNEDY BLVD ",city:"PHILADELPHIA",state_code:"PA",country_code:"USA",postal_code:"19103"], primaryKey: false)
            insert('organizations', [ id:10009,version:0,name: "Gynecologic Oncology Group",address_id:10009,nci_institute_code:"GOG"], primaryKey: false)
      	 }
      	 void m4()
        {
        	insert('addresses', [ id:10010,version:0,street_address:"200 First Street SW",city:"Rochester",state_code:"MN",country_code:"USA",postal_code:"55905"], primaryKey: false)
            insert('organizations', [ id:10010,version:0,name: "North Central Cancer Treatment Group",address_id:10010,nci_institute_code:"NCCTG"], primaryKey: false)
      	 }
      	 void m5()
        {
        	insert('addresses', [ id:10011,version:0,street_address:"10 Alcorn Avenue",city:"Toronto",state_code:"Ontario",country_code:"Canada"], primaryKey: false)
            insert('organizations', [ id:10011,version:0,name: "National Cancer Institute of Canada Clinical Trials Group",address_id:10011,nci_institute_code:"NCIC"], primaryKey: false)
      	 }
      	 void m6()
        {
        	insert('addresses', [ id:10012,version:0,street_address:"Four Allegheny Center ",city:"Pittsburgh",state_code:"PA",country_code:"USA",postal_code:"15212-5234"], primaryKey: false)
            insert('organizations', [ id:10012,version:0,name: "National Surgical Adjuvant Breast and Bowel Project",address_id:10012,nci_institute_code:"NSABP"], primaryKey: false)
      	 }
      	 void m7()
        {
        	insert('addresses', [ id:10013,version:0,street_address:"1818 Market Street",city:"Philadelphia",state_code:"PA",country_code:"USA",postal_code:"19103-3604"], primaryKey: false)
            insert('organizations', [ id:10013,version:0,name: "Radiation Therapy Oncology Group",address_id:10013,nci_institute_code:"RTOG"], primaryKey: false)
      	 }
      	 void m8()
        {
        	insert('addresses', [ id:10014,version:0,street_address:"24 Frank Lloyd Wright Drive",city:"Ann Arbor",state_code:"MI",country_code:"USA",postal_code:"48106-0483"], primaryKey: false)
            insert('organizations', [ id:10014,version:0,name: "Southwest Oncology Group",address_id:10014,nci_institute_code:"SWOG"], primaryKey: false)
      	 }
      	 void m9()
        {
        	insert('addresses', [ id:10015,version:0,street_address:"440 East Huntington Drive",city:"Arcadia",state_code:"CA",country_code:"USA",postal_code:"91066-6012"], primaryKey: false)
            insert('organizations', [ id:10015,version:0,name: "Children's Oncology Group",address_id:10015,nci_institute_code:"COG"], primaryKey: false)
      	 }

    void down() {
     	
     	execute("DELETE from ORGANIZATIONS")
     	execute("DELETE from ADDRESSES")	   	  	
      	
   	}
	
}