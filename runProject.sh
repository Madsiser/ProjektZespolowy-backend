#!/bin/bash
docker-compose -f ../../docker-compose.yml down
./gradlew build
docker-compose -f ../../docker-compose.yml up --build
