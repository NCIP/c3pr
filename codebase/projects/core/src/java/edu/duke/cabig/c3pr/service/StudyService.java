package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.Study;

import java.util.List;
import java.util.Date;


/**
 * Interface for Services on Study related domain object
 * 
 * @author priyatam
 */
public interface StudyService extends CCTSWorkflowService {
    public List<Study> searchByExample(Study study, int maxResults);
    public int countAcrrualsByDate(Study study, Date startDate, Date endDate);
}
