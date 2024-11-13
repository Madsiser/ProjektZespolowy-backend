FROM openjdk:21
VOLUME /tmp
WORKDIR /app
COPY ./Forum/build/libs/Forum-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
