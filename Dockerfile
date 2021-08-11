FROM openjdk:8-jdk-alpine
COPY target/system_platform_k8s-1.0-SNAPSHOT.jar system_platform_k8s-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-Xms2048m","-Xmx2048m","-jar","-Duser.timezone=GMT+8","/system_platform_k8s-1.0-SNAPSHOT.jar"]