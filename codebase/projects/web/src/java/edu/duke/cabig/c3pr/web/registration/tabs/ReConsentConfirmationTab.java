/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.registration.tabs;

import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

public class ReConsentConfirmationTab extends RegistrationTab<StudySubjectWrapper> {

	public ReConsentConfirmationTab() {
        super("Confirmation", "Confirmation", "registration/reg_re_consent_confirmation");
    }
}
