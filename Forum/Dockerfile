FROM openjdk:21
VOLUME /tmp
WORKDIR /app
COPY build/libs/Forum-0.0.1-SNAPSHOT-plain.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]