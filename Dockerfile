FROM maven:3-openjdk-17

ARG PROFILE_ACTIVE

ENV PROFILE_ACTIVE=${PROFILE_ACTIVE}

WORKDIR /app

COPY . /app

EXPOSE 8080

EXPOSE 43049

RUN mvn clean package -DskipTests

ENTRYPOINT ["mvn", "spring-boot:run", "-Dspring-boot.run.profiles=${PROFILE_ACTIVE}"]
