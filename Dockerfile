FROM openjdk:20-ea-31-jdk-oracle
VOLUME /cmmsAPI
ADD target/cmmsAPI-0.0.1-SNAPSHOT.jar cmmsAPI.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/cmmsAPI.jar"]
