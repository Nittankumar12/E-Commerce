FROM openjdk:latest
VOLUME /tmp
ADD target/api-gateway.jar api-gateway.jar
ENTRYPOINT ["java", "-jar", "/api-gateway.jar"]
EXPOSE 8765