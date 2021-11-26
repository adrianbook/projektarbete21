
FROM adoptopenjdk/openjdk11
ARG JAR_FILE=build/libs
COPY ${JAR_FILE}/toiletproject-0.0.1-SNAPSHOT.jar  toiletproject-0.0.1-SNAPSHOT.jar
#/usr/local/tomcat/webapps/
ENTRYPOINT ["java","-jar","toiletproject-0.0.1-SNAPSHOT.jar"]