FROM openjdk:11
ADD target/currency-converter.jar currency-converter.jar
#ADD target/dependency /dependency
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "currency-converter.jar"]