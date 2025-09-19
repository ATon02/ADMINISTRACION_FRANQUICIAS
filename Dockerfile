FROM openjdk:17-jdk-alpine

RUN addgroup -S appuser && adduser -S appuser -G appuser
VOLUME /tmp
COPY ./target/management.franchises-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
USER appuser
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar app.jar" ]