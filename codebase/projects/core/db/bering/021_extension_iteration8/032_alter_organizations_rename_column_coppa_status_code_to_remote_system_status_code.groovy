class AlterOrganizationsRenameColumnCoppaStatusCodeToRemoteSystemStatusCode extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		renameColumn('Organizations','coppa_status_code','remote_system_status_code')
	}

	void down(){
			renameColumn('Organizations','remote_system_status_code','coppa_status_code')
	}
}