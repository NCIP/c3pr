package edu.duke.cabig.c3pr.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

public class LogWriter{

  static private LogWriter instance;
  private static String baseDir ="/local/content/";
  private LogWriter() {
    init();
  }

  static synchronized public LogWriter getInstance()
   {

     if (instance == null)
      {

       instance = new LogWriter();
      }

      return instance;
   }

   private void init(){
     try{
     logger = Logger.getLogger("gov.nih.nci.c3pr");
     cal.setTime(new Date());
     if(!ApplicationUtils.isUnix())
    {
      baseDir = "C:/";
    }
     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
     String fileName = formatter.format(new Date())+"Log.xml";
//     String fileName = cal.get(Calendar.YEAR)+"_"+(cal.get(Calendar.MONTH)+1)+"_"+cal.get(Calendar.DATE)+"Log.xml";
     FileHandler filehandler = new FileHandler(baseDir+"c3pr/log/"+fileName);
     filehandler.setFormatter(new XMLFormatter());// comment out this line for no XML
     logger.addHandler(filehandler);
     logger.setLevel(Level.ALL);
     }catch(Exception ex){
       ex.printStackTrace();
     }
   }
   private void checkAndSwitch()
   {
     Calendar now = new GregorianCalendar();
     now.setTime(new Date());
     long nowSec = now.getTimeInMillis();
     long thenSec = cal.getTimeInMillis();
     if((nowSec-thenSec)>HR)
     {
       init();
     }
   }

   public void log(Level level, String msg){
     logger.log(level,msg);
   }
   public synchronized void  log(Level level, String msg, Object param1) {
     this.checkAndSwitch();
     logger.log(level, msg,param1);
   }
 private static final long HR = 24*60*60*1000;
 private Calendar cal = new GregorianCalendar();
 private Logger logger = null;
 public static Level ALL = Level.ALL;
 public static Level CONFIG = Level.CONFIG;
 public static Level FINE = Level.FINE;
 public static Level FINER = Level.FINER;
 public static Level FINEST = Level.FINEST;
 public static Level INFO = Level.INFO;
 public static Level OFF = Level.OFF;
 public static Level SEVERE = Level.SEVERE;
 public static Level WARNING = Level.WARNING;

 public static void main(String[] args)
 {
   LogWriter lg = LogWriter.getInstance();
 }
}