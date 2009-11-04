class defaultCompanionStudyColumns extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        if (databaseMatches('oracle')) {
        	execute("ALTER TABLE STUDIES MODIFY standalone_indicator DEFAULT 1")
    		execute("ALTER TABLE STUDIES MODIFY companion_indicator DEFAULT 0")
    		execute("update studies set standalone_indicator='1' where standalone_indicator IS NULL ")
        	execute("update studies set companion_indicator='0' where companion_indicator IS NULL ")
        } else if (databaseMatches('postgresql')){
        	execute("ALTER TABLE STUDIES ALTER COLUMN standalone_indicator SET DEFAULT true")
    		execute("ALTER TABLE STUDIES ALTER COLUMN companion_indicator SET DEFAULT false")
    		execute("update studies set standalone_indicator='true' where standalone_indicator IS NULL ")
        	execute("update studies set companion_indicator='false' where companion_indicator IS NULL ")
        } else  if (databaseMatches('sqlserver')) {
        	execute("ALTER TABLE studies ADD CONSTRAINT DF_STUDIES_STANDALONE_INDICATOR DEFAULT 1 FOR STANDALONE_INDICATOR")
  			execute("ALTER TABLE studies ADD CONSTRAINT DF_STUDIES_COMPANION_INDICATOR DEFAULT 0 FOR COMPANION_INDICATOR")
    		execute("update studies set standalone_indicator=1 where standalone_indicator IS NULL ")
        	execute("update studies set companion_indicator=0 where companion_indicator IS NULL ")
        }
    }
    
    void down() {
       
    }
}

