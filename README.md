# GoEuro cities fetcher
GoEuro cities fetcher

## Install
The fetcher requires Java 8 to run.
Since Java 8 is installed, everything is ready to fetch cities.

## Build
To build the fetcher you need Maven. Cd into a folder of the project and type `mvn clean package` on the command line.

To view code coverage report after `maven clean package` has been executed, go to `target/site/jacoco` subfolder and view `index.html` file in this subfolder with your browser.

To generate project reports (FindBugs, CMD, PMD and CheckStyle), cd into a folder of the project and type `mvn site` on the command line.

To view these reports, go to `target/site` subfolder and view `project-reports.html` file in this subfolder with your browser.

## Run
Cd into a folder of the project and type on the command line `java -jar target/cities-fetcher-1.0-SNAPSHOT.jar "CITY_NAME"`.

