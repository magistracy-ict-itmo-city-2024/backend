FROM azul/zulu-openjdk:17.0.12-jre

COPY ./CA.pem /root/.postgresql/root.crt

RUN chmod 0600 /root/.postgresql/root.crt

ENV CITY_LOCAL_FILES_STORAGE_ROOT=/data/media

COPY server/standalone/build/libs/standalone-0.0.1-SNAPSHOT.jar /application.jar

CMD java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar /application.jar