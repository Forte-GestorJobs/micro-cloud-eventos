FROM openjdk:17-jdk-buster
VOLUME /tmp
EXPOSE 8083
ARG JAR_FILE=target/micro-cloud-eventos-spring-boot.jar
ADD ${JAR_FILE} app.jar

ENV MCE_DBHOST "gestor-jobs-9572.7tc.aws-eu-central-1.cockroachlabs.cloud"
ENV MCE_DBNAME "gestor-jobs"
ENV MCE_DBPORT 26257
ENV MCE_DBPWD "1uapORTatdlFQRMvqErw4Q"
ENV MCE_DBUSER "postgres"


ENTRYPOINT ["java","-jar","/app.jar"]