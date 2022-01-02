#!/bin/bash
./mvnw clean install
docker build -t opofa/quiz-api -f Dockerfile .