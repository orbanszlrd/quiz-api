FROM maven:3.8.4-jdk-11-slim@sha256:5b7f60171d0110de1d4afd82c3ba6047041cc28740d99155b7dd024540360021 AS build
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN mvn clean package

FROM adoptopenjdk/openjdk11:jre-11.0.13_8-alpine@sha256:3052f477dffe8063ee7e1cc9e5a19ff3b7835c40bf91b6bd798639da80c940de
RUN apk add dumb-init
RUN mkdir /app
RUN addgroup --system quizuser && adduser -S -s /bin/false -G quizuser quizuser
COPY --from=build /project/target/quiz-api-0.0.1-SNAPSHOT.jar /app/quiz-api.jar
WORKDIR /app
RUN chown -R quizuser:quizuser /app
USER quizuser
CMD "dumb-init" "java" "-jar" "quiz-api.jar"