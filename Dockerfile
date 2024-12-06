## 1. Use an official OpenJDK image as the base image
#FROM openjdk:17-jdk-slim
#
## 2. Set environment variables
#ENV APP_HOME=/app
#ENV GRADLE_OPTS="-Dorg.gradle.daemon=false"
#
## 3. Set the working directory
#WORKDIR $APP_HOME
#
## 4. Copy the Gradle wrapper and build.gradle files
#COPY gradlew $APP_HOME/
#COPY gradle $APP_HOME/gradle
#COPY build.gradle $APP_HOME/
#COPY settings.gradle $APP_HOME/
#
## 5. Copy the source code
#COPY src $APP_HOME/src
#
## 6. Copy other resources like application.properties
#COPY src/main/resources/application.properties $APP_HOME
#
## 7. Grant execution permission to Gradle wrapper
#RUN chmod +x gradlew
#
## 8. Build the application using Gradle
#RUN ./gradlew clean build -x test
#
#RUN ls build/libs
#
## 9. Copy the generated JAR file to the working directory
#COPY build/libs/*.jar app.jar
#
## 10. Expose the application port
#EXPOSE 8080
#
## 11. Define the entry point to run the application
#ENTRYPOINT ["java", "-jar", "app.jar"]

# 1. Use an official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# 2. Set environment variables
ENV APP_HOME=/app
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false"

# 3. Set the working directory
WORKDIR $APP_HOME

# 4. Copy the Gradle wrapper and build.gradle files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 5. Copy the source code
COPY src src

# 6. Grant execution permission to Gradle wrapper
RUN chmod +x gradlew

# 7. Build the application using Gradle
RUN ./gradlew clean bootJar

# 8. Move the JAR file (수정된 부분)
RUN find build/libs/ -name '*.jar' -exec cp {} app.jar \;
RUN #find build/libs/ -name '*SNAPSHOT.jar' -exec cp {} app.jar \;

# 9. Expose the application port
EXPOSE 8080

# 10. Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]