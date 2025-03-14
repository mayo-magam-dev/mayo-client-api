FROM openjdk:17-jdk-slim

LABEL maintainer="wnstj0614@naver.com"
LABEL description="mayo(마감해요) Client Backend API 서버입니다."

WORKDIR /opt

COPY *.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]