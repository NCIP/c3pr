/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version 1.01 2004/08/16
 */

package org.gridlab.gridsphere.extras.portlets.commander;

import org.gridlab.gridsphere.services.core.secdir.SecureDirectoryService;
import org.gridlab.gridsphere.services.core.secdir.FileInfo;
import org.gridlab.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridlab.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridlab.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridlab.gridsphere.provider.portletui.beans.FrameBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridlab.gridsphere.provider.portletui.beans.FileInputBean;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.apache.oro.text.perl.Perl5Util;

import javax.portlet.*;

import java.io.*;
import java.util.*;

public class CommanderPortlet extends ActionPortlet {
    public static final String VIEW_JSP = "commander/explorer.jsp";
    public static final String EDIT_JSP = "commander/editfile.jsp";
    public static final String HELP_JSP = "commander/help.jsp";
    public static final String rootDir = "commander";

    private Perl5Util util = new Perl5Util();
    private static Map userDatas = java.util.Collections.synchronizedMap(new HashMap());

    public void init(javax.portlet.PortletConfig portletConfig) throws javax.portlet.PortletException {
        super.init(portletConfig);
        DEFAULT_VIEW_PAGE = "showExplorer";
        DEFAULT_HELP_PAGE = HELP_JSP;
    }

    public void showExplorer(RenderFormEvent event) throws PortletException {
        FrameBean frame = event.getFrameBean("errorFrame");
        String userID = (String) ((Map) event.getRenderRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        org.gridlab.gridsphere.extras.portlets.commander.UserData userData = (org.gridlab.gridsphere.extras.portlets.commander.UserData) userDatas.get(userID);

        if (userData == null) {
            userData = new org.gridlab.gridsphere.extras.portlets.commander.UserData();
            readDirectories(event, userData);
            userDatas.put(userID, userData);
        }
        event.getRenderRequest().setAttribute("userData", userData);

        if (userData.getState().equals("explore")) {
            if (!userData.getCorrect().booleanValue()) {
                frame.setKey("COMMANDER_ERROR_INIT");
                frame.setStyle(FrameBean.ERROR_TYPE);
            }
            setNextState(event.getRenderRequest(), VIEW_JSP);
        } else {
            setNextState(event.getRenderRequest(), EDIT_JSP);
        }
    }

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
        readDirectories(event, userData);
    }

    public void uploadFileLeft(ActionFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        org.gridlab.gridsphere.extras.portlets.commander.UserData userData = (org.gridlab.gridsphere.extras.portlets.commander.UserData) userDatas.get(userID);

        try {
            FileInputBean fi = event.getFileInputBean("userfileLeft");
            String filename = fi.getFileName();

            if (fi.getSize() < FileInputBean.MAX_UPLOAD_SIZE) {
                SecureDirectoryService secureDirectoryService = (SecureDirectoryService) createPortletService(SecureDirectoryService.class);
                filename = util.substitute("s!\\\\!/!g", filename);
                if (util.match("m!/([^/]+)$!", filename)) {
                    filename = util.group(1);
                }

                filename = util.substitute("s! !!g", filename);

                String path = userData.getPath("left");
                secureDirectoryService.addFile(secureDirectoryService.createFileLocationID(userID, rootDir, path + filename), fi.getInputStream());
                readDirectories(event, userData);
            } else
                log.error("Size of uploaded file is to big");
        } catch (PortletServiceException e) {
            log.error("Secure service not found", e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void uploadFileRight(ActionFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        org.gridlab.gridsphere.extras.portlets.commander.UserData userData = (org.gridlab.gridsphere.extras.portlets.commander.UserData) userDatas.get(userID);

        try {
            FileInputBean fi = event.getFileInputBean("userfileRight");
            String filename = fi.getFileName();

            if (fi.getSize() < FileInputBean.MAX_UPLOAD_SIZE) {
                SecureDirectoryService secureDirectoryService = (SecureDirectoryService) createPortletService(SecureDirectoryService.class);
                filename = util.substitute("s!\\\\!/!g", filename);
                if (util.match("m!/([^/]+)$!", filename)) {
                    filename = util.group(1);
                }

                filename = util.substitute("s! !!g", filename);

                String path = userData.getPath("right");
                secureDirectoryService.addFile(secureDirectoryService.createFileLocationID(userID, rootDir, path + filename), fi.getInputStream());
                readDirectories(event, userData);
            } else
                log.error("Size of uploaded file is to big");
        } catch (PortletServiceException e) {
            log.error("Secure service not found", e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void newDirectory(ActionFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        String sideParam = event.getActionRequest().getParameter("side");
        TextFieldBean textFieldBean = event.getTextFieldBean("resourceName" + sideParam);
        String resourceName = textFieldBean.getValue();
        org.gridlab.gridsphere.extras.portlets.commander.UserData userData = (org.gridlab.gridsphere.extras.portlets.commander.UserData) userDatas.get(userID);

        if (resourceName != null && !resourceName.equals("")) {
            try {
                SecureDirectoryService secureDirectoryService = (SecureDirectoryService) createPortletService(SecureDirectoryService.class);
                String path = userData.getPath(sideParam);
                resourceName = util.substitute("s! !!g", resourceName);

                File newDirectory = secureDirectoryService.getFile(secureDirectoryService.createFileLocationID(userID, rootDir, path + resourceName));
                newDirectory.mkdir();
                textFieldBean.setValue("");
                readDirectories(event, userData);
            } catch (PortletServiceException e) {
                log.error("Secure service not found", e);
            }
        }
    }

    public void newFile(ActionFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        String sideParam = event.getActionRequest().getParameter("side");
        TextFieldBean textFieldBean = event.getTextFieldBean("resourceName" + sideParam);
        String resourceName = textFieldBean.getValue();

        log.error("\n\n\nKURWA MAC:\n"+sideParam+":\n"+resourceName+":\n\n\n");

        org.gridlab.gridsphere.extras.portlets.commander.UserData userData = (org.gridlab.gridsphere.extras.portlets.commander.UserData) userDatas.get(userID);

        if (resourceName != null && !resourceName.equals("")) {
            try {
                SecureDirectoryService secureDirectoryService = (SecureDirectoryService) createPortletService(SecureDirectoryService.class);
                String path = userData.getPath(sideParam);
                resourceName = util.substitute("s! !!g", resourceName);

                File newFile = secureDirectoryService.getFile(secureDirectoryService.createFileLocationID(userID, rootDir, path + resourceName));
                newFile.createNewFile();
                textFieldBean.setValue("");
                readDirectories(event, userData);
            } catch (PortletServiceException e) {
                log.error("Secure service not found", e);
            } catch (IOException e) {
                log.error("Unable to create new file", e);
            }
        }
    }

    public void gotoRootDirLeft(ActionFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        org.gridlab.gridsphere.extras.portlets.commander.UserData userData = (org.gridlab.gridsphere.extras.portlets.commander.UserData) userDatas.get(userID);

        userData.setPath("left", "/");
        readDirectories(event, userData);
    }

    public void gotoRootDirRight(ActionFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        org.gridlab.gridsphere.extras.portlets.commander.UserData userData = (org.gridlab.gridsphere.extras.portlets.commander.UserData) userDatas.get(userID);

        userData.setPath("right", "/");
        readDirectories(event, userData);
    }

    public void copy(ActionFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        String sideParam = event.getActionRequest().getParameter("side");
        org.gridlab.gridsphere.extras.portlets.commander.UserData userData = (org.gridlab.gridsphere.extras.portlets.commander.UserData) userDatas.get(userID);

        String sourcePath = userData.getPath(sideParam);
        String destinationPath = (sideParam.equals("left") ?
                userData.getPath("right") :
                userData.getPath("left"));

        FileInfo[] resources = null;
        resources = (sideParam.equals("left") ?
                userData.getLeftFileList() :
                userData.getRightFileList());
        try {
            SecureDirectoryService secureDirectoryService = (SecureDirectoryService) createPortletService(SecureDirectoryService.class);
            Enumeration params = event.getActionRequest().getParameterNames();

            while (params.hasMoreElements()) {
                String param = (String) params.nextElement();
                if (util.match("m!^" + sideParam + "_(\\d)+$!", param)) {
                    secureDirectoryService.copyFile(secureDirectoryService.createFileLocationID(userID, rootDir, sourcePath + resources[Integer.parseInt(util.group(1))].getResource()), destinationPath + resources[Integer.parseInt(util.group(1))].getResource());
                }
            }
            readDirectories(event, userData);
        } catch (PortletServiceException e) {
            log.error("Secure service not found", e);
        }
    }

    public void move(ActionFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        String sideParam = event.getActionRequest().getParameter("side");
        org.gridlab.gridsphere.extras.portlets.commander.UserData userData = (org.gridlab.gridsphere.extras.portlets.commander.UserData) userDatas.get(userID);

        String sourcePath = userData.getPath(sideParam);
        String destinationPath = (sideParam.equals("left") ?
                userData.getPath("right") :
                userData.getPath("left"));

        FileInfo[] resources = null;
        resources = (sideParam.equals("left") ?
                userData.getLeftFileList() :
                userData.getRightFileList());
        try {
            SecureDirectoryService secureDirectoryService = (SecureDirectoryService) createPortletService(SecureDirectoryService.class);
            Enumeration params = event.getActionRequest().getParameterNames();

            while (params.hasMoreElements()) {
                String param = (String) params.nextElement();
                if (util.match("m!^" + sideParam + "_(\\d)+$!", param)) {
                    secureDirectoryService.moveFile(secureDirectoryService.createFileLocationID(userID, rootDir, sourcePath + resources[Integer.parseInt(util.group(1))].getResource()), destinationPath + resources[Integer.parseInt(util.group(1))].getResource());
                }
            }
            readDirectories(event, userData);
        } catch (PortletServiceException e) {
            log.error("Secure service not found", e);
        }
    }

    public void delete(ActionFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        String sideParam = event.getActionRequest().getParameter("side");
        org.gridlab.gridsphere.extras.portlets.commander.UserData userData = (org.gridlab.gridsphere.extras.portlets.commander.UserData) userDatas.get(userID);

        String path = userData.getPath(sideParam);

        FileInfo[] resources = null;
        resources = (sideParam.equals("left") ?
                userData.getLeftFileList() :
                userData.getRightFileList());
        try {
            SecureDirectoryService secureDirectoryService = (SecureDirectoryService) createPortletService(SecureDirectoryService.class);
            Enumeration params = event.getActionRequest().getParameterNames();

            while (params.hasMoreElements()) {
                String param = (String) params.nextElement();
                if (util.match("m!^" + sideParam + "_(\\d)+$!", param)) {
                    secureDirectoryService.deleteFile(secureDirectoryService.createFileLocationID(userID, rootDir, path + resources[Integer.parseInt(util.group(1))].getResource()), true);
                }
            }
            readDirectories(event, userData);
        } catch (PortletServiceException e) {
            log.error("Secure service not found", e);
        }
    }

    public void editFile(ActionFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        String sideParam = event.getAction().getParameter("side");
        String fileNumberParam = event.getAction().getParameter("fileNumber");
        org.gridlab.gridsphere.extras.portlets.commander.UserData userData = (org.gridlab.gridsphere.extras.portlets.commander.UserData) userDatas.get(userID);

        try {
            SecureDirectoryService secureDirectoryService = (SecureDirectoryService) createPortletService(SecureDirectoryService.class);
            int fileNumber = Integer.parseInt(fileNumberParam);
            FileInfo[] resources = null;
            resources = (sideParam.equals("left") ?
                    userData.getLeftFileList() :
                    userData.getRightFileList());

            File file = secureDirectoryService.getFile(secureDirectoryService.createFileLocationID(userID, rootDir, userData.getPath(sideParam) + resources[fileNumber].getResource()));
            userData.setEditSide(sideParam.equals("left") ? "left" : "right");
            userData.setEditFile(file);
            userData.setState("edit");
        } catch (NumberFormatException e) {
            log.error("Unable to parse fileNumberParam (" + fileNumberParam + ")", e);
        } catch (PortletServiceException e) {
            log.error("Secure service not found", e);
        }
    }

    public void save(ActionFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        String fileData = event.getActionRequest().getParameter("fileData");
        org.gridlab.gridsphere.extras.portlets.commander.UserData userData = (org.gridlab.gridsphere.extras.portlets.commander.UserData) userDatas.get(userID);

        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(fileData.getBytes());
            SecureDirectoryService secureDirectoryService = (SecureDirectoryService) createPortletService(SecureDirectoryService.class);
            secureDirectoryService.addFile(secureDirectoryService.createFileLocationID(userID, rootDir, userData.getPath(userData.getEditSide()) + userData.getEditFile().getName()), bais);
            readDirectories(event, userData);
            userData.setState("explore");
        } catch (PortletServiceException e) {
            log.error("Secure service not found", e);
        } catch (IOException e) {
            log.error("Unable to save file.", e);
        }
    }

    public void cancel(ActionFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        org.gridlab.gridsphere.extras.portlets.commander.UserData userData = (org.gridlab.gridsphere.extras.portlets.commander.UserData) userDatas.get(userID);

        userData.setEditFile(null);
        userData.setState("explore");
    }

    private void readDirectories(ActionFormEvent event, org.gridlab.gridsphere.extras.portlets.commander.UserData userData) {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        try {
            SecureDirectoryService secureDirectoryService = (SecureDirectoryService) createPortletService(SecureDirectoryService.class);
            if (!secureDirectoryService.categoryExistsForUser(userID, rootDir))
                secureDirectoryService.createCategoryForUser(userID, rootDir);

            String leftPath = userData.getPath("left");
            String rightPath = userData.getPath("right");
            FileInfo[] leftFileList = secureDirectoryService.getFileList(secureDirectoryService.createFileLocationID(userID, rootDir, leftPath));
            FileInfo[] rightFileList = secureDirectoryService.getFileList(secureDirectoryService.createFileLocationID(userID, rootDir, rightPath));

            String[] leftURIs = null;
            String[] rightURIs = null;

            if (leftFileList != null) {
                leftURIs = new String[leftFileList.length];
                for (int i = 0; i < leftFileList.length; ++i) {
                    if (!leftFileList[i].isDirectory()) {
                        leftURIs[i] = secureDirectoryService.getDownloadFileUrl(secureDirectoryService.createFileLocationID(userID, rootDir, leftPath + leftFileList[i].getResource()), leftFileList[i].getResource(), null);
                    }
                }
            }
            if (rightFileList != null) {
                rightURIs = new String[rightFileList.length];
                for (int i = 0; i < rightFileList.length; ++i) {
                    if (!rightFileList[i].isDirectory()) {
                        rightURIs[i] = secureDirectoryService.getDownloadFileUrl(secureDirectoryService.createFileLocationID(userID, rootDir, rightPath + rightFileList[i].getResource()), rightFileList[i].getResource(), null);
                    }
                }
            }
            userData.setLeftFileList(leftFileList);
            userData.setRightFileList(rightFileList);
            userData.setLeftURIs(leftURIs);
            userData.setRightURIs(rightURIs);
            userData.setCorrect(Boolean.TRUE);
        } catch (PortletServiceException e) {
            log.error("Secure service unavailable", e);
            userData.setCorrect(Boolean.FALSE);
        } catch (IOException e) {
            userData.setCorrect(Boolean.FALSE);
        }
    }

    private void readDirectories(RenderFormEvent event, org.gridlab.gridsphere.extras.portlets.commander.UserData userData) {
        String userID = (String) ((Map) event.getRenderRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        try {
            SecureDirectoryService secureDirectoryService = (SecureDirectoryService) createPortletService(SecureDirectoryService.class);
            if (!secureDirectoryService.categoryExistsForUser(userID, rootDir))
                secureDirectoryService.createCategoryForUser(userID, rootDir);
            String leftPath = userData.getPath("left");
            String rightPath = userData.getPath("right");
            FileInfo[] leftFileList = secureDirectoryService.getFileList(secureDirectoryService.createFileLocationID(userID, rootDir, leftPath));
            FileInfo[] rightFileList = secureDirectoryService.getFileList(secureDirectoryService.createFileLocationID(userID, rootDir, rightPath));

            String[] leftURIs = null;
            String[] rightURIs = null;

            if (leftFileList != null) {
                leftURIs = new String[leftFileList.length];
                for (int i = 0; i < leftFileList.length; ++i) {
                    if (!leftFileList[i].isDirectory()) {
                        leftURIs[i] = secureDirectoryService.getDownloadFileUrl(secureDirectoryService.createFileLocationID(userID, rootDir, leftPath + leftFileList[i].getResource()), leftFileList[i].getResource(), null);
                    }
                }
            }
            if (rightFileList != null) {
                rightURIs = new String[rightFileList.length];
                for (int i = 0; i < rightFileList.length; ++i) {
                    if (!rightFileList[i].isDirectory()) {
                        rightURIs[i] = secureDirectoryService.getDownloadFileUrl(secureDirectoryService.createFileLocationID(userID, rootDir, rightPath + rightFileList[i].getResource()), rightFileList[i].getResource(), null);
                    }
                }
            }
            userData.setLeftFileList(leftFileList);
            userData.setRightFileList(rightFileList);
            userData.setLeftURIs(leftURIs);
            userData.setRightURIs(rightURIs);
            userData.setCorrect(Boolean.TRUE);
        } catch (PortletServiceException e) {
            log.error("Secure service unavailable", e);
            userData.setCorrect(Boolean.FALSE);
        } catch (IOException e) {
            userData.setCorrect(Boolean.FALSE);
        }
    }
}