
FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} toiletproject-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","toiletproject-0.0.1-SNAPSHOT.jar"]