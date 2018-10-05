FROM tomcat:8-jre8

MAINTAINER "Dejan Paunovic <dejan.paunovic@institutepupin.com>"

ENV SERVICE_URL_ACTIVITIES https://activitiesservice.experimental.slidewiki.org
# ENV SERVICE_URL_ACTIVITIES http://172.17.42.1:5000

ADD ./build/web /usr/local/tomcat/webapps/analytics
RUN mkdir /home/prediction
ADD ./temp_test.txt /home/prediction/temp_test.txt

EXPOSE 8080

CMD chmod +x /usr/local/tomcat/bin/catalina.sh

CMD ["catalina.sh", "run"]
