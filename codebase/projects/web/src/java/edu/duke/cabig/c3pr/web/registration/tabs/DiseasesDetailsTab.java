/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.registration.tabs;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class DiseasesDetailsTab extends RegistrationTab<StudySubjectWrapper> {

    public DiseasesDetailsTab() {
        super("Diseases", "Diseases", "registration/reg_diseases");
    }
}
