# Xibix

# Requirements
Java 14+
Gradle 6.9+

# How to

In order to run the project, proceed as follows:

 gradle wrapper
 
 ./gradlew build or gradlew.bat build
 
After that you will find a Xibix-1.0.0.jar under build/libs.

Start the project with

 java -jar build/libs/Xibix-1.0.0.jar [JSON_FILE] [NUMBER_OF_VIEWSPOTS]
 
The project has a logger which is so defined that it logs to ${HOME}/CodingChallange.log and to console.
If you want to change that edit the file logging.properties under src/main/resources.
