FROM openjdk:22
VOLUME /tmp
ADD target/service-registry.jar service-registry.jar
ENTRYPOINT ["java", "-jar", "/service-registry.jar"]
EXPOSE 8761