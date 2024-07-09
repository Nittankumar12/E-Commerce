FROM openjdk:latest
VOLUME /tmp
ADD target/product-service.jar product-service.jar
ENTRYPOINT ["java", "-jar", "/product-service.jar"]
EXPOSE 8081
