class AlterResearchStaffAndInvestigatorsRenameUniqueIdentifierToExternalId extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			renameColumn('research_staff','unique_identifier','external_Id');
			renameColumn('investigators','unique_identifier','external_Id');
	}

	void down(){
			renameColumn('research_staff','external_Id','unique_identifier');
			renameColumn('investigators','external_Id','unique_identifier');
	}
}
