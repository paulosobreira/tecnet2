FROM tomcat:7.0.94-jre7-alpine
MAINTAINER Paulo Sobreira
ADD build/tecnet2.war /usr/local/tomcat/webapps/tecnet2.war
EXPOSE 8080