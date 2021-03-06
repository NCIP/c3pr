/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.navigation;

import java.util.List;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;
import edu.duke.cabig.c3pr.tools.Configuration;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationProperty;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationPropertyEditor;

/**
 * @author Rhett Sutphin, Priyatam
 */
public class SectionInterceptor extends HandlerInterceptorAdapter {
    private List<Section> sections;

    private String attributePrefix;

    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    private Configuration configuration;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String controllerPath = urlPathHelper.getPathWithinServletMapping(request);
        Section current = findSection(controllerPath);
        Task currentTask = findTask(current, controllerPath);
        Task currentSubTask = findSubTask(current, controllerPath);

        request.setAttribute(prefix("currentSection"), current);
        request.setAttribute(prefix("currentTask"), currentTask);
        request.setAttribute(prefix("currentSubTask"), currentSubTask);
        request.setAttribute(prefix("sections"), getSections());

        return true;
    }

    private Section findSection(String controllerPath) {
        for (Section section : getSections()) {
            for (String pattern : section.getPathMappings()) {
                if (pathMatcher.match(pattern, controllerPath)) {
                    return section;
                }
            }
        }
        return null;
    }

    private Task findTask(Section section, String controllerPath) {
		for (Task task : getTasks(section)) {
			if (task.getUrl().indexOf(controllerPath) > -1) {
				return task;
			} else {
				for (Task subTask : task.getSubTasks()) {
					if (subTask.getUrl().indexOf(controllerPath) > -1) {
						return task;
					}else{
						for (Task sub : subTask.getSubTasks()) {
    						if (sub.getUrl().indexOf(controllerPath) > -1) {
    							return task;
    						}
    					}
					}
				}
			}
		}
		return null;
	}

    private Task findSubTask(Section section, String controllerPath) {
    	 for (Task task : getTasks(section)) {
    		  for (Task subTask: task.getSubTasks()){
    			  if (subTask.getUrl().indexOf(controllerPath) > -1) {
    	                 return subTask;
    	          }else{
    	        	  for (Task sub : subTask.getSubTasks()) {
  						if (sub.getUrl().indexOf(controllerPath) > -1) {
  							return subTask;
  						}
  					}
    	          }
    		  }
         }
         return null;
    }

    private String prefix(String attr) {
        if (getAttributePrefix() == null) {
            return attr;
        }
        else {
            return getAttributePrefix() + attr;
        }
    }

    public List<Task> getTasks(Section section) {
        return section.getTasks();
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public String getAttributePrefix() {
        return attributePrefix;
    }

    public void setAttributePrefix(String attributePrefix) {
        this.attributePrefix = attributePrefix;
    }
}
