class defaultCompanionStudyColumns extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        if (databaseMatches('oracle')) {
        	execute("update studies set standalone_indicator='1' where standalone_indicator = 'null' )
        	execute("update studies set companion_indicator='0' where companion_indicator = 'null' )
        } else if (databaseMatches('postgresql')){
        	execute("update studies set standalone_indicator='true' where standalone_indicator = 'null' )
        	execute("update studies set companion_indicator='false' where companion_indicator = 'null' )
        } else {
        	execute("update studies set standalone_indicator='true' where standalone_indicator = 'null' )
        	execute("update studies set companion_indicator='false' where companion_indicator = 'null' )
        }
    }
    
    void down() {
        if (databaseMatches('oracle')) {
        	execute("update studies set standalone_indicator= null where standalone_indicator = '1' )
        	execute("update studies set companion_indicator= null where companion_indicator = '0' )
        } else if (databaseMatches('postgresql')){
        	execute("update studies set standalone_indicator=null  where standalone_indicator = 'true' )
        	execute("update studies set companion_indicator=null  where companion_indicator = 'false' )
        } else {
        	execute("update studies set standalone_indicator=null  where standalone_indicator = 'true' )
        	execute("update studies set companion_indicator=null  where companion_indicator = 'false' )
        }
    }
}

