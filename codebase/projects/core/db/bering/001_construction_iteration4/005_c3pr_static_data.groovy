 class StaticData extends edu.northwestern.bioinformatics.bering.Migration {
     void up() {
         if (databaseMatches('oracle')) {
             external("../../oracle/static-data.sql")
         } else if (databaseMatches('postgresql')){
             external("../../PostGreSQL/static-data.sql")
         } else if (databaseMatches('sqlserver')){
             external("../../SQLServer/static-data.sql")
         }
     }

     void down() {
      if (databaseMatches('oracle')) {
             external("../../oracle/static-data-delete.sql")
        } else if (databaseMatches('postgresql')){
             external("../../PostGreSQL/static-data-delete.sql")
        } else if (databaseMatches('sqlserver')){
             external("../../SQLServer/static-data-delete.sql")
        }
     }
 }



