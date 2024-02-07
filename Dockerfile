# base image
FROM eclipse-temurin:17-jdk-alpine

# port to expose the application
EXPOSE 8080

# what we are adding to the container
WORKDIR /app
ADD target/pura-vida-gourmet.jar pura-vida-gourmet.jar

# how to run the application
ENTRYPOINT ["java", "-jar", "pura-vida-gourmet.jar"]