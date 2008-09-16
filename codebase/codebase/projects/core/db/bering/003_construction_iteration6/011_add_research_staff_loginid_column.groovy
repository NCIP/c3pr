class AddResearchStaffLoginIDColumn  extends edu.northwestern.bioinformatics.bering.Migration {


    public void up(){
        addColumn('RESEARCH_STAFF','LOGIN_ID','string');
    }


    public void down(){
        dropColumn('RESEARCH_STAFF', 'LOGIN_ID');
    }

  }

