FROM openjdk:8-jdk
WORKDIR /usr/app
COPY ./target/*.jar /order-service.jar
EXPOSE 8083
ENTRYPOINT [ "sh", "-c", "java  -jar /order-service.jar" ]