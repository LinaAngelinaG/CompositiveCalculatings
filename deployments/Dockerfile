FROM maven AS build
WORKDIR /app
COPY pom.xml ./pom.xml
RUN mvn -f pom.xml dependency:go-offline -B
COPY . .
RUN mvn -f pom.xml clean package -Dmaven.test.skip -DskipTests -T 8C

FROM openjdk:16-alpine3.13
COPY --from=build /app/target/CompositiveCalculatings-1.0-SNAPSHOT.jar /CompositiveCalculatings.jar
ENTRYPOINT ["java","-jar","/CompositiveCalculatings.jar"]
