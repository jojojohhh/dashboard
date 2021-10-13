FROM openjdk:8-jdk-alpine
VOLUME /srv/docker/spring-dashborad
ARG JAR_FILE=dashboard-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} dashboard.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-jar", "/dashboard.jar"]