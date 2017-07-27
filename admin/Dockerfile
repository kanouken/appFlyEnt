# AlpineLinux open jre 8
FROM alpine:latest
# Install cURL
RUN echo -e "https://mirror.tuna.tsinghua.edu.cn/alpine/v3.4/main\n\
https://mirror.tuna.tsinghua.edu.cn/alpine/v3.4/community" > /etc/apk/repositories
RUN apk --update add curl bash openjdk8-jre-base && \
      rm -rf /var/cache/apk/*
# Set environment
ENV JAVA_HOME /usr/lib/jvm/default-jvm
ENV PATH ${PATH}:${JAVA_HOME}/bin
ENV	PROFILE pro
ENV JAVA_OPTS="-server -Xmx500M -Xms500M"
COPY admin/target/*.jar  /app.jar
RUN bash -c 'touch /app.jar'
VOLUME /tmp
EXPOSE 8002
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=${PROFILE}"]