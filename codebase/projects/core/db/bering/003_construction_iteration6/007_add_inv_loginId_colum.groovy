class AddPersonLoginIDColumn  extends edu.northwestern.bioinformatics.bering.Migration {


    public void up(){
        addColumn('INVESTIGATORS','LOGIN_ID','string');
    }


    public void down(){
        dropColumn('INVESTIGATORS', 'LOGIN_ID');
    }

  }

