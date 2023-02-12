FROM openjdk:17-oracle

COPY web/build/libs/web-0.0.1-SNAPSHOT.jar genshin-artifact-recorder-server.jar

ENTRYPOINT ["java","-jar","/genshin-artifact-recorder-server.jar"]

EXPOSE 8709
