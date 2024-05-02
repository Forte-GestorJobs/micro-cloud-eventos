FROM openjdk:17-jdk-buster
VOLUME /tmp
EXPOSE 8083
ARG JAR_FILE=target/micro-cloud-eventos-spring-boot.jar
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]