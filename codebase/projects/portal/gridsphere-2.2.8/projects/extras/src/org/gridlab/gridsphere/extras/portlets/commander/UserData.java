/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version 1.0 2004/06/20
 */
package org.gridlab.gridsphere.extras.portlets.commander;

import org.gridlab.gridsphere.services.core.secdir.FileInfo;

import java.io.File;

public class UserData {
    private Boolean correct;
    private String leftPath;
    private FileInfo[] leftFileList;
    private String[] leftURIs;
    private String rightPath;
    private FileInfo[] rightFileList;
    private String[] rightURIs;
    private String state;
    private File editFile = null;
    private String editSide = null;

    public UserData() {
        this.leftPath = "/";
        this.rightPath = "/";
        this.state = "explore";
        this.correct = Boolean.FALSE;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public String getPath(String side) {
        if (side.equals("left")) {
            return leftPath;
        } else if (side.equals("right")) {
            return rightPath;
        }
        return "";
    }

    public void setPath(String side, String path) {
        if (side.equals("left")) {
            this.leftPath = path;
        } else if (side.equals("right")) {
            this.rightPath = path;
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public FileInfo[] getLeftFileList() {
        return leftFileList;
    }

    public void setLeftFileList(FileInfo[] leftFileList) {
        this.leftFileList = leftFileList;
    }

    public String[] getLeftURIs() {
        return leftURIs;
    }

    public void setLeftURIs(String[] leftURIs) {
        this.leftURIs = leftURIs;
    }

    public FileInfo[] getRightFileList() {
        return rightFileList;
    }

    public void setRightFileList(FileInfo[] rightFileList) {
        this.rightFileList = rightFileList;
    }

    public String[] getRightURIs() {
        return rightURIs;
    }

    public void setRightURIs(String[] rightURIs) {
        this.rightURIs = rightURIs;
    }

    public File getEditFile() {
        return editFile;
    }

    public void setEditFile(File editFile) {
        this.editFile = editFile;
    }

    public String getEditSide() {
        return editSide;
    }

    public void setEditSide(String editSide) {
        this.editSide = editSide;
    }
}
