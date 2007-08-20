class AddNciInstituteCode extends edu.northwestern.bioinformatics.bering.Migration {

    void up() {
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = 'Duke' where ID='10001'")
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = 'Warren' where ID='10002'")
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = 'NCI' where ID='10003'")
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = 'C3PR' where ID='10004'")
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = 'Wake' where ID='10005'")
   	 }

    void down() {
    	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = '' where ID='10001'")
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = '' where ID='10002'")
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = '' where ID='10003'")
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = '' where ID='10004'")
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = '' where ID='10005'")
	}

}