FROM openjdk:17

ARG PROFILE_ACTIVE

ARG ADDITIONAL_OPTS

ENV PROFILE_ACTIVE=${PROFILE_ACTIVE}

WORKDIR /app

COPY /target/movie-catch-api*.jar movie-catch-api.jar

EXPOSE 8080

EXPOSE 43049

ENTRYPOINT ["java", "-jar", "movie-catch-api.jar", "--spring.profiles.active=${PROFILE_ACTIVE}"]
