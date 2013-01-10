/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.report.tabs;

import edu.duke.cabig.c3pr.domain.Summary3Report;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.WorkFlowTab;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 13, 2007 Time: 7:27:09 PM To change this template
 * use File | Settings | File Templates.
 */
public class Summary3ReportDetailsTab extends WorkFlowTab<Summary3Report> {

	public Summary3ReportDetailsTab() {
        super("Summary 3 Report", "Summary 3 Report", "report/summary_3_report_details");
    }

}
