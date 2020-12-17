FROM tomcat:9.0.41-jdk8
MAINTAINER "Junior Team"
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8001
COPY build/libs/supplier-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/supplier.war