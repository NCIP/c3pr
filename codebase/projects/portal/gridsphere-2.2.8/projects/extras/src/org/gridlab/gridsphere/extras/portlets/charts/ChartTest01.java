package org.gridlab.gridsphere.extras.portlets.charts;

import org.gridlab.gridsphere.services.core.charts.*;
import org.gridlab.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridlab.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridlab.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridlab.gridsphere.provider.portletui.beans.ImageBean;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.services.core.secdir.FileLocationID;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import java.util.Map;

/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version $Id: ChartTest01.java,v 1.1.1.1 2007-02-01 20:07:24 kherm Exp $
 */

public class ChartTest01 extends ActionPortlet {
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
            chartService = (ChartService) createPortletService(ChartService.class);
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

        String chartURL = chartService.getChartUrl(chartService.createChartLocationID(userID, CHARTAPP, "ChartTest1"));
        ImageBean imageBean = event.getImageBean("chart1");
        if (chartURL != null)
            imageBean.setSrc(chartURL);
        setNextState(event.getRenderRequest(), "charts/view1.jsp");
    }

    public void createChart(ActionFormEvent event) throws PortletException {
        String userID = (String) ((Map) event.getActionRequest().getAttribute(PortletRequest.USER_INFO)).get("user.id");
        org.jfree.data.DefaultPieDataset dataset = new org.jfree.data.DefaultPieDataset();
        dataset.setValue("Pacific", 179700);
        dataset.setValue("Atlantic", 106463);
        dataset.setValue("Indian", 74917);
/*
        "ChartTest1" is name of chart and dataset file (please sepecify it without extension !!!)

        Please try to change it for some path (also in viewChart method !!!) f.e. "/chart/test/ChartTest1" or "creazy/path\\chart\\test//////testChartTest1"
        SecureDirectoryService will create path the path - after chart is deleted
        path is be also deleted (recursively to the first level where some other files are, or to the secure directory base)
*/
        try {
            FileLocationID chartLocationID = chartService.createChartLocationID(userID, CHARTAPP, "ChartTest1");

            ChartDescriptor chartDescriptor = chartService.createPie3DChart(chartLocationID, dataset);

//            All settings below are optional

//          defining and setting paints (colors, but can be also gradients) for chart

            SeriesPaint seriesPaintPacific = new SeriesPaint();
            SeriesPaint seriesPaintAtlantic = new SeriesPaint();
            SeriesPaint seriesPaintIndian = new SeriesPaint();

            Color blue = new Color();
            blue.setBlue(255);

            Color white = new Color();
            white.setBlue(255);
            white.setRed(255);
            white.setGreen(255);

            Color grey = new Color();
            grey.setBlue(200);
            grey.setRed(200);
            grey.setGreen(200);

            seriesPaintPacific.setColor(blue);
            seriesPaintIndian.setColor(white);
            seriesPaintAtlantic.setColor(grey);

            chartDescriptor.getChartInfo().getPlot().addSeriesPaint(0, seriesPaintPacific);
            chartDescriptor.getChartInfo().getPlot().addSeriesPaint(1, seriesPaintAtlantic);
            chartDescriptor.getChartInfo().getPlot().addSeriesPaint(2, seriesPaintIndian);

//            setting chart foreground alfa for 50%

            chartDescriptor.getChartInfo().getPlot().setForegroundAlpha(0.5f);

//          creating and setting gradient paint for plot background
            BackgroundPaint backgroundPaint = new BackgroundPaint();

            Gradient background = new Gradient();
            GradientPoint point1 = new GradientPoint();
            point1.setColor(white);
            point1.setX(0);
            point1.setY(0);
            GradientPoint point2 = new GradientPoint();
            point2.setColor(blue);
            point2.setX(400);
            point2.setY(300);
            background.addGradientPoint(point1);
            background.addGradientPoint(point2);

            backgroundPaint.setGradient(background);
            chartDescriptor.getChartInfo().getPlot().setBackgroundPaint(backgroundPaint);

//            turn off legend

            chartDescriptor.getChartInfo().setLegend(false);

//            setting label generator - {0} for name {1} for value {2} for percents

            chartDescriptor.getChartInfo().getPlot().getSettings().getPie().setLabelGenerator("{0} Ocean is {2}");

//            After making settings chartDescriptor have to be passed to method setChartDescriptor !!!
            chartService.setChartDescriptor(chartLocationID, chartDescriptor);
            chartService.setChartTitle(chartLocationID, "Area of oceans [mln * km2]");
        } catch (Exception e) {
        }
        setNextState(event.getActionRequest(), "viewChart");
    }
}
