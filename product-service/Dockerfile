# build the jar
FROM maven:3.8-eclipse-temurin-11 AS mvn
WORKDIR /src
COPY pom.xml .
COPY src ./src
RUN mvn -q package -DskipTests

FROM eclipse-temurin:11-jdk-alpine AS build
LABEL maintainer="Samuel Moreno <samuelmc@arizona.edu>"
COPY --from=mvn /src/target/*.jar app.jar
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /app.jar)

FROM eclipse-temurin:11-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.samazon.product.ProductServiceApplication"]