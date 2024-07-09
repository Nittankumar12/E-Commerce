FROM openjdk:latest
VOLUME /tmp
ADD target/order-service.jar order-service.jar
ENTRYPOINT ["java", "-jar", "/order-service.jar"]
EXPOSE 8082
