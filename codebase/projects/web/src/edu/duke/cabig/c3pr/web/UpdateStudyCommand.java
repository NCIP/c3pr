package edu.duke.cabig.c3pr.web;

import edu.duke.cabig.c3pr.domain.Study;

public class UpdateStudyCommand {
	
	private SearchStudyCommand searchStudyCommand = new SearchStudyCommand(); 
	private Study study = new Study();
	
	public UpdateStudyCommand(Study study)
	{
		this.study=study;
	}
	
	public UpdateStudyCommand(){}

	public SearchStudyCommand getSearchStudyCommand() {
		return searchStudyCommand;
	}

	public void setSearchStudyCommand(SearchStudyCommand searchStudyCommand) {
		this.searchStudyCommand = searchStudyCommand;
	}

	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}		
}
