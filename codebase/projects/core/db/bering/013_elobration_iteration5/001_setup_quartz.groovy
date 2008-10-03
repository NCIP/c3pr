class SetupQuartz extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        if (databaseMatches('oracle')) {
            external("quartz_oracle_create.sql")
        } else if (databaseMatches('postgresql')){
            external("quartz_postgresql_create.sql")
        } else if (databaseMatches('sqlserver')){
            external("quartz_sqlserver_create.sql")
        }else {
            external("quartz_hsqldb_create.sql")
        }
    }
    
    void down() {
        if (databaseMatches('oracle')) {
            external("quartz_oracle_drop.sql")
        } else if (databaseMatches('postgresql')) {
            external("quartz_postgresql_drop.sql")
        } else if (databaseMatches('sqlserver')){
            external("quartz_sqlserver_drop.sql")
        }else {
            external("quartz_hsqldb_drop.sql")
        }
    }
}

