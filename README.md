[![License](https://img.shields.io/badge/License-MPL%202.0-green.svg)](https://github.com/slidewiki/microservice-template/blob/master/LICENSE)
# analytics-service
Manages Student Performance Prediction. Manages user based deck recommendations.

Usage:

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
  
