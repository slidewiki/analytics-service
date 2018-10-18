# Analytics service

[![License](https://img.shields.io/badge/License-MPL%202.0-green.svg)](https://github.com/slidewiki/microservice-template/blob/master/LICENSE)

Analytics service using RESTful API based on JAX-RS. Manages Student Performance Prediction and User-based Deck Recommendations.

### How to install and run with docker

1. git clone https://github.com/slidewiki/analytics-service
2. cd analytics-service/
3. (if needed) Change corresponding server name in SERVICE_URL_ACTIVITIES inside the Dockerfile 
4. (sudo) docker build -t test-analytics-service .
5. (sudo) docker run -it --rm -p 80:8080 test-analytics-service 
6. The service will be available at http://localhost/analytics 

### How to install and run without docker

1. git clone https://github.com/slidewiki/analytics-service
2. cd analytics-service/
3. Install Java 1.8 ( sudo add-apt-repository ppa:webupd8team/java && sudo apt-get update && sudo apt-get install oracle-java8-installer )
4. Install Tomcat 8.0.27.0 ( sudo apt-get install tomcat8 tomcat8-docs tomcat8-admin )
5. Deploy analytics service to Tomcat ( copy /build/web to /usr/local/tomcat/webapps/analytics )
6. Create directory /home/prediction and copy temp_test.txt to it ( allow read permissions )
7. Create environment variable SERVICE_URL_ACTIVITIES = https://activitiesservice.experimental.slidewiki.org
8. Run the Tomcat server
9. The service will be available at localhost:8080/analytics


### Usage:

GET /analytics/webresources/recommender

  query parameters:
  
      userid - id of the user
      
POST /analytics/webresources/predictionjob

  body:
  
      {
      
          related_prediction_activity_id (string),
          
          user_id (string),
          
          deck_id (string),
          
          jwt (string),
          
          dummy (boolean)
          
      }
  
