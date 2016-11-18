FROM java:8

MAINTAINER Ivo Woltring <webmaster@ivo2u.nl>

ENV profile prod

ADD /ivo2u-tiny.jar //
RUN mkdir /config
RUN chmod 777 /config
VOLUME /config

ENTRYPOINT ["java", "-Dspring.profiles.active=${profile}", "-jar" ,"/ivo2u-tiny.jar"]
