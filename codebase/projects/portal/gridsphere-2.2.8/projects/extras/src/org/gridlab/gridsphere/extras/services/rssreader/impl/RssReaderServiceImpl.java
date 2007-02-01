package org.gridlab.gridsphere.extras.services.rssreader.impl;

import org.gridlab.gridsphere.extras.services.rssreader.RssReaderService;
import org.gridlab.gridsphere.extras.services.rssreader.RssFeed;
import org.gridlab.gridsphere.extras.services.rssreader.RssFeed;
import org.gridlab.gridsphere.extras.services.rssreader.RssReaderService;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;

import java.util.HashMap;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: RssReaderServiceImpl.java,v 1.1.1.1 2007-02-01 20:08:28 kherm Exp $
 */

public class RssReaderServiceImpl implements PortletServiceProvider, RssReaderService {


    private HashMap store = new HashMap();
    private long expire = 1000*60*60;

    public void init(PortletServiceConfig portletServiceConfig) throws PortletServiceUnavailableException {
    }

    public void destroy() {
    }

    private RssFeed fetchFeed(String url) {
        RssFeed result = new RssFeed();
        result.setUrl(url);


        URL feedurl = null;
        BufferedReader in = null;
        String rss = "";

        try {
            feedurl = new URL(url);
            URLConnection feedc = feedurl.openConnection();
            in = new BufferedReader(new InputStreamReader(feedc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                rss = rss + inputLine;
            in.close();
        } catch (IOException e) {
            // something went wrong, feed not available
            result.setFetchedTime(1);
        }

        result.setFetchedTime(System.currentTimeMillis());
        result.setContent(rss);



        return result;

    }

    /**
     *
     * @param url
     * @return
     */
    public RssFeed getRssFeed(String url) {

        RssFeed result = null;

        if (store.containsKey(url)) {
            result = (RssFeed)store.get(url);
            if (result.getFetchedTime()+expire<System.currentTimeMillis()) {
                result = fetchFeed(url);
                store.put(url, result);
            }
        } else {
            result = fetchFeed(url);
            store.put(url, result);
        }
        return result;
    }

}
