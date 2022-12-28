FROM maven:3.6.3-openjdk-17 AS build
COPY ./pom.xml /app/pom.xml
WORKDIR /app
RUN mvn dependency:go-offline -B
COPY ./src /app/src

ENV DB_UPDATE_TYPE none
ENV KAFKA_HOST 34.173.164.107:9092

RUN mvn --show-version --update-snapshots --batch-mode clean package


FROM amazoncorretto:17

RUN mkdir /app && echo $db_hostname
WORKDIR /app
COPY --from=build ./app/target/ChargingStation-*.jar /app
EXPOSE 8080
CMD ["java", "-jar", "ChargingStation-0.0.1-SNAPSHOT.jar"]
