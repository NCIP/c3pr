class RefactorIdentifierModel extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    
       		addColumn('IDENTIFIERS', "DTYPE", "string")
	    	setNullable('IDENTIFIERS', 'DTYPE', false)
	    	setNullable('IDENTIFIERS', 'SOURCE', true)
	    	
	     	addColumn('IDENTIFIERS', "HCS_ID", "integer")
	    		    	
	    	execute("alter table IDENTIFIERS add constraint FK_IDN_HCS FOREIGN KEY (HCS_ID) references ORGANIZATIONS(ID)")
	     	execute("update identifiers set hcs_id = (select id from organizations where name like source)")
	     	execute("update IDENTIFIERS set dtype='SAI' where HCS_ID is null")
	     	execute("update identifiers set dtype = 'OAI' where HCS_ID is not null");
	     	execute("update identifiers set source ='' where HCS_ID is not null");
	     	
	     	renameColumn('IDENTIFIERS','SOURCE','SYSTEM_NAME')
	     			        	
   	 	}

    void down() {
       
    		renameColumn('IDENTIFIERS','SYSTEM_NAME',"SOURCE")
    		execute("update identifiers set SOURCE = (select name from organizations where id like hcs_id)")
      		setNullable('IDENTIFIERS','SOURCE',false)
    		dropColumn('IDENTIFIERS','hcs_ID')
    		dropColumn('IDENTIFIERS','DTYPE')
    		
	    	}
	    }