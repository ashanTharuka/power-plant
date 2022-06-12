FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/*.jar power-plant.jar
ENV TZ=Asia/Colombo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT ["java","-jar","/power-plant.jar"]
EXPOSE 9999


