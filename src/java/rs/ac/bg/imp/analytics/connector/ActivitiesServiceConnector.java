package rs.ac.bg.imp.analytics.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import rs.ac.bg.imp.analytics.prediction.entities.PredictionJob;

public class ActivitiesServiceConnector {
//    public static String activitiesServiceUrl = System.getenv("SERVICE_URL_ACTIVITIES");
    public static String activitiesServiceUrl = "https://activitiesservice.experimental.slidewiki.org";
    
    public static void createActivity(PredictionJob pj, String jwt) {
        String url = activitiesServiceUrl + "/activity/new";
        
        try {
            URL object = new URL(url);

            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("----jwt----", jwt);
            con.setRequestMethod("POST");

            JSONObject data = new JSONObject();
            JSONObject prediction_info = new JSONObject();
            data.put("activity_type", "prediction");
            data.put("user_id", pj.getUserId());
            data.put("content_id", pj.getDeckId());
            data.put("content_kind", "deck");
            prediction_info.put("prediction_activity_type", "end");
            prediction_info.put("related_prediction_activity_id", pj.getRelatedPredictionActivityId());
            prediction_info.put("result", pj.getResult().toString());
            prediction_info.put("accuracy", pj.getAccuracy().toString());
            prediction_info.put("no_of_users", pj.getNoOfUsers().toString());
            prediction_info.put("no_of_decks", pj.getNoOfDecks().toString());
            data.put("prediction_info", prediction_info);

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(data.toString());
            wr.flush();

            //display what returns the POST request
            StringBuilder sb = new StringBuilder();  
            int HttpResult = con.getResponseCode(); 
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line = null;  
                while ((line = br.readLine()) != null) {  
                    sb.append(line + "\n");  
                }
                br.close();
                System.out.println("" + sb.toString());  
            } else {
                System.out.println(con.getResponseMessage());  
            } 
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static List<String> getLikedDecks(String userId) {
//        System.out.println("getLikedDecks " + userId);
        List<String> listOfLikedDecks = new ArrayList<>();
        String url = activitiesServiceUrl + "/activities/user/" + userId + "?metaonly=false&activity_type=react";
        InputStream response;
        try {
            response = new URL(url).openStream();

            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray jsonarray = new JSONArray(jsonObject.get("items").toString());
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String deckId = jsonobject.getString("content_id").split("-")[0];//remove revision number
                listOfLikedDecks.add(deckId);
//                System.out.println("deckId = " + deckId);
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//System.out.println(listOfLikedDecks);
        return listOfLikedDecks;
    }

    public static HashMap<String, Double> getMapOfLikedDecks(String userId) {
        HashMap<String, Double> mapOfLikedDecks = new HashMap<>();
        List<String> listOfLikedDecks = getLikedDecks(userId);
        for (String deck: listOfLikedDecks) {
            mapOfLikedDecks.put(deck, 1.0);
        }
        return mapOfLikedDecks;
    }

    public static List<String> getlistOfUsersWhoLikedDeck(String deckId) {
//        System.out.println("getlistOfUsersWhoLikedDeck: " + deckId);
        List<String> listOfUsersWhoLikedDeck = new ArrayList<>();
        
        String url = activitiesServiceUrl + "/activities/deck/" + deckId + "?metaonly=false&all_revisions=true&activity_type=react";
        InputStream response;
        try {
            response = new URL(url).openStream();

            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray jsonarray = new JSONArray(jsonObject.get("items").toString());
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String userId = jsonobject.getString("user_id");
                listOfUsersWhoLikedDeck.add(userId);
//                System.out.println("user id = " + userId);
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//System.out.println(listOfUsersWhoLikedDeck);
        return listOfUsersWhoLikedDeck;

    }

    public static HashMap<String, String> getMapOfExamsForUser(String userId) {
        HashMap<String, String> mapOfExams = new HashMap<>();
        
        String url = activitiesServiceUrl + "/activities/user/" + userId + "?metaonly=false&activity_type=exam";
        InputStream response;
        try {
            response = new URL(url).openStream();

            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray jsonarray = new JSONArray(jsonObject.get("items").toString());
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String deckId = jsonobject.getString("content_id").split("-")[0];//remove revision number
                JSONObject examInfo = jsonobject.getJSONObject("exam_info");
                String score = String.valueOf(examInfo.getInt("score"));
                mapOfExams.put(deckId, score);
//                System.out.println("deckId = " + deckId);
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return mapOfExams;
    }
    
    public static HashMap<String, String> getMapOfExamsForDeck(String deckId) {
        HashMap<String, String> mapOfExams = new HashMap<>();
        
        String url = activitiesServiceUrl + "/activities/deck/" + deckId + "?metaonly=false&activity_type=exam";
        InputStream response;
        try {
            response = new URL(url).openStream();

            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray jsonarray = new JSONArray(jsonObject.get("items").toString());
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String userId = jsonobject.getString("user_id");
                JSONObject examInfo = jsonobject.getJSONObject("exam_info");
                String score = String.valueOf(examInfo.getInt("score"));
                mapOfExams.put(userId, score);
//                System.out.println("deckId = " + deckId);
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return mapOfExams;
    }

    private static List<String> getMockupListOfLikedDecks(String userId) {
        List<String> listOfLikedDecks = new ArrayList<>();
        switch(userId) {
        case "1":
            listOfLikedDecks.add("1234");
            listOfLikedDecks.add("5678");
            listOfLikedDecks.add("6789");
            break;
        case "2":
            listOfLikedDecks.add("2345");
            listOfLikedDecks.add("3456");
            listOfLikedDecks.add("5678");
            break;
        case "3":
            listOfLikedDecks.add("1234");
            listOfLikedDecks.add("2345");
            listOfLikedDecks.add("3456");
            break;
        case "4":
            listOfLikedDecks.add("1234");
            listOfLikedDecks.add("2345");
            listOfLikedDecks.add("3456");
            listOfLikedDecks.add("4567");
            break;
        }
        return listOfLikedDecks;
    }

    private static List<String> getMockupListOfUsersWhoLikedDeck(String deckId) {
        List<String> listOfUsersWhoLikedDeck = new ArrayList<>();
        switch(deckId) {
        case "1234":
            listOfUsersWhoLikedDeck.add("1");
            listOfUsersWhoLikedDeck.add("3");
            listOfUsersWhoLikedDeck.add("4");
            break;
        case "2345":
            listOfUsersWhoLikedDeck.add("2");
            listOfUsersWhoLikedDeck.add("3");
            listOfUsersWhoLikedDeck.add("4");
            break;
        case "3456":
            listOfUsersWhoLikedDeck.add("2");
            listOfUsersWhoLikedDeck.add("3");
            listOfUsersWhoLikedDeck.add("4");
            break;
        case "4567":
            listOfUsersWhoLikedDeck.add("4");
            break;
        case "5678":
            listOfUsersWhoLikedDeck.add("1");
            listOfUsersWhoLikedDeck.add("2");
            break;
        case "6789":
            listOfUsersWhoLikedDeck.add("1");
            break;
        }
        return listOfUsersWhoLikedDeck;
    }
}
