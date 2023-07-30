FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests
# RUN cd demo/src/main/resources/static/js && echo -e "export const apiKey = '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';" >apikey.js

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]