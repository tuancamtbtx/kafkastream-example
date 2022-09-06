# stage build
FROM maven:3.6.0-jdk-8-slim AS target
WORKDIR /build

# ARG GITHUB_USERNAME
# ARG GITHUB_TOKEN
# ENV GITHUB_USERNAME="$GITHUB_USERNAME"
# ENV GITHUB_TOKEN="$GITHUB_TOKEN"
# RUN echo $GITHUB_USERNAME
# RUN echo $GITHUB_TOKEN

 # copy source code to /build/src
COPY src /build/src
 # copy maven file to /build
COPY pom.xml /build
COPY settings.xml /build
 # build java with maven

RUN mvn -f /build/pom.xml clean package

#stage install scylla

# stage package
FROM adoptopenjdk/openjdk8:jre
WORKDIR /usr/local/runme


 ## run in stating and production
 # copy jar file
 # copy target build/target/tiki-smarter-model-worker-*.jar to /usr/local/runme/app.jar
COPY --from=target build/target/ilotusland-streaming-*.jar app.jar
 # copy project dependencies
 # cp -rf /build/target/lib/  /usr/local/runme/lib
COPY --from=target build/target/lib lib/

# run entrypoint
EXPOSE 9090
ENTRYPOINT ["java", "-Xmx8G","-XshowSettings:vm", "-XX:NativeMemoryTracking=summary", "-jar", "app.jar"]

