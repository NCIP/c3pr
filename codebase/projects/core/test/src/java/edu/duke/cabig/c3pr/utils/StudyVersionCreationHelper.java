/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import edu.duke.cabig.c3pr.constants.StatusType;
import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyVersion;


public class StudyVersionCreationHelper {

	public StudyVersion buildBasicStudyVersion(){
		Study study = new LocalStudy();

		StudyVersion studyVersion = new StudyVersion();
        studyVersion.setPrecisText("Study with randomization");
        studyVersion.setShortTitleText("ShortTitleText1");
        studyVersion.setLongTitleText("LongTitleText1");
        studyVersion.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
        studyVersion.setDescriptionText("Description Text");
        studyVersion.setComments("basic study");
        studyVersion.setDescriptionText("Description");
        studyVersion.setName("Default version");
        studyVersion.setStudy(study);
        studyVersion.setVersionStatus(StatusType.AC);
        //TODO: fix it later
        //studyVersion.setMandatoryIndicator(false);
        return studyVersion;
	}


}
