class defaultCompanionStudyColumns extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        if (databaseMatches('oracle')) {
        	execute("ALTER TABLE STUDIES ALTER COLUMN standalone_indicator SET DEFAULT 1")
    		execute("ALTER TABLE STUDIES ALTER COLUMN standalone_indicator SET DEFAULT 0")
    		execute("update studies set standalone_indicator='1' where standalone_indicator IS NULL ")
        	execute("update studies set companion_indicator='0' where companion_indicator IS NULL ")
        } else if (databaseMatches('postgresql')){
        	execute("ALTER TABLE STUDIES ALTER COLUMN standalone_indicator SET DEFAULT true")
    		execute("ALTER TABLE STUDIES ALTER COLUMN standalone_indicator SET DEFAULT false")
    		execute("update studies set standalone_indicator='true' where standalone_indicator IS NULL ")
        	execute("update studies set companion_indicator='false' where companion_indicator IS NULL ")
        } else {
        	execute("ALTER TABLE STUDIES ALTER COLUMN standalone_indicator SET DEFAULT true")
    		execute("ALTER TABLE STUDIES ALTER COLUMN standalone_indicator SET DEFAULT false")
    		execute("update studies set standalone_indicator='true' where standalone_indicator IS NULL ")
        	execute("update studies set companion_indicator='false' where companion_indicator IS NULL ")
        }
    }
    
    void down() {
       
    }
}

