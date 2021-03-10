FROM openjdk:11
LABEL version="1.0.0" description="Desafio Proposta Zup" maintainer="Jackson Alves"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV URL_ANALISE=http://analise:9999/api
ENV URL_CARTAO=http://contas:8888/api
ENV DB_URL=postgres
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app.jar"]