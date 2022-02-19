FROM openjdk:8-jdk
WORKDIR /usr/app
COPY ./target/*.jar /payment-service.jar
EXPOSE 8083
ENTRYPOINT [ "sh", "-c", "java  -jar /payment-service.jar" ]