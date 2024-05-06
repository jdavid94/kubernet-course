ARG MSVC_NAME=msvc-users
FROM openjdk:17-alpine as builder
ARG MSVC_NAME
WORKDIR /app/$MSVC_NAME

COPY ./pom.xml /app
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw .
COPY ./$MSVC_NAME/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./$MSVC_NAME/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-alpine
ARG MSVC_NAME
WORKDIR /app
RUN mkdir ./logs
ARG TARGET_FOLDER=/app/$MSVC_NAME/target
COPY --from=builder $TARGET_FOLDER/msvc-users-0.0.1-SNAPSHOT.jar .
ENV PORT 8001
EXPOSE $PORT
CMD sleep 20 && java -jar msvc-users-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java", "-jar", "msvc-users-0.0.1-SNAPSHOT.jar"]

