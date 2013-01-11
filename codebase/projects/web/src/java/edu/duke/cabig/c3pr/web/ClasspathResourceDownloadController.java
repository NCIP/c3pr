/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web;

import java.io.ByteArrayInputStream;

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
        ByteArrayInputStream inputStream = null;
        try {
            file = request.getParameter("file");
            fileName = file.substring(file.lastIndexOf("/") + 1);
            inputStream = (ByteArrayInputStream) Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
        }
        catch (NullPointerException e) {
            throw new Exception("File not found. Please check path " + file);
        }

        if (inputStream != null ) {
        	int size = inputStream.available() ;
            String mimetype = servletContext.getMimeType(file);
            response.setBufferSize(size);
            response.setContentType(mimetype);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentLength(size);

            // for IE
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            FileCopyUtils.copy(inputStream, response.getOutputStream());
            inputStream.close();
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
        else {
            throw new Exception("File is invalid. Size is < 0. Please check path " + file);
        }

        return null;
    }

}
