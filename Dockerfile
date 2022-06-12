FROM openjdk:8-alpine

COPY target/uberjar/rental.jar /rental/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/rental/app.jar"]
