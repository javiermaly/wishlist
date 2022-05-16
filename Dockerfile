FROM adoptopenjdk/openjdk11:alpine AS builder
MAINTAINER maly.uy
WORKDIR build
COPY .mvn ./.mvn
COPY mvnw .
COPY pom.xml .
COPY src/main/resources/application-prod.properties .
RUN ./mvnw dependency:go-offline
COPY . .
RUN ./mvnw package

FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER maly.uy
RUN apk add --no-cache tzdata
RUN adduser --shell /bin/sh --disabled-password --gecos "" maly
USER maly:maly
ENV TZ=America/Montevideo
WORKDIR app
COPY --from=builder build/target/wishlist.jar ./app.jar
COPY --from=builder build/application-prod.properties .
ENTRYPOINT java -jar app.jar --spring.config.location=file:./application-prod.properties
