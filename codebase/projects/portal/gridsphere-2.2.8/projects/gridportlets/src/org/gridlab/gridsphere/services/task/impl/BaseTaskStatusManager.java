package org.gridlab.gridsphere.services.task.impl;

import org.gridlab.gridsphere.services.task.TaskStatusListener;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.Vector;
import java.util.List;
import java.lang.reflect.Constructor;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseTaskStatusManager.java,v 1.1.1.1 2007-02-01 20:41:53 kherm Exp ${FILE}, ${VERSION} Mar 7, 2004 4:33:46 PM russell $
 * <p>
 * Specifies a transfer operation.
 */

public class BaseTaskStatusManager {

    private static PortletLog log = SportletLog.getInstance(BaseTaskStatusManager.class);
    private static BaseTaskStatusManager instance = null;
    private Class constructorClasses[] = new Class[0];
    private Object constructorParams[] = new Object[0];

    private BaseTaskStatusManager() {
    }

    public static BaseTaskStatusManager getInstance() {
        if (instance == null) {
            instance = new BaseTaskStatusManager();
        }
        return instance;
    }

    public List getTaskStatusListeners(List taskStatusListenerClassNames) {
        List taskStatusListeners = new Vector();
        for (int ii = 0; ii < taskStatusListenerClassNames.size(); ++ii)  {
            String listenerClassName = (String)taskStatusListenerClassNames.get(ii);
            try {
                Class listenerClass = Class.forName(listenerClassName);
                Constructor constructor = listenerClass.getConstructor(constructorClasses);
                TaskStatusListener listener = (TaskStatusListener)constructor.newInstance(constructorParams);
                taskStatusListeners.add(listener);
            } catch (Exception e) {
                log.error("Unable to get class instance for " + listenerClassName, e);
            }
        }
        return taskStatusListeners;
    }

    public List getTaskStatusListenerClassNames(List taskStatusListeners) {
        List taskStatusListenerClassNames = new Vector();
        for (int ii = 0; ii < taskStatusListeners.size(); ++ii)  {
            TaskStatusListener listener = (TaskStatusListener)taskStatusListeners.get(ii);
            String listenerClassName = listener.getClass().getName();
            taskStatusListenerClassNames.add(listenerClassName);
        }
        return taskStatusListenerClassNames;
    }

    public void addTaskStatusListener(TaskStatusListener listener,
                                      List taskStatusListeners,
                                      List taskStatusListenerClassNames) {
        String listenerClassName = listener.getClass().getName();
        boolean found = false;
        for (int ii = 0; ii < taskStatusListenerClassNames.size(); ++ii) {
            String nextClassName = (String)taskStatusListenerClassNames.get(ii);
            if (nextClassName.equals(listenerClassName)) {
                found = true;
                break;

            }
        }
        if (!found) {
            taskStatusListenerClassNames.add(listenerClassName);
            if (taskStatusListeners != null) {
                taskStatusListeners.add(listener);
            }
        }
    }
}
