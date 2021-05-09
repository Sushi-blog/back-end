FROM openjdk:11-jre-slim
EXPOSE 8081
ENV TZ=Asia/Seoul
COPY ./build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
