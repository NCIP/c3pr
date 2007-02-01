/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: BannerPortlet.java,v 1.1.1.1 2007-02-01 20:07:19 kherm Exp $
 */
package org.gridlab.gridsphere.extras.portlets.banner;

import org.gridlab.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridlab.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridlab.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridlab.gridsphere.provider.portletui.beans.FrameBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextAreaBean;
import org.gridlab.gridsphere.services.core.secdir.SecureDirectoryService;
import org.gridlab.gridsphere.services.core.secdir.FileInfo;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.extras.portlets.commander.UserData;
import org.apache.oro.text.perl.Perl5Util;
import org.exolab.castor.xml.Unmarshaller;

import javax.portlet.*;
import java.util.Map;
import java.util.HashMap;
import java.io.*;

public class BannerPortlet extends ActionPortlet {

    private SecureDirectoryService secureDirectoryService = null;

    private static final String CONFIGURE_JSP = "banner/configure.jsp";
    private static final String HELP_JSP = "banner/help.jsp";
    private static final String EDIT_JSP = "banner/edit.jsp";
    private static final String VIEW_JSP = "banner/view.jsp";

    private static final String rootDir = "commander";
    private static final int BUFFER_SIZE = 4 * 1024;
    private static Map userDatas = java.util.Collections.synchronizedMap(new HashMap());
    private static Map bannerSettingsMap = java.util.Collections.synchronizedMap(new HashMap());
    private static final String TITLE = "title";
    private static final String FILE = "file";
    private static final String BANNERAPP = "banner";
    private static final String BANNERCONFIGFILE = "bannerSettings.xml";

    private Perl5Util util = new Perl5Util();

    public void init(javax.portlet.PortletConfig config) throws PortletException {
        super.init(config);
        try {
            secureDirectoryService = (SecureDirectoryService) createPortletService(SecureDirectoryService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize FileManagerService", e);
        }
        DEFAULT_VIEW_PAGE = "doViewFile";
        DEFAULT_EDIT_PAGE = "doEditViewFile";
        DEFAULT_CONFIGURE_PAGE = "doConfigureViewFile";

        DEFAULT_HELP_PAGE = HELP_JSP;
    }

    public String getTitle() {
        String defaultTitle = (String) this.getPortletContext().getAttribute(TITLE);
        if (defaultTitle == null) defaultTitle = "";
        return defaultTitle;
    }

    public String getFileURL() {
        String defaultFileURL = (String) this.getPortletContext().getAttribute(FILE);
        if (defaultFileURL == null) defaultFileURL = "";
        return defaultFileURL;
    }

    /**
     * Configure mode allows the displayed file to be set in PortletSettings
     *
     * @param event
     * @throws PortletException
     */
    public void doConfigureViewFile(RenderFormEvent event) throws PortletException {
//        checkAdminRole(event);
        log.error("\n\n\nIN CONFIG MODE\n\n\n");
        TextFieldBean displayTitle = event.getTextFieldBean("displayTitle");
        displayTitle.setValue(getTitle());

        TextFieldBean displayFile = event.getTextFieldBean("displayFile");
        displayFile.setValue(getFileURL());
        log.error("\n\n\nTITLE:"+getTitle()+"\n\n\n");
        log.error("\n\n\nURL:"+getFileURL()+"\n\n\n");
        log.error("\n\n\nIN CONFIG MODE 2\n\n\n");
        setNextState(event.getRenderRequest(), CONFIGURE_JSP);
//        setNextState(event.getRenderRequest(), VIEW_JSP);
    }

    public void setConfigureDisplayFile(ActionFormEvent event) throws PortletException {
        log.debug("in BannerPortlet: setConfigureDisplayFile");
//        checkAdminRole(event);
        TextFieldBean displayFile = event.getTextFieldBean("displayFile");
        String defaultFileURL = displayFile.getValue();
        getPortletContext().setAttribute(FILE, defaultFileURL);

        TextFieldBean displayTitle = event.getTextFieldBean("displayTitle");
        String defaultTitle = displayTitle.getValue();

        getPortletContext().setAttribute(TITLE, defaultTitle);
/*
        FrameBean alert = event.getFrameBean("alert");
        try {
            getPortletSettings().store();
            alert.setValue(this.getLocalizedText(req, "BANNER_CONFIGURE"));
        } catch (IOException e) {
            log.error("Unable to save portlet settings", e);
            alert.setValue(this.getLocalizedText(req, "BANNER_FAILURE"));
            alert.setStyle("error");
        }
*/
        setNextState(event.getActionRequest(), CONFIGURE_JSP);
    }

    public void setEditDisplayFile(ActionFormEvent event) throws PortletException {
        log.debug("in BannerPortlet: setEditDisplayFile");
//        checkUserRole(event);
        FrameBean alert = event.getFrameBean("alert");
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");

        String fileNumberParam = event.getActionRequest().getParameter("fileNumber");

        UserData userData = (UserData) userDatas.get(userID);
        BannerSettings bannerSettings = (BannerSettings) bannerSettingsMap.get(userID);
        String fileURL = null;

        try {
            int fileNumber = Integer.parseInt(fileNumberParam);
            FileInfo[] resources = userData.getLeftFileList();
            fileURL = userData.getPath("left") + resources[fileNumber].getResource();
        } catch (Exception e) {
        }

        if (fileURL == null) {
            log.error("did not select a file");
            alert.setValue(this.getLocalizedText(event.getActionRequest(), "BANNER_NOFILE_SELECTED"));
            alert.setStyle("error");
        } else
            bannerSettings.setBannerFile(fileURL);

        TextFieldBean displayTitle = event.getTextFieldBean("displayTitle");
        String title = displayTitle.getValue();
        if (title != null && !title.equals("")) {
            bannerSettings.setBannerTitle(title);
        }
        try {
            StringWriter stringWriter = new StringWriter();
            bannerSettings.marshal(stringWriter);
            ByteArrayInputStream bais = new ByteArrayInputStream(stringWriter.toString().getBytes());
            secureDirectoryService.addFile(secureDirectoryService.createFileLocationID(userID, BANNERAPP, BANNERCONFIGFILE), bais);
            alert.setValue(this.getLocalizedText(event.getActionRequest(), "BANNER_CONFIGURE"));
        } catch (Exception e) {
            log.error("Unable to save portlet data");
            alert.setValue(this.getLocalizedText(event.getActionRequest(), "BANNER_FAILURE"));
            alert.setStyle("error");
        }

        setNextState(event.getActionRequest(), "doEditViewFile");
    }

    /**
     * Edit mode allows the displayed file to be set in PortletData
     *
     * @param event
     * @throws PortletException
     */
    public void doEditViewFile(RenderFormEvent event) throws PortletException {
//        checkUserRole(event);
        FrameBean alert = event.getFrameBean("errorFrame");
        String userID = (String) ((Map) event.getRenderRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        UserData userData = (UserData) userDatas.get(userID);
        BannerSettings bannerSettings = (BannerSettings) bannerSettingsMap.get(userID);

        if (userData == null) {
            userData = new UserData();
            userDatas.put(userID, userData);
        }
        if (bannerSettings == null) {
            try {
                if (secureDirectoryService.fileExists(secureDirectoryService.createFileLocationID(userID, BANNERAPP, BANNERCONFIGFILE))) {
                    File settingsFile = secureDirectoryService.getFile(secureDirectoryService.createFileLocationID(userID, BANNERAPP, BANNERCONFIGFILE));
                    FileReader fileReader = new FileReader(settingsFile);
                    bannerSettings = (BannerSettings) Unmarshaller.unmarshal(BannerSettings.class, fileReader);
                } else
                    throw new Exception();
            } catch (Exception e) {
                bannerSettings = new BannerSettings();
                bannerSettings.setBannerFile(getFileURL());
                bannerSettings.setBannerTitle(getTitle());
            }
            bannerSettingsMap.put(userID, bannerSettings);
        }

        TextBean fileName = event.getTextBean("fileName");
        fileName.setValue(bannerSettings.getBannerFile());
        TextBean title = event.getTextBean("title");
        title.setValue(bannerSettings.getBannerTitle());

        readDirectories(event.getRenderRequest(), userData);
        event.getRenderRequest().setAttribute("userData", userData);

        if (!userData.getCorrect().booleanValue()) {
            alert.setKey("COMMANDER_ERROR_INIT");
            alert.setStyle(FrameBean.ERROR_TYPE);
        }

        setNextState(event.getRenderRequest(), EDIT_JSP);
    }

    public void doViewFile(RenderFormEvent event) throws PortletException {
        log.debug("in BannerPortlet: doViewFile");
        RenderResponse response = event.getRenderResponse();
        String userID = (String) ((Map) event.getRenderRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        BannerSettings bannerSettings = (BannerSettings) bannerSettingsMap.get(userID);
//        String title = getTitle();
        String fileURL = null;
        boolean userFile = false;
        if (bannerSettings != null) {

            fileURL = bannerSettings.getBannerFile();
            userFile = true;
            // if user hasn't configured banner, show them help
            /*
            if (fileURL == null) {
                setNextState(request, HELP_JSP);
                return;
            }
            */
        }

        if (fileURL == null) {
            fileURL = getFileURL();
            userFile = false;
        }

        //setNextState(request, fileURL);
        //TextAreaBean fileTA = event.getTextAreaBean("fileTA");


        try {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            if (fileURL.equals("")) {
                out.println((this.getLocalizedText(event.getRenderRequest(), "BANNER_FILE_NOTFOUND")));
                //fileTA.setValue((this.getLocalizedText(event.getRenderRequest(), "BANNER_FILE_NOTFOUND")));
            } else {
                File file = null;
                if (userFile) {
                    file = secureDirectoryService.getFile(secureDirectoryService.createFileLocationID(userID, rootDir, fileURL));
                    if (file == null)
                        throw new IOException("Unable to get " + fileURL + " form secure directory service.");
                    //FileReader fileReader = new FileReader(file);
                    //rewrite(fileReader, out);

                } else {
                    file = new File(getPortletContext().getRealPath(fileURL));
                    if (file == null)
                        throw new IOException("Unable to get " + fileURL + " form secure directory service.");
                    //FileReader fileReader = new FileReader(file);
                    //rewrite(fileReader, out);

                }
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = null;
                //StringBuffer sb = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    out.println(line);
                    //sb.append("\n");
                }
                reader.close();
                //fileTA.setValue(sb.toString());
            }
        } catch (IOException e) {
            log.error("Unable to find file: " + fileURL);
        }
        //setNextState(event.getRenderRequest(), VIEW_JSP);
    }

    public void cancelEditFile(ActionFormEvent event) throws PortletException {
        log.debug("in BannerPortlet: cancelEdit");
        event.getActionResponse().setPortletMode(PortletMode.VIEW);
        setNextState(event.getActionRequest(), DEFAULT_VIEW_PAGE);
    }

    protected String getTitle(RenderRequest renderRequest) {
        String userID = (String) ((Map) renderRequest.getAttribute(PortletRequest.USER_INFO)).get("user.id");
        if (userID != null && !userID.equals("")) {
            BannerSettings bannerSettings = (BannerSettings) bannerSettingsMap.get(userID);
            if (bannerSettings != null)
                return bannerSettings.getBannerTitle();
        }
        return getTitle();
    }

/*
    public void doTitle(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        PrintWriter out = response.getWriter();
        if (request.getMode() == Portlet.Mode.VIEW) {
            User user = request.getUser();
            String title = null;
            if (!(user instanceof GuestUser)) {
                PortletData data = request.getData();
                title = data.getAttribute(TITLE);
                if (title == null) title = this.getLocalizedText(request, "BANNER_HELP");
            } else {
                title = getTitle();
            }
            out.println(title);
        } else if (request.getMode() == Portlet.Mode.HELP) {
            out.println(this.getLocalizedText(request, "BANNER_HELP"));
        } else {
            out.println(this.getLocalizedText(request, "BANNER_EDIT"));
        }
    }
*/


    public void changeDir(ActionFormEvent event) throws PortletException {
        String newDirParam = event.getAction().getParameter("newDir");
        String sideParam = event.getAction().getParameter("side");

        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        org.gridlab.gridsphere.extras.portlets.commander.UserData userData = (org.gridlab.gridsphere.extras.portlets.commander.UserData) userDatas.get(userID);
        String newDir = userData.getPath(sideParam);
        if (newDirParam.equals("..")) {
            newDir = util.substitute("s!/[^/]+/$!/!", newDir);
        } else {
            newDir += newDirParam + "/";
        }
        userData.setPath(sideParam, newDir);
        readDirectories(event.getActionRequest(), userData);
    }

    public void gotoRootDirLeft(ActionFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        org.gridlab.gridsphere.extras.portlets.commander.UserData userData = (org.gridlab.gridsphere.extras.portlets.commander.UserData) userDatas.get(userID);

        userData.setPath("left", "/");
        readDirectories(event.getActionRequest(), userData);
    }

    private void readDirectories(PortletRequest req, UserData userData) {
        String userID = (String) ((Map) req.getAttribute(PortletRequest.USER_INFO)).get("user.id");

        try {
            SecureDirectoryService secureDirectoryService = (SecureDirectoryService) createPortletService(SecureDirectoryService.class);
            if (!secureDirectoryService.categoryExistsForUser(userID, rootDir))
                secureDirectoryService.createCategoryForUser(userID, rootDir);
            String leftPath = userData.getPath("left");
            FileInfo[] resourceList = secureDirectoryService.getFileList(secureDirectoryService.createFileLocationID(userID, rootDir, leftPath));

            userData.setLeftFileList(resourceList);
            userData.setCorrect(Boolean.TRUE);
        } catch (PortletServiceException e) {
            e.printStackTrace();
            log.error("Secure service unavailable", e);
            userData.setCorrect(Boolean.FALSE);
        } catch (IOException e) {
            e.printStackTrace();
            userData.setCorrect(Boolean.FALSE);
        }
    }

    private void rewrite(InputStreamReader input, OutputStream output) throws IOException {
        char[] buf = new char[BUFFER_SIZE];
        while (!(input.read(buf) < 0)) {
            output.write(new String(buf).getBytes());
        }
    }

}
