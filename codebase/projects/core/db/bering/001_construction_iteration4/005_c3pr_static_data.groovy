 class StaticData extends edu.northwestern.bioinformatics.bering.Migration {
     void up() {
         if (databaseMatches('oracle')) {
             external("../../oracle/static-data.sql")
         } else if (databaseMatches('postgresql')){
             external("../../PostGreSQL/static-data.sql")
         }
     }

     void down() {
             execute("DELETE FROM anatomic_sites");
             execute("DELETE FROM RESEARCH_STAFF");
             execute("DELETE FROM HC_SITE_INVESTIGATORS");
             execute("DELETE FROM INVESTIGATORS");
             execute("DELETE FROM ORGANIZATIONS");
             execute("DELETE FROM ADDRESSES");
     }
 }



