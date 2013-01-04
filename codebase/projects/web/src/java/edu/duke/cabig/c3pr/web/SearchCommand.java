/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web;

/**
 * @author Priyatam
 */

public class SearchCommand {

    private String searchText;

    private String searchType;
    
    private String statusSearchText;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

	public String getStatusSearchText() {
		return statusSearchText;
	}

	public void setStatusSearchText(String statusSearchText) {
		this.statusSearchText = statusSearchText;
	}

}
