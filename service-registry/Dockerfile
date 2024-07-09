FROM openjdk:latest
VOLUME /tmp
ADD target/service-registry.jar service-registry.jar
ENTRYPOINT ["java", "-jar", "/service-registry.jar"]
EXPOSE 8761