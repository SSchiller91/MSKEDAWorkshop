FROM openjdk:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 3000
CMD ["./gradlew", "clean", "bootJar"]
ENTRYPOINT ["java","-jar","app.jar"]