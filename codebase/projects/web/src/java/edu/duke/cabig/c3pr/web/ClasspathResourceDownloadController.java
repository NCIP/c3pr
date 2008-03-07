package edu.duke.cabig.c3pr.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * This controller is used to download files from the UI. These files should be on the classpath
 * 
 * Created by IntelliJ IDEA. User: kherm Date: Jan 29, 2008 Time: 1:19:59 PM To change this template
 * use File | Settings | File Templates.
 */
public class ClasspathResourceDownloadController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
                    throws Exception {

        ServletContext servletContext = request.getSession().getServletContext();

        String file = null;
        String fileName = null;
        File uFile = null;
        int fSize = 0;
        try {
            file = request.getParameter("file");
            fileName = file.substring(file.lastIndexOf("/") + 1);

            uFile = new File(Thread.currentThread().getContextClassLoader().getResource(file)
                            .getFile());
            fSize = (int) uFile.length();
        }
        catch (NullPointerException e) {
            throw new Exception("File not found. Please check path " + file);
        }

        if (fSize > 0) {

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(uFile));
            String mimetype = servletContext.getMimeType(file);

            response.setBufferSize(fSize);
            response.setContentType(mimetype);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentLength(fSize);

            // for IE
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            FileCopyUtils.copy(in, response.getOutputStream());
            in.close();
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
        else {
            throw new Exception("File is invalid. Size is < 0. Please check path " + file);
        }

        return null;
    }

}
