FROM eclipse-temurin:21-jdk AS build-env

ARG VERSION
USER root
COPY ./ ./
RUN ./gradlew bootJar

FROM eclipse-temurin:21-jre-alpine
WORKDIR /App
ENV VERSION=${VERSION}
COPY --from=build-env build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]