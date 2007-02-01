
/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ClippingPortlet.java,v 1.1.1.1 2007-02-01 20:07:29 kherm Exp $
 */
package org.gridlab.gridsphere.extras.portlets.clipping;

import org.gridlab.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridlab.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridlab.gridsphere.services.core.secdir.SecureDirectoryService;
import org.gridlab.gridsphere.services.core.secdir.FileLocationID;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.UnavailableException;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class ClippingPortlet extends ActionPortlet {

    protected String page = "";
    protected String title = "";

    private static final String CLIPPING = "clipping";
    private SecureDirectoryService secDirectory;

    public void init(PortletConfig config) throws PortletException, UnavailableException {
        super.init(config);
        try {
            secDirectory = (SecureDirectoryService)createPortletService(SecureDirectoryService.class);
        } catch (PortletServiceException e) {
            page = "Unable to get instance of SecureDirectoryService!";
        }
        String webpage = config.getInitParameter("webpage");

        GetMethod get = new GetMethod(webpage);
        HttpClient client = new HttpClient();

        try {
            // first HTTP GET retrieves the entire web page
            int status = client.executeMethod( get );
            page = get.getResponseBodyAsString();
            get.releaseConnection();

            // while loop goes thru and retrieves any referenced images (img src href's)
            boolean done = false;
            int imgIdx = 0;
            int endIdx;
            String imglink = "";
            String newlink = "";
            String file = "";
            //System.err.println(page);
            while (!done) {
                System.err.println(imgIdx);
                imgIdx = page.indexOf("<img src=\"", imgIdx);
                endIdx = page.indexOf("\"", imgIdx+"<img src=\"".length());
                if (imgIdx < 0) {
                    done = true;
                } else {
                    String href =  page.substring(imgIdx+"<img src=\"".length(), endIdx);
                    if (href.startsWith("http://")) {
                        imglink = href;
                    } else if (href.startsWith("/")) {
                        imglink = webpage + href;
                    } else {
                        imglink = webpage + "/" + href;
                    }

                    endIdx = imglink.lastIndexOf("/");
                    if (endIdx < 0) {
                        file = imglink;
                    } else {
                        file = imglink.substring(endIdx+1);
                    }

                    //System.err.println("href= " + href);
                    //System.err.println("imglink= " + imglink);
                    //System.err.println("file= " + file);
                    get = new GetMethod(imglink);

                    status = client.executeMethod(get);

                    FileLocationID locId = secDirectory.createFileLocationID(CLIPPING, file);

                    secDirectory.addFile(locId,  get.getResponseBodyAsStream());

                    newlink = secDirectory.getFileUrl(locId);

                    get.releaseConnection();

                    //System.err.println("newlink= " + newlink);

                    // rewrite the webpage to reference images stored in secure directory
                    page = page.replaceAll(href, newlink);
                    imgIdx++;
                }
            }

            int startIdx = 0;
            endIdx = 0;
            done = false;
            StringBuffer tmp = new StringBuffer();
            boolean notarget = false;
            while (!done) {
                System.err.println(startIdx);
                endIdx = page.indexOf("<a href=\"", startIdx);

                if (endIdx > 0) {
                    tmp.append(page.substring(startIdx, endIdx));
                } else {
                    tmp.append(page.substring(startIdx));
                }

                if (endIdx < 0) {
                    done = true;
                } else {

                    startIdx = endIdx;
                    endIdx = page.indexOf("\"", endIdx+"<a href=\"".length());
                    String href =  page.substring(startIdx+"<a href=\"".length(), endIdx);

                    int p = href.indexOf(":");
                    if (p > 0) {
                        if (href.startsWith("http://")) {
                            imglink = href;
                        } else {
                            imglink = href;
                            notarget = true;
                        }
                    } else {
                        if (href.startsWith("/")) {
                            imglink = webpage + href;
                        } else {
                            imglink = webpage + "/" + href;
                        }
                    }


                    //System.err.println("href= " + href);
                    //System.err.println("imglink= " + imglink);

                    if (notarget) {
                        newlink = "<a href=\"" + imglink;
                        notarget = false;
                    }  else {
                        newlink = "<a href=\"" + imglink + "\" target=\"_blank\"";
                    }


                    //System.err.println("newlink= " + newlink);

                    // rewrite the webpage to reference images stored in secure directory

                    tmp.append(newlink);

                   // System.err.println(tmp.toString());
                    startIdx = endIdx;

                }
            }


            page = tmp.toString();


            int begin = page.indexOf("<title>");
            int end = page.indexOf("</title>");

            title = page.substring(begin + "<title>".length(), end);

            begin = page.indexOf("<body>");
            end = page.indexOf("</body>");

            page = page.substring(begin+"<body>".length(), end);

            //System.err.println(page);

        } catch (Exception e) {
            e.printStackTrace();
            page = "Unable to retrieve link: " + webpage;
        }

        DEFAULT_VIEW_PAGE = "displayPage";
    }


    public void displayPage(RenderFormEvent event) throws IOException, PortletException {

        RenderResponse res = event.getRenderResponse();
        res.setContentType("text/html");
        res.setTitle(title);

        PrintWriter out = res.getWriter();

        out.println(page);


    }


}