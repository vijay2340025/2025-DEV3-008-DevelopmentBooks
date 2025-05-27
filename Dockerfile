FROM gcr.io/distroless/java21-debian12:nonroot

USER nobody

WORKDIR /app

COPY target/DevelopmentBooks-0.0.1-SNAPSHOT.jar /bookstore/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/bookstore/DevelopmentBooks-0.0.1-SNAPSHOT.jar"]