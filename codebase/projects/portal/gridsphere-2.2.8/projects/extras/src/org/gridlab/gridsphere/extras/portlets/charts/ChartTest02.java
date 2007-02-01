package org.gridlab.gridsphere.extras.portlets.charts;

import org.gridlab.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridlab.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridlab.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridlab.gridsphere.provider.portletui.beans.ImageBean;
import org.gridlab.gridsphere.services.core.charts.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.services.core.secdir.FileLocationID;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import java.util.Map;

/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version $Id: ChartTest02.java,v 1.1.1.1 2007-02-01 20:07:24 kherm Exp $
 */
public class ChartTest02 extends ActionPortlet {
    private ChartService chartService = null;

/*
    setting appName for "mySecretApplication", this will allow to hide both dataset and image file
    you can also set different appNames for dataset, and for image check:
    ChartDescriptor->FileInfo->  [
        Image->Filename,
        Image->AppName,
        Dataset->AppName
        Dataset->Filename,
    ])

*/

    private static final String CHARTAPP = "mySecretApplication";

    public void init(PortletConfig portletConfig) throws PortletException {
        super.init(portletConfig);
        try {
            chartService = (org.gridlab.gridsphere.services.core.charts.ChartService) createPortletService(org.gridlab.gridsphere.services.core.charts.ChartService.class);
            DEFAULT_VIEW_PAGE = "viewChart";
        } catch (PortletServiceException e) {
            DEFAULT_VIEW_PAGE = "charts/error.jsp";
        }
    }

    public void viewChart(RenderFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getRenderRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
/*
        Always invoke getChartUrl before showing chart on page !!!
        If chartfile is older then dataset, or some chart settings has been changed
        chart will be automaticly regenerated.
*/

        String chartURL = chartService.getChartUrl(chartService.createChartLocationID(userID, CHARTAPP, "some/directort\\with/ChartTest2"));
        ImageBean imageBean = event.getImageBean("chart2");
        if (chartURL != null)
            imageBean.setSrc(chartURL);
        setNextState(event.getRenderRequest(), "charts/view2.jsp");
    }

    public void createChart(ActionFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        org.jfree.data.DefaultCategoryDataset dataset = new org.jfree.data.DefaultCategoryDataset();
        dataset.setValue(979, "South America", "Salto del Angel");
        dataset.setValue(604, "Europe", "Giessbach");
        dataset.setValue(580, "Oceania", "Sutherland");
        dataset.setValue(491, "North America", "Ribbon");
        dataset.setValue(422, "Europe", "Gavarnie");
        dataset.setValue(274, "Europe", "Vettisfoss");
        dataset.setValue(253, "Asia", "Jog");
        dataset.setValue(213, "North America", "Fairy");
        dataset.setValue(108, "Africa", "Victoria");
        dataset.setValue(55, "North America", "Niagara");
        try {
            FileLocationID chartLocationID = chartService.createChartLocationID(userID, CHARTAPP, "some/directort\\with/ChartTest2");

            ChartDescriptor chartDescriptor = chartService.createBar3DChart(chartLocationID, dataset);

//            All settings below are optional

//            setting descriptions for axises

            chartDescriptor.getChartInfo().getPlot().getSettings().getCategory().setRangeAxisLabel("height [m]");
            chartDescriptor.getChartInfo().getPlot().getSettings().getCategory().setDomainAxisLabel("name");

//            setting image for JPEG, maximum quality, image size(950,400)
            Image image = chartDescriptor.getFileInfo().getImage();
            image.setWidth(950);
            image.setHeight(400);
            image.setQuality(1.0f);
            image.setType("JPEG");

//            subtitle ;-)

            Subtitle subtitle = new Subtitle();
            subtitle.setText("Obviously this is not full list ;-)");
            chartDescriptor.getChartInfo().setSubtitle(subtitle);
            chartDescriptor.getChartInfo().getPlot().setForegroundAlpha(0.25f);
//            After making settings chartDescriptor have to be passed to method setChartDescriptor !!!

            chartService.setChartDescriptor(chartLocationID, chartDescriptor);
            chartService.setChartTitle(chartLocationID, "Waterfalls of the World");
        } catch (Exception e) {
        }
        setNextState(event.getActionRequest(), "viewChart");
    }

}
