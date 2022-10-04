FROM openjdk:17-alpine
COPY build/libs/resident-service-1.0.0.jar resident-service.jar
ENTRYPOINT ["java", "-jar", "resident-service.jar"]