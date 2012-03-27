Sup.

-- Checking out the project --

On windows, install mysysgit - http://code.google.com/p/msysgit/downloads/list 

After install in Git Bash, type "git clone git://github.com/croo/Szakdolgozat.git"
This will download the current repository to your PC into the same folder you gave the command.


-- Set up the project --

Apache Ant 1.8.3 - download it from http://apache.mirrors.crysys.hit.bme.hu/dist//ant/binaries/apache-ant-1.8.3-bin.zip
And set up the $ANT_HOME enviroment variable, and put %ANT_HOME%/bin to your classpath

Java JDK 1.7 - Set up the $JAVA_HOME enviroment variable, and put %JAVA_HOME%/bin to your classpath

After these the ant commands should work.

Jena - The libaries you need is in the repo : hopefully you don't need to do anyting. 
GoogleMaps API v2 - The libaries you need is in the repo : hopefully you don't need to do anyting.  
GWT 2.4 - The libaries you need is in the repo : hopefully you don't need to do anyting.


-- Check out the project with Eclipse --

Open Eclipse, File -> Import Project... -> Import existing project into workspace... -> browse to the project, and open it.
Yay.

-- Build from the command line with Ant --

You can use Ant to build the project. (http://ant.apache.org/)  
Ant uses the generated 'build.xml' file which describes exactly how to build your project.  
This file has been tested to work against Ant 1.8.3.  
The following assumes 'ant' is on your command line path.
The top of the build.xml files contains the properties to jars and other locations, such as the app-server domain directory.
Please modify these properties to suit your needs.


To run development mode, just type 'ant devmode'.

To compile your project for deployment, just type 'ant'.

To compile and also bundle into a .war file, type 'ant war'.

To deploy your project to a local app-server(Glassfish) type 'ant deploy'.

For a full listing of other targets, type 'ant -p'.
