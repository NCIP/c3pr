class AddNciInstituteCode extends edu.northwestern.bioinformatics.bering.Migration {

    void up() {
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = 'Duke' where ID='1001'")
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = 'Warren Grant' where ID='1002'")
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = 'NCI' where ID='1003'")
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = 'C3PR' where ID='1004'")
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = 'Wake Forest' where ID='1005'")

   	 }

    void down() {
    	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = '' where ID='1001'")
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = '' where ID='1002'")
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = '' where ID='1003'")
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = '' where ID='1004'")
   	 	execute("update ORGANIZATIONS set NCI_INSTITUTE_CODE = '' where ID='1005'")
	}

}