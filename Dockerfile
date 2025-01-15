FROM eclipse-temurin:21-jdk AS build-env
COPY ./ ./
RUN ./gradlew bootJar

FROM eclipse-temurin:21-jre-alpine
WORKDIR /App
COPY --from=build-env build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

# 다른거
# docker buildx build --platform linux/amd64 -t rainbowletter.azurecr.io/rainbowletter-api:develop . && docker push rainbowletter.azurecr.io/rainbowletter-api:develop
# intel
# docker build -t rainbowletter.azurecr.io/rainbowletter-api:develop . && docker push rainbowletter.azurecr.io/rainbowletter-api:develop