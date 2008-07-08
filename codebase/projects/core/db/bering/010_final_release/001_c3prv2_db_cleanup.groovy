class CleanC3prv2DB extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        if (databaseMatches('oracle')) {
            external("../../oracle/static-data-delete.sql")
        } else if (databaseMatches('postgresql')){
            external("../../PostGreSQL/static-data-delete.sql")
        }
    }

    void down() {
        if (databaseMatches('oracle')) {
            external("../../oracle/static-data-delete.sql")
        } else if (databaseMatches('postgresql')) {
          external("../../oracle/static-data-delete.sql")
        }
    }
}
