class RefactorStudyOrganizations extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    		execute("alter table STUDY_SITES drop constraint FK_STS_ORG")
	    	execute("alter table STUDY_SITES drop constraint FK_STS_STU")
	    	execute("alter table STUDY_INVESTIGATORS drop constraint FK_STI_STS")
	    	execute("alter table STUDY_PERSONNELS drop constraint FK_STDPER_STDSIT")
	    	execute("alter table STUDY_SUBJECTS drop constraint FK_SPA_STS")
	    	 		
	 		execute("alter table STUDY_SITES drop constraint PK_STUDY_SITES")
	 		
			renameTable('STUDY_SITES', 'STUDY_ORGANIZATIONS')

			if (databaseMatches('postgres')) {
	 		 execute("ALTER TABLE STUDY_ORGANIZATIONS ALTER COLUMN id SET DEFAULT nextval('STUDY_ORGANIZATIONS_ID_SEQ'::regclass)")	   
	 		}
	      	setNullable('STUDY_ORGANIZATIONS', 'ROLE_CODE', true)
	    	setNullable('STUDY_ORGANIZATIONS', 'STATUS_CODE', true)
	    	addColumn('STUDY_ORGANIZATIONS', "TYPE", "string")
	    	execute("update STUDY_ORGANIZATIONS set type='SST'")
	    	setNullable('STUDY_ORGANIZATIONS', 'TYPE', false)
	    	    	    		    	
	    	execute("alter table STUDY_ORGANIZATIONS add constraint PK_STUDY_ORGANIZATIONS PRIMARY KEY (ID)")
	    	
	    	dropColumn('STUDY_INVESTIGATORS', "STS_ID")
	    	addColumn('STUDY_INVESTIGATORS', "STO_ID", "string")
	    	dropColumn('STUDY_PERSONNELS', "STS_ID")
	    	addColumn('STUDY_PERSONNELS', "STO_ID", "string")
	    	dropColumn('STUDY_SUBJECTS', "STS_ID")
	    	addColumn('STUDY_SUBJECTS', "STO_ID", "string")
	    	
		    execute("alter table STUDY_INVESTIGATORS add constraint FK_STI_STO FOREIGN KEY (STO_ID) REFERENCES STUDY_ORGANIZATIONS (ID)")
			execute("alter table STUDY_SUBJECTS add constraint FK_SPA_STO FOREIGN KEY (STO_ID) REFERENCES STUDY_ORGANIZATIONS (ID)")
			execute("alter table STUDY_PERSONNELS add constraint FK_STDPER_STO FOREIGN KEY (STO_ID) REFERENCES STUDY_ORGANIZATIONS (ID)")
			execute("alter table STUDY_ORGANIZATIONS add constraint FK_STO_STU FOREIGN KEY (STUDY_ID) REFERENCES STUDIES (ID)")
			execute("alter table STUDY_ORGANIZATIONS add constraint FK_STO_ORG FOREIGN KEY (HCS_ID) REFERENCES ORGANIZATIONS (ID)")
   	 }

    void down() {
    
    		execute("alter table STUDY_ORGANIZATIONS drop constraint FK_STO_ORG")
    		execute("alter table STUDY_ORGANIZATIONS drop constraint FK_STO_STU")
    		execute("alter table STUDY_PERSONNELS drop constraint FK_STDPER_STO")
    		execute("alter table STUDY_SUBJECTS drop constraint FK_SPA_STO")
    		execute("alter table STUDY_INVESTIGATORS drop constraint FK_STI_STO")	
    		
    		dropColumn('STUDY_SUBJECTS', "STO_ID")
	    	addColumn('STUDY_SUBJECTS', "STS_ID", "string")
   	    	dropColumn('STUDY_PERSONNELS', "STO_ID")
	    	addColumn('STUDY_PERSONNELS', "STS_ID", "string")
	    	dropColumn('STUDY_INVESTIGATORS', "STO_ID")
	    	addColumn('STUDY_INVESTIGATORS', "STS_ID", "string")
   	
   			    	
	    	execute("alter table STUDY_ORGANIZATIONS drop constraint PK_STUDY_ORGANIZATIONS")   	
			
			setNullable('STUDY_ORGANIZATIONS', 'TYPE', true)
	    	dropColumn('STUDY_ORGANIZATIONS', "TYPE")
	    	setNullable('STUDY_ORGANIZATIONS', 'STATUS_CODE', false)
	    	setNullable('STUDY_ORGANIZATIONS', 'ROLE_CODE', false)
	    	 
	    	if (databaseMatches('oracle')) {
	    	execute("RENAME STUDY_ORGANIZATIONS_ID_SEQ to STUDY_SITES_ID_SEQ")
	 		}	
	 	 	if (databaseMatches('postgres')) {
	    	execute("alter table STUDY_ORGANIZATIONS_ID_SEQ rename to STUDY_SITES_ID_SEQ")
	 		} 	
	 		
	 		renameTable('STUDY_ORGANIZATIONS','STUDY_SITES') 
    
       	    execute("alter table STUDY_ORGANIZATIONS add CONSTRAINT PK_STUDY_SITES PRIMARY KEY (ID)")
       	    execute("alter table STUDY_SUBJECTS add CONSTRAINT FK_SPA_STS FOREIGN KEY (STS_ID) REFERENCES STUDY_SITES (ID)")   	    	    	  	
	    	execute("alter table STUDY_PERSONNELS add CONSTRAINT FK_STDPER_STDSIT FOREIGN KEY (STS_ID) REFERENCES STUDY_SITES (ID)")
	    	execute("alter table STUDY_INVESTIGATORS add CONSTRAINT FK_STI_STS FOREIGN KEY (STS_ID) REFERENCES STUDY_SITES (ID)")	    	
	    	execute("alter table STUDY_SITES add CONSTRAINT FK_STS_STU FOREIGN KEY (STUDY_ID) REFERENCES STUDIES (ID)")	    	
	    	execute("alter table STUDY_SITES ADD CONSTRAINT FK_STS_ORG FOREIGN KEY (HCS_ID) REFERENCES ORGANIZATIONS (ID)")
	    	}
	    }