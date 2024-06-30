FROM openjdk:17-jdk-slim

WORKDIR /app

RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

COPY pom.xml .

COPY src ./src

RUN mvn clean package

CMD ["java", "-jar", "target/nome-do-seu-arquivo-jar.jar"]
