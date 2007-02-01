package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PortletFrameRegistry {

    private static PortletFrameRegistry instance = new PortletFrameRegistry();
    private static HashMap portlets = new HashMap();;

    private PortletFrameRegistry() {}

    public static PortletFrameRegistry getInstance() {
        return instance;
    }

    public PortletFrame getPortletFrame(String label, String portletId, GridSphereEvent event) {
        String sessionId = event.getPortletRequest().getPortletSession(true).getId();
        Map map = (Map)portlets.get(sessionId);
        PortletFrame frame = null;
        if (map != null) {
            frame = (PortletFrame)map.get(label);
            if (frame != null) return frame;
        } else {
            map = new HashMap();
        }
        if (portletId == null) return null;
        frame = new PortletFrame();
        frame.setPortletClass(portletId);
        frame.setLabel(label);
        frame.init(event.getPortletRequest(), new ArrayList());
        map.put(label, frame);
        portlets.put(sessionId, map);
        return frame;
    }

    public void removeAllPortletFrames(GridSphereEvent event) {
        String sessionId = event.getPortletRequest().getPortletSession(true).getId();
        portlets.remove(sessionId);
    }
}
