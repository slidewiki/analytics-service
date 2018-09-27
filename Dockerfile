FROM tomcat:8-jre8

MAINTAINER "Dejan Paunovic <dejan.paunovic@institutepupin.com>"

ENV SERVICE_URL_ACTIVITIES https://activitiesservice.experimental.slidewiki.org





#RUN echo "export JAVA_OPTS=\"-Dapp.env=staging\"" > /usr/local/tomcat/bin/setenv.sh







ADD ./Analytics.war /usr/local/tomcat/webapps/analytics.war
RUN mkdir /home/prediction
ADD ./temp_test.txt /home/prediction/temp_test.txt
#ADD server.xml /usr/local/tomcat/conf/
EXPOSE 8080
CMD chmod +x /usr/local/tomcat/bin/catalina.sh




CMD ["catalina.sh", "run"]
