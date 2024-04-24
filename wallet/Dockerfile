#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

#information around who maintains the image
MAINTAINER david.mbaimbai.co.za

#Add the application's jar to the image
COPY target/wallet-0.0.1-SNAPSHOT.jar wallet-0.0.1-SNAPSHOT.jar
RUN mvn clean package -Dskiptests
EXPOSE 8080
#execute the application
ENTRYPOINT ["java", "-jar", "wallet-0.0.1-SNAPSHOT.jar"]
