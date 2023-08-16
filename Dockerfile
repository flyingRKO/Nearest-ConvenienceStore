FROM openjdk:11
ARG JAR_FILE=build/libs/store.jar
COPY ${JAR_FILE} ./store.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "./store.jar"]