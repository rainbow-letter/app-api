FROM eclipse-temurin:21-jdk AS build-env
COPY ./ ./
RUN ./gradlew bootJar

FROM eclipse-temurin:21-jre-alpine
WORKDIR /App
COPY --from=build-env build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]