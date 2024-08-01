FROM openjdk:22
VOLUME /tmp
ADD target/authentication-service.jar authentication-service.jar
ENTRYPOINT ["java", "-jar", "/authentication-service.jar"]
EXPOSE 8083
