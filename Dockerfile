FROM openjdk:8
ADD target/exam-mongodb.jar exam-mongodb.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","exam-mongodb.jar"]