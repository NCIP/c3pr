package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.net.MalformedURLException;
/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileStageParameter.java,v 1.1.1.1 2007-02-01 20:40:40 kherm Exp $
 * <p>
 * Represents a file stage parameter to a job.
 */

public class FileStageParameter {

    public static final String QUERY_ITEM_FILE_STAGE_TYPE = "filestagetype";
    public static final String QUERY_ITEM_FILE_STAGE_NAME = "filestagename";

    private static PortletLog log = SportletLog.getInstance(FileStageParameter.class);

    protected FileLocation fileLocation = null;

    public FileStageParameter() {
        super();
    }

    public FileStageParameter(FileLocation location) {
        fileLocation = location;
    }

    public FileStageParameter(FileLocation location, String stageName, FileStageType stageType) {
        fileLocation = location;
        setFileStageName(stageName);
        setFileStageType(stageType);
    }

    public FileStageParameter(FileStageParameter parameter) {
        if (parameter.fileLocation != null) {
            fileLocation = (FileLocation)parameter.fileLocation.clone();
        }
    }

    public String getFileStageUrl() {
        if (fileLocation == null) return null;
        return fileLocation.getUrl();
    }

    public String getFileStageUrlWithoutQuery() {
        if (fileLocation == null) return null;
        return fileLocation.getUrlWithoutQuery();
    }

    public FileLocation getFileLocation() {
        FileLocation location = null;
        try {
            location = new FileLocation(fileLocation.getUrlWithoutQuery());
        } catch (MalformedURLException e) {
            log.warn(e.getMessage());
        }
        return location;
    }

    public void setFileLocation(FileLocation location) {
        fileLocation = location;
    }

    public String getFileStageName() {
        if (fileLocation == null) return null;
        String fileStageName = fileLocation.getQueryItem(QUERY_ITEM_FILE_STAGE_NAME);
        if (fileStageName == null) fileStageName = fileLocation.getFileName();
        return fileStageName;
    }

    public void setFileStageName(String name) {
        if (fileLocation == null) return;
        fileLocation.setQueryItem(QUERY_ITEM_FILE_STAGE_NAME, name);
    }

    public FileStageType getFileStageType() {
        if (fileLocation == null) return null;
        String fileStageType = fileLocation.getQueryItem(QUERY_ITEM_FILE_STAGE_TYPE);
        if (fileStageType == null) return FileStageType.IN;
        return FileStageType.toFileStageType(fileStageType);
    }

    public void setFileStageType(FileStageType fileStageType) {
        if (fileLocation == null) return;
        if (fileStageType == null) fileStageType = FileStageType.IN;
        fileLocation.setQueryItem(QUERY_ITEM_FILE_STAGE_TYPE, fileStageType.getName());
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass().equals(FileStageParameter.class)) {
            FileStageParameter parameter = (FileStageParameter)o;
            return (fileLocation != null &&
                    parameter.fileLocation != null &&
                    fileLocation.equals(parameter.fileLocation));
        }
        return false;
    }

    public int hashCode() {
        if (fileLocation == null) return 0;
        return fileLocation.hashCode();
    }

    public Object clone() {
        FileStageParameter newParameter = new FileStageParameter();
        if (fileLocation != null) {
            newParameter.fileLocation =  (FileLocation)fileLocation.clone();
        }
        return fileLocation;
    }
}
