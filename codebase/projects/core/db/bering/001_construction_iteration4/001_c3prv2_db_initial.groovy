class CreateC3prv2DB extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        if (databaseMatches('oracle')) {
            external("../oracle/c3prv2_create_ddl.sql")
        } else if (databaseMatches('postgresql')){
            external("../../PostGreSQL/c3prv2_create_ddl.sql")
        }
    }

    void down() {
        if (databaseMatches('oracle')) {
            external("../oracle/c3prv2_delete_ddl.sql")
        } else if (databaseMatches('postgresql')) {
          external("../../oracle/c3prv2_delete_ddl.sql")
        }
    }
}

