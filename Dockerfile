FROM ubuntu:latest
WORKDIR /app
RUN sudo apt update -y && sudo apt install -y openjdk-17-jdk
COPY /target/MovieCatalogService-0.0.1-SNAPSHOT.jar MovieCatalogService.jar
EXPOSE 9080
ENTRYPOINT [ "java" ,"-jar","MovieCatalogService.jar" ]
