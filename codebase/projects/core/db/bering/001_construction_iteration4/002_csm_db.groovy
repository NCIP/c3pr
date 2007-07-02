class CreateCSMDB extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        if (databaseMatches('oracle')) {
            external("../../oracle/csm/1_AuthSchema_create.sql")
        } else if (databaseMatches('postgresql')){
            external("../../PostGreSQL/csm/1_AuthSchema_create.sql")
        }
    }

    void down() {
        if (databaseMatches('oracle')) {
            external("../../oracle/csm/1_AuthSchema_delete.sql")
        } else if (databaseMatches('postgresql')) {
          external("../../PostGreSQL/csm/1_AuthSchema_delete.sql")
        }
    }
}

