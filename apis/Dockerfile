# base image
FROM eclipse-temurin:17-jre-alpine

# port to expose the application
EXPOSE 8080

#RUN groupadd -g 999 puravida && useradd -r -u 999 -g puravida puravida

# what we are adding to the container
WORKDIR /app

#RUN chmod +755 /app

ADD ../target/pura-vida-gourmet-api.jar pura-vida-gourmet-api.jar

#USER puravida

ENV DATABASE_URL jdbc:postgresql://db:5432/puraVida
ENV DATABASE_USER puravida
ENV DATABASE_PASSWORD gourmet

# Run the application
ENTRYPOINT ["java", "-jar", "pura-vida-gourmet-api.jar"]