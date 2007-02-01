package org.gridlab.gridsphere.extras.services.rssreader;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: RssFeed.java,v 1.1.1.1 2007-02-01 20:08:19 kherm Exp $
 */

public class RssFeed {

    private String url = "";
    private long fetchedTime = 1;
    private String content = "";
    private String version = "";

    
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the time when feed was fetched in milisec.
     * @return
     */
    public long getFetchedTime() {
        return fetchedTime;
    }

    /**
     * Sets the time when it was fetched in milisec
     * @param fetchedTime
     */
    public void setFetchedTime(long fetchedTime) {
        this.fetchedTime = fetchedTime;
    }

    /**
     * Gets the content of the feed
     * @return content of feed
     */
    public String getContent() {
        return content;
    }

    /***
     * Sets the content of the feed.
     * @param content content of the feed
     */
    public void setContent(String content) {
        this.content = content;
    }
}
