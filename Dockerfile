FROM openjdk:8
ADD target/exam.jar exam.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","exam.jar"]