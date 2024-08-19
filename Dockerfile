FROM amazoncorretto:21-alpine

WORKDIR /hotel

COPY rest/target/rest-0.0.1-SNAPSHOT.jar /hotel/hotel-docker.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/hotel/hotel-docker.jar"]