FROM openjdk:17-oracle
ADD build/libs/spribe-1.0-SNAPSHOT.jar spribe.jar
ENTRYPOINT ["java", "-jar", "/spribe.jar"]