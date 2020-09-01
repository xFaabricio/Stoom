FROM openjdk:8
EXPOSE 8080
ADD target/stoom.jar
ENTRYPOINT ["java", "-jar", "/stoom.jar"]