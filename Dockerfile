FROM openjdk:11
ARG JAR_FILE=build/libs/snowbox.jar
COPY ${JAR_FILE} ./snowbox.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "./snowbox.jar"]