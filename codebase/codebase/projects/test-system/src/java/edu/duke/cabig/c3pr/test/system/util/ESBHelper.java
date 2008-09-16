package edu.duke.cabig.c3pr.test.system.util;

import gov.nci.nih.cagrid.tests.core.util.AntUtils;
import gov.nci.nih.cagrid.tests.core.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Feb 15, 2007
 * Time: 8:05:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class ESBHelper {
    File tmpESBLocation;
    File esbHome;
    Process esbProcess;

    /**
     *
     * @param esbHome Basedirectory of the ESB project
     * Utility depends on esbHome/build.xml
     */
    public ESBHelper(File esbHome) {
        this.esbHome = esbHome;
    }

    public void createTempESB() throws IOException, InterruptedException {

        this.tmpESBLocation = FileUtils.createTempDir("esb", "dir", null);

        Properties sysProps = null;

        sysProps = new Properties();
        sysProps.setProperty("esb.deploy.dir", this.tmpESBLocation.toString());

        AntUtils.runAnt(esbHome,null,"deployESB",sysProps,null);

    }


    public void stopESB(){
        esbProcess.destroy();
    }

    public void startESB() throws IOException, InterruptedException{
        File java = new File(System.getProperty("java.home"), "bin" + File.separator + "java");
        String smLib = tmpESBLocation + File.separator + "lib";
        String smConf = tmpESBLocation + File.separator + "conf" + File.separator + "servicemix.conf";
        String classpath= smConf + File.pathSeparator + smLib + File.separator + "classworlds-1.0.1.jar";

        // build command
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add(java.toString());
        cmd.add("-classpath");
        cmd.add(classpath);
        cmd.add("-Dderby.system=" + tmpESBLocation + File.separator + "var");
        cmd.add("-Dderby.storage.fileSyncTransactionLog=true");
        cmd.add("-Dcom.sun.management.jmxremote");
        
        cmd.add("-Dservicemix.home=" + tmpESBLocation);
        cmd.add("-Djava.endorsed.dirs=" + smLib + File.separator + "endorsed");
        cmd.add("-Dclassworlds.conf=" +  smConf);
        cmd.add("org.codehaus.classworlds.Launcher");


        System.out.println(cmd.toString());
        // start globus
		esbProcess = Runtime.getRuntime().exec(cmd.toArray(new String[0]),null,tmpESBLocation);
		 

        //long wait
        sleep(15000);

    }

    public void cleanupTempESB(){

        if(tmpESBLocation != null)
            FileUtils.deleteRecursive(tmpESBLocation);
    }

    private static void sleep(long ms)
	{
		Object sleep = new Object();
		try { synchronized (sleep) { sleep.wait(ms); } }
		catch(Exception e) { e.printStackTrace(); }
	}

    //getters
    public File getTmpESBLocation() {
        return tmpESBLocation;
    }

    public Process getEsbProcess() {
        return esbProcess;
    }

    public File getEsbHome() {
        return esbHome;
    }
}
