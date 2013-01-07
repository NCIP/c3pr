class SetUserIdSequence extends edu.northwestern.bioinformatics.bering.Migration {

  public void up(){
  		//sqlserver doesn't need sequence manipulation
        if (databaseMatches('postgresql')){
            execute("SELECT setval('CSM_USER_USER_ID_SEQ', 3)");
        }
    }


    public void down(){
        if (databaseMatches('postgresql')){
            execute ("SELECT setval('CSM_USER_USER_ID_SEQ', 1)");

        }
    }
  }

