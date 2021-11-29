
FROM adoptopenjdk/openjdk11
ARG JAR_FILE=build/libs
COPY ${JAR_FILE}/toileruserservice-0.0.1-SNAPSHOT.jar  toileruserservice-0.0.1-SNAPSHOT.jar
#/usr/local/tomcat/webapps/
ENTRYPOINT ["java","-jar","toileruserservice-0.0.1-SNAPSHOT.jar"]