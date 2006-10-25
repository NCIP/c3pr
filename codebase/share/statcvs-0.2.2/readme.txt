
  StatCVS README
  ==============

      StatCVS is a statistics tool for CVS repositories. It generates
      HTML reports from CVS log files.


  The StatCVS Manual

      The StatCVS manual is located here: http://statcvs.sf.net/manual


  Quick Start

      * Download the latest release from http://statcvs.sf.net/
      * Expand the zip file into some directory, e.g c:\statcvs
      * Check out a working copy of the desired CVS module into
        some directory, e.g. c:\myproject.
      * Change into that directory and type
        'cvs log > myproject.log'
      * Change back to the c:\statcvs directory
      * type 'java -jar statcvs.jar c:\myproject\cvslog c:\myproject'
      * Open c:\statcvs\index.html in your web browser

      You can tweak the output of StatCVS in various ways. Run
      'java -jar statcvs.jar' for an overview of the command line
      parameters, and check the manual for full information.
