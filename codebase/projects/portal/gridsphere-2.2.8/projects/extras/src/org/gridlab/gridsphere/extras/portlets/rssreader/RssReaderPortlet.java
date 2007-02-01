    package org.gridlab.gridsphere.extras.portlets.rssreader;

import org.gridlab.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridlab.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridlab.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.extras.services.rssreader.RssReaderService;
import org.gridlab.gridsphere.extras.services.rssreader.RssFeed;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;

import javax.portlet.*;
import java.util.Enumeration;
import java.util.Date;
import java.io.IOException;

import churchillobjects.rss4j.RssDocument;
import churchillobjects.rss4j.RssChannel;
import churchillobjects.rss4j.RssChannelItem;
import churchillobjects.rss4j.parser.RssParser;
import churchillobjects.rss4j.parser.RssParseException;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: RssReaderPortlet.java,v 1.1.1.1 2007-02-01 20:07:48 kherm Exp $
 */

public class RssReaderPortlet extends ActionPortlet {

    private RssReaderService rssReaderService = null;

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        DEFAULT_VIEW_PAGE = "prepareView";
        DEFAULT_EDIT_PAGE = "prepareEdit";
        DEFAULT_HELP_PAGE = "rss/help.jsp";

        try {
            rssReaderService = (RssReaderService) createPortletService(RssReaderService.class);
        } catch (PortletServiceException e) {
            e.printStackTrace();
        }
    }

    public void prepareEdit(RenderFormEvent event) throws PortletException {
        ListBoxBean list = event.getListBoxBean("allChannels");
        list.clear();
        PortletPreferences prefs = event.getRenderRequest().getPreferences();
        list = makeFeedListBox(prefs, list, true, null);
        list.setSize(10);
        list.setMultipleSelection(false);
        setNextState(event.getRenderRequest(), "rssreader/edit.jsp");
    }

    private ListBoxBean makeFeedListBox(PortletPreferences prefs, ListBoxBean list, boolean longDisplay, String selected) {
        Enumeration enu = prefs.getNames();

        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            if (prefs.getValue(key, "").startsWith("http://")) {
                ListBoxItemBean item = new ListBoxItemBean();
                item.setName(key);
                if (!longDisplay) item.setValue(key); else item.setValue(key + " (" + prefs.getValue(key, "") + ")");

                if (selected!=null) {
                    if (key.equals(selected)) item.setSelected(true);
                }
                list.addBean(item);
            }
        }
        return list;
    }

    private boolean hasFeedsSaved(PortletPreferences prefs) {
        Enumeration enu = prefs.getNames();

        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            if (prefs.getValue(key, "").startsWith("http://")) {
                return true;
            }
        }
        return false;
    }

    public void addFeed(ActionFormEvent event) throws PortletException {
        TextFieldBean name = event.getTextFieldBean("name");
        TextFieldBean url = event.getTextFieldBean("url");
        PortletPreferences prefs = event.getActionRequest().getPreferences();
        prefs.setValue(name.getValue(), url.getValue());
        try {
            prefs.store();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setNextState(event.getActionRequest(), "prepareEdit");
    }

    public void deleteFeed(ActionFormEvent event) throws PortletException {
        ListBoxBean list = event.getListBoxBean("allChannels");
        String url = list.getSelectedName();
        if (url!=null) {
            PortletPreferences prefs = event.getActionRequest().getPreferences();

            prefs.reset(url);
            try {
                prefs.store();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setNextState(event.getActionRequest(), "prepareEdit");
    }


    private String parseURL(String url, MessageBoxBean messageBox) {


        String result = "";
        if (url!=null) {
            RssFeed rssfeed = rssReaderService.getRssFeed(url);

            RssDocument doc = null;
            try {
                doc = RssParser.parseRss(rssfeed.getContent());
                result = doc.getVersion();

            } catch (RssParseException e) {

                // maybe not supported version try other parser (for 2.0/atom)
                result = "";
                messageBox.appendText("This feed '"+url+"' is in a not supported version or not valid.");
                messageBox.setMessageType(MessageStyle.MSG_ALERT);
            }


            if (doc!=null) {
                Enumeration enu = doc.channels();
                while (enu.hasMoreElements()) {
                    RssChannel channel = (RssChannel) enu.nextElement();
                    result += ("<h2>" + channel.getChannelTitle() + "</h2>");
                    Date date = new Date(rssfeed.getFetchedTime());
                    result += "last updated: " + date.toString() + "<br/>";
                    Enumeration items = channel.items();
                    while (items.hasMoreElements()) {
                        RssChannelItem item = (RssChannelItem) items.nextElement();
                        result += ("<a target=\"_blank\" href=\"" + item.getItemLink() + "\">" + item.getItemTitle() + "</a>");
                        if (item.getItemDescription() != null) result += "<br/>" + item.getItemDescription();
                        result += "</p>";
                    }
                    messageBox.clearMessage();
                }
            }
        }
        return result;
    }

    public void showFeed(ActionFormEvent event) throws PortletException {
        ListBoxBean allChannels = event.getListBoxBean("allChannels");
        String selectedName = allChannels.getSelectedName();
        String selectedValue = allChannels.getSelectedValue();
        MessageBoxBean messageBox = event.getMessageBoxBean("messagebox");

        String result = "";
        String url;

        PortletPreferences pref = event.getActionRequest().getPreferences();

        allChannels.clear();
        allChannels = makeFeedListBox(pref, allChannels, false, selectedName);

        if (selectedValue != null) {
            url = pref.getValue(selectedValue, "");
            result = parseURL(url, messageBox);

            pref.setValue("lastfeed", selectedName);
            try {
                pref.store();
            } catch (IOException e) {
                log.error("Could not save Preferences (storing defaulturl).");
            }
        } else {
            messageBox.setValue("This feed is not available!");
            messageBox.setMessageType(MessageStyle.MSG_ALERT);
        }

        TextBean feed = event.getTextBean("feed");
        feed.setValue(result);
        setNextState(event.getActionRequest(), "rssreader/view.jsp");
    }

    public void prepareView(RenderFormEvent event) throws PortletException {

        RenderRequest request = event.getRenderRequest();
        PortletPreferences prefs = request.getPreferences();
        ListBoxBean allChannels = event.getListBoxBean("allChannels");
        TextBean feed = event.getTextBean("feed");

        PortletPreferences pref = event.getRenderRequest().getPreferences();

        String key = pref.getValue("lastfeed", null);
        String url = null;

        if (key!=null) {
            url = pref.getValue(key, null);

        }
        allChannels.clear();
        allChannels = makeFeedListBox(prefs, allChannels, false, key);
        MessageBoxBean messageBox = event.getMessageBoxBean("messageBox");

        feed.setValue(this.parseURL(url, messageBox));

        if (!this.hasFeedsSaved(pref)) {
            messageBox.setValue("You have no RSS feeds saved. Please define at least one.");
            messageBox.setMessageType(MessageStyle.MSG_INFO);
        }
        setNextState(request, "rssreader/view.jsp");
    }

}
