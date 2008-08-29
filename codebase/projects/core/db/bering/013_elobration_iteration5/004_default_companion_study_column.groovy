class defaultCompanionStudyColumns extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        if (databaseMatches('oracle')) {
        	execute("alter table STUDIES modify standalone_indicator default 1")
    		execute("alter table STUDIES modify standalone_indicator default 0")
        } else if (databaseMatches('postgresql')){
        	execute("alter table STUDIES modify standalone_indicator default 'true'")
    		execute("alter table STUDIES modify standalone_indicator default 'false'")
        } else {
        	execute("alter table STUDIES modify standalone_indicator default 'true'")
    		execute("alter table STUDIES modify standalone_indicator default 'false'")
        }
    }
    
    void down() {
       
    }
}

