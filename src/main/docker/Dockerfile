FROM java:8-jdk-alpine
RUN ls -ltr
COPY sampleproject-api*.jar app.jar
RUN sh -c 'touch app.jar'
ENTRYPOINT ["java","-jar","app.jar"]
