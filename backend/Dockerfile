FROM adoptopenjdk/openjdk10-openj9

WORKDIR /app

RUN mkdir /opt/app

RUN mkdir /opt/cache

WORKDIR /opt/app

COPY . /opt/app

VOLUME /opt/app/.gradle/

VOLUME /opt/app/gradle

ENV GRADLE_USER_HOME /opt/cache
VOLUME $GRADLE_USER_HOME

CMD ["./gradlew", "bootRun"]