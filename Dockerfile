FROM openjdk:20-ea-31-jdk-oracle
VOLUME /cmmsApi
ADD target/cmmsApi-0.0.1-SNAPSHOT.jar cmmsApi.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/cmmsApi.jar"]
