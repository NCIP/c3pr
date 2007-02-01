package org.gridlab.gridsphere.extras.portlets.charts;

import org.gridlab.gridsphere.services.core.charts.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridlab.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridlab.gridsphere.provider.portletui.beans.ImageBean;
import org.gridlab.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridlab.gridsphere.services.core.secdir.FileLocationID;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import java.util.Map;

/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version $Id: ChartTest03.java,v 1.1.1.1 2007-02-01 20:07:24 kherm Exp $
 */
public class ChartTest03 extends ActionPortlet {
    private ChartService chartService = null;
/*
    chart dataset file, and chart image file will be stored in secure directory under
    CHARTAPP root directory ("commander" is the same as for Commander Portlet -
    this will allow to see, modify, download, uploat dataset through Commander
    It is possible to change filename or webAppName of chart or dataset - please change
    ChartDescriptor->FileInfo->  [
        Image->Filename,
        Image->AppName,
        Image->Type, {JPEG or PNG supported, default PNG}
        Image->Quality {for JPEG only, default 0.75}
        Image->Width,   {default 400}
        Image->Height,  {default 300}
        Dataset->Filename,
        Dataset->AppName
    ])
*/
    private static final String CHARTAPP = "commander";

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

        String chartURL = chartService.getChartUrl(chartService.createChartLocationID(userID, CHARTAPP, "ChartTest3"));
        ImageBean imageBean = event.getImageBean("chart3");
        if (chartURL != null)
            imageBean.setSrc(chartURL);
        setNextState(event.getRenderRequest(), "charts/view3.jsp");
    }

    public void createChart(ActionFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        org.jfree.data.DefaultCategoryDataset dataset = new org.jfree.data.DefaultCategoryDataset();
        dataset.setValue(179700, "Pacific", "Ocean");
        dataset.setValue(106463, "Atlantic", "Ocean");
        dataset.setValue(74917, "Indian", "Ocean");
        dataset.setValue(44406, "Asia", "Land");
        dataset.setValue(29853, "Africa", "Land");
        dataset.setValue(24298, "North America", "Land");
        dataset.setValue(17684, "South America", "Land");
        dataset.setValue(13176, "Antarctic", "Land");
        dataset.setValue(9763, "Europe", "Land");
        dataset.setValue(8936, "Australia", "Land");
        try {
            FileLocationID chartLocationID = chartService.createChartLocationID(userID, CHARTAPP, "ChartTest3");

            ChartDescriptor chartDescriptor = chartService.createStackedBar3DChart(chartLocationID, dataset);

//            All settings below are optional

            Category category = chartDescriptor.getChartInfo().getPlot().getSettings().getCategory();
            category.setRangeAxisLabel("area [mln * km2]");
            category.setDomainAxisLabel("");
            category.setPlotOrientation("PlotOrientation.HORIZONTAL");

            SeriesPaint pacificPaint = new SeriesPaint();
            Gradient pacificGradient = new Gradient();
            GradientPoint point1 = new GradientPoint();
            GradientPoint point2 = new GradientPoint();
            Color yellow = new Color();
            yellow.setGreen(255);
            yellow.setRed(255);
            Color white = new Color();
            white.setBlue(255);
            white.setRed(255);
            white.setGreen(255);
            point1.setColor(white);
            point1.setX(100);
            point1.setY(0);
            point2.setColor(yellow);
            point2.setX(200);
            point2.setY(0);
            pacificGradient.addGradientPoint(point1);
            pacificGradient.addGradientPoint(point2);
            pacificPaint.setGradient(pacificGradient);
            chartDescriptor.getChartInfo().getPlot().addSeriesPaint(pacificPaint);

//            changing image filename (please check Commander)

            chartDescriptor.getFileInfo().getImage().setFilename("Chart3Test_but_other_filename");


//            changing chart background to light yellow

            BackgroundPaint backgroundPaint = new BackgroundPaint();
            Color lgreen = new Color();
            lgreen.setBlue(215);
            lgreen.setRed(215);
            lgreen.setGreen(255);
            backgroundPaint.setColor(lgreen);
            chartDescriptor.getChartInfo().setBackgroundPaint(backgroundPaint);


            chartDescriptor.getChartInfo().getPlot().setForegroundAlpha(0.75f);
//            After making settings chartDescriptor have to be passed to method setChartDescriptor !!!

            chartService.setChartDescriptor(chartLocationID, chartDescriptor);
            chartService.setChartTitle(chartLocationID, "Area of Oceans and Continents");
        } catch (Exception e) {
        }
        setNextState(event.getActionRequest(), "viewChart");
    }

    public void createChartNoData(ActionFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        org.jfree.data.DefaultCategoryDataset dataset = new org.jfree.data.DefaultCategoryDataset();
        try {
            chartService.createStackedBarChart(chartService.createChartLocationID(userID, CHARTAPP, "ChartTest3"), dataset);
        } catch (Exception e) {
        }
        setNextState(event.getActionRequest(), "viewChart");
    }
}
