FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests
RUN cd demo/src/main/resources/static/js && echo -e "const apiKey = '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';\n\nexport default apiKey;" > apikey.js

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]
