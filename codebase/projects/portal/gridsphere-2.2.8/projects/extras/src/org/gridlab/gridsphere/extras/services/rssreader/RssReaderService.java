package org.gridlab.gridsphere.extras.services.rssreader;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.extras.services.rssreader.RssFeed;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: RssReaderService.java,v 1.1.1.1 2007-02-01 20:08:19 kherm Exp $
 */

public interface RssReaderService extends PortletService {


    /**
     * Returns the RssFeed for a given URL
     * @param url
     * @return
     */
    public RssFeed getRssFeed(String url);


}
