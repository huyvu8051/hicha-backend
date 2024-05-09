
FROM karluto/jdk21-apline3.18
COPY hicha-business/target/hicha-business-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]