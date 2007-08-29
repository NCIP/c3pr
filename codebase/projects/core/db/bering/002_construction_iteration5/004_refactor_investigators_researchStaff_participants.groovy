class RefactorInvestigatorsResearchStaffParticipants extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       if (databaseMatches('postgres')) {
    		execute("alter table INVESTIGATORS add Middle_Name varchar(200)")
	    	execute("alter table PARTICIPANTS add Middle_Name varchar(200)")
	    	execute("alter table RESEARCH_STAFFS add Middle_Name varchar(200)")
	    	execute("alter table RESEARCH_STAFFS add NCI_IDENTIFIER varchar(50)")
	    	execute("alter table STUDIES add RANDOMIZATION_TYPE SMALLINT")
	 	}
       if (databaseMatches('oracle')) {
    		execute("alter table INVESTIGATORS add Middle_Name VARCHAR2(200)")
	    	execute("alter table PARTICIPANTS add Middle_Name VARCHAR2(200)")
	    	execute("alter table RESEARCH_STAFFS add Middle_Name VARCHAR2(200)")
	    	execute("alter table RESEARCH_STAFFS add NCI_IDENTIFIER VARCHAR2(200)")
	    	execute("alter table STUDIES add RANDOMIZATION_TYPE NUMBER(10)")
	 	}
  
    }

    void down() {
        if (databaseMatches('postgres')) {	    	
	    	execute("alter table STUDIES drop RANDOMIZATION_TYPE")
	    	execute("alter table RESEARCH_STAFFS drop NCI_IDENTIFIER")
	    	execute("alter table RESEARCH_STAFFS drop Middle_Name")
	    	execute("alter table PARTICIPANTS drop Middle_Name")
	    	execute("alter table INVESTIGATORS drop Middle_Name")
	 	}
    	if (databaseMatches('oracle')) {
	    	execute("alter table STUDIES drop column RANDOMIZATION_TYPE")
	    	execute("alter table RESEARCH_STAFFS drop column NCI_IDENTIFIER")
	    	execute("alter table RESEARCH_STAFFS drop column Middle_Name")
	    	execute("alter table PARTICIPANTS drop column Middle_Name")
	    	execute("alter table INVESTIGATORS drop column Middle_Name")
	 	}
    }
}