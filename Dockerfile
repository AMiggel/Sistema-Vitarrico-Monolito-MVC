FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/spring-boot-data-jpa-0.0.1-SNAPSHOT.jar target/app.jar
EXPOSE 9010
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=local","target/app.jar"]
