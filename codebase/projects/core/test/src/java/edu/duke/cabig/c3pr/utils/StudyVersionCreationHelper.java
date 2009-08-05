package edu.duke.cabig.c3pr.utils;

import edu.duke.cabig.c3pr.constants.StatusType;
import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyVersion;


public class StudyVersionCreationHelper {

	public StudyVersion buildBasicStudyVersion(){
		Study study = new Study();

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
        return studyVersion;
	}

}
