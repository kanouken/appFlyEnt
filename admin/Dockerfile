# AlpineLinux open jre 8
FROM alpine:latest
# Install cURL
RUN echo -e "https://mirror.tuna.tsinghua.edu.cn/alpine/v3.4/main\n\
https://mirror.tuna.tsinghua.edu.cn/alpine/v3.4/community" > /etc/apk/repositories
RUN apk --update add curl bash openjdk8-jre fontconfig ttf-dejavu && \
      rm -rf /var/cache/apk/*
# Set environment
ENV JAVA_HOME /usr/lib/jvm/default-jvm
ENV PATH ${PATH}:${JAVA_HOME}/bin
COPY target/*.jar  /app.jar
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
RUN bash -c 'touch /app.jar'
VOLUME /data
EXPOSE 8000
ENTRYPOINT ["/entrypoint.sh"]