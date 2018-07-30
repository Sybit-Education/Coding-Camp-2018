FROM java:8

LABEL maintainer="Sybit GmbH <info@sybit.de>"

# Expose the port the app runs in
EXPOSE 8080

RUN mkdir /app

VOLUME /app/log

ADD ./build/libs/battleship-0.0.1-SNAPSHOT.war /app/app.war

WORKDIR /app

# RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=production", "app.war"]

# CMD java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=production -jar app.jar 
