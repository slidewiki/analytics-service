package rs.ac.bg.imp.analytics.recommender.knn;

import rs.ac.bg.imp.analytics.connector.ActivitiesServiceConnector;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

//import imp.bg.ac.rs.KNNrecomm.UserFeatures.LikedDecks;

//Recommendation based on user preference ("likes")
public class Recommender {
//	
//	
//	// UserSample as a set of user features (e.g. likes per decks) 
//	public static class UserSample {
//		String userID;
//		// User preference data
//		double [] like_features; // likes (Boolean) per decks
//		// User engagement data
//		// ...
//		// User demographic data
//		// ...
//	}
//	
//	
//	// Finding positions of closest neighbors
//	static int[] indexesOfTopElements(double[] orig, int k) {
//        double[] copy = Arrays.copyOf(orig, orig.length);
//        Arrays.sort(copy); // sorted array
//        double[] honey = Arrays.copyOfRange(copy,0,k); // array of k closest neighbors 
//        
//        int[] resultingIndexes = new int[k];
//        int resultPos = 0;
//        for(int i = 0; i < orig.length; i++) {
//        	if(resultPos==k) break;
//            double onTrial = orig[i];
//            int index = Arrays.binarySearch(honey,onTrial); // search for second arg.(onTrial) in the first arg.(honey)
//            
//            if (index < 0) 
//            	continue;
//            else {
//            	resultingIndexes[resultPos] = i;
//            	resultPos++;
//            }
//        }
//        return resultingIndexes; //indexes of closest neighbors
//    }
//	
//	 
//	// samples classifying  
//	private static List<UserSample> findClosestNeighbors(List<UserSample> userFeatureMatrix, double[] targetLikeFeatures, int k) {
//		List<UserSample> kClosestUsers = new ArrayList<UserSample>();
//		double[] distances = new double[userFeatureMatrix.size()];
//		
//		int i = 0;
//		for(UserSample sample: userFeatureMatrix) {
//			double dist = distance(sample.like_features, targetLikeFeatures);
//			distances[i] = dist;
//			i++;
//		}
//		
//		int[] resultingIndexes = indexesOfTopElements(distances,k);
//		for(int j=0; j<k; j++) {
//			kClosestUsers.add(userFeatureMatrix.get(resultingIndexes[j]));
//		}
//		return kClosestUsers;
//	}
//	
//	// Calculate recommended decks
//	private static TreeMap<String, Integer> calculateRecommendation(List<LikedDecks> listOfLickedDecks) {
//		List<String> listOfDecks = new ArrayList<String>();
//		List<Integer> count = new ArrayList<Integer>();
//		for (LikedDecks decks: listOfLickedDecks) {
//			for (String d: decks.likedDecks) {
//				if (listOfDecks.contains(d)) {
//					count.set(listOfDecks.indexOf(d), count.get(listOfDecks.indexOf(d))+1);
//				} else {
//					listOfDecks.add(d);
//					count.add(listOfDecks.size()-1, 1);
//				}
//			}
//		}
//
//		// sort listOfDecks according to count
//		HashMap<String, Integer> map = new HashMap<String, Integer>();
//		for (int i = 0; i<listOfDecks.size(); i++) {
//			map.put(listOfDecks.get(i), count.get(i));
//		}
//		TreeMap<String, Integer> sortedMap = sortMapByValue(map);
//		
//		return sortedMap;
//	}
//	
//	public static TreeMap<String, Integer> sortMapByValue(HashMap<String, Integer> map){
//		Comparator<String> comparator = new ValueComparator(map);
//		//TreeMap is a map sorted by its keys. 
//		//The comparator is used to sort the TreeMap by keys. 
//		TreeMap<String, Integer> result = new TreeMap<String, Integer>(comparator);
//		result.putAll(map);
//		return result;
//	}
	
	
	
	
	
	
	
	
	
	
    // Calculating distance
    private static double distance(double[] a, double[] b) {
        double sum = 0;
        for(int i = 0; i < b.length; i++) {
            sum += (a[i] - b[i]) * (a[i] - b[i]);
        }
        return (double)Math.sqrt(sum); // Euclidean distance...
    }

    private static HashMap<String, HashMap<String, Double>> getUsersWhoAlsoLikedDecks(HashMap<String, Double> likedDecks){
        //Create HashMap to represent matrix
        HashMap<String, HashMap<String, Double>> usersWhoAlsoLikedDecks = new HashMap<>();
        for (String deck: likedDecks.keySet()) {
            List<String> listOfUsersWhoLikedDeck = ActivitiesServiceConnector.getlistOfUsersWhoLikedDeck(deck);
            for (String user: listOfUsersWhoLikedDeck) {
                HashMap<String,Double> deckLikes = usersWhoAlsoLikedDecks.get(user);
                if (deckLikes == null) {
                    deckLikes = new HashMap<>();
                    usersWhoAlsoLikedDecks.put(user, deckLikes);
                }
                deckLikes.put(deck, 1.0);
            }
        }

        return usersWhoAlsoLikedDecks;
    }

    private static TreeMap<Double, List<String>> getSortedUserScores(HashMap<String, Double> likedDecks,
                HashMap<String, HashMap<String, Double>> usersWhoAlsoLikedDecks) {
        //Score and sort by nearest users (TreeMap does the sorting)
        TreeMap<Double, List<String>> userScores = new TreeMap<>();//(score, arraylist of users with that score

        int likesSize = likedDecks.size();
        double [] targetValues = new double[likesSize];
        int i = 0;
        for (String deck: likedDecks.keySet()) {
            targetValues[i++] = likedDecks.get(deck);
        }
        for (String user: usersWhoAlsoLikedDecks.keySet()) {
            HashMap<String, Double> userLikedDecks = usersWhoAlsoLikedDecks.get(user);

            double [] currentValues = new double[likesSize];
            int j = 0;
            for (String deck: likedDecks.keySet()) {
                Double like = userLikedDecks.get(deck);
                currentValues[j++] = (like != null) ? like : 0.0;
            }
            Double distance = distance(targetValues, currentValues);
            List<String> usersWithDistance = userScores.get(distance);
            if (usersWithDistance == null) {
                usersWithDistance = new ArrayList<>();
            }
            usersWithDistance.add(user);
            userScores.put(distance(targetValues, currentValues), usersWithDistance);
        }

        return userScores;
    }

    private static TreeMap<Double, List<String>> getRecommendedDecks(HashMap<String, Double> likedDecks,
                TreeMap<Double, List<String>> userScores, int noOfUsers){
        TreeMap<Double, List<String>> recommendedDecks = new TreeMap<>();
        HashMap<String, Double> weightPerDeck = new HashMap<>();
        for (Double distance: userScores.keySet()) {
            List<String> users = userScores.get(distance);
            double currentUserWeight = 1.0;
            //double weight = 1 / distance; //Maybe use 1/distance 
            for (String user: users) {
                if (noOfUsers-- == 0) {
                    break;
                }
                //get liked decks for the current user
                List<String> currentUserLikedDecks = ActivitiesServiceConnector.getLikedDecks(user);
                //check if it's been already liked by the target user
                for (String deck: currentUserLikedDecks) {
                    if (likedDecks.get(deck) == null) {
                        //if not, add it to the HashMap
                        Double weightForTheCurrentDeck = weightPerDeck.get(deck);
                        if (weightForTheCurrentDeck == null) {
                            weightForTheCurrentDeck = 0.0;
                        } 
                        weightForTheCurrentDeck = weightForTheCurrentDeck + currentUserWeight;
                        
                        weightPerDeck.put(deck, weightForTheCurrentDeck);
                    }
                }

            }
            if (noOfUsers-- == 0) {
                break;
            }
        }

        //Create TreeMap <Integer, List<String>> to represent list of decks per no of likes
        for (String deck: weightPerDeck.keySet()) {
            Double weightForTheCurrentDeck = weightPerDeck.get(deck);

            List<String> decksWithWeight = recommendedDecks.get(weightForTheCurrentDeck);
            if (decksWithWeight == null) {
                decksWithWeight = new ArrayList<>();
            }
            decksWithWeight.add(deck);
            recommendedDecks.put(weightForTheCurrentDeck, decksWithWeight);
        }

        return recommendedDecks;
    }
    
    private static List<JSONObject> getListOfJSONObjects(TreeMap<Double, List<String>> recommendedDecks) {
        List<JSONObject> listOfJSONObjects = new ArrayList<>();
        for (Double weight : recommendedDecks.keySet()) {
            List<String> decks = recommendedDecks.get(weight);
            for (String deck : decks) {
                try {
                    listOfJSONObjects.add(new JSONObject().put("deckid", deck).put("weight", weight));
                } catch (JSONException ex) {
                    Logger.getLogger(Recommender.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Collections.reverse(listOfJSONObjects);//order by weight (descended)
        return listOfJSONObjects;
    }

    public static List<JSONObject> getRecommendations(String userId) throws IOException  {

        // Fetch relevant user features (normalized)
        UserFeatures userFeatures = new UserFeatures(userId); // target user ID as args[0]

        HashMap<String, Double> likedDecks = userFeatures.getMapOfLikedDecks();

        HashMap<String, HashMap<String, Double>> usersWhoAlsoLikedDecks = getUsersWhoAlsoLikedDecks(likedDecks);

        TreeMap<Double, List<String>> userScores = getSortedUserScores(likedDecks, usersWhoAlsoLikedDecks);

        int numberOfUsers = 10;
        
        TreeMap<Double, List<String>> recommendedDecks = getRecommendedDecks(likedDecks, userScores, numberOfUsers);

//        System.out.println("recommendedDecks " + recommendedDecks.values());
        return getListOfJSONObjects(recommendedDecks);











//		List<UserSample> userFeatureMatrix = UserFeatures.getUserFeatureMatrix(); // kNN table of related users (without a target one)
//		UserSample targetUserSample = UserFeatures.getTargetUserSample(); // target user features
//
//		// Identify K closest users
//		int k = 10; // k number of closest neighbors
//		List<UserSample> kClosestUsers = findClosestNeighbors(userFeatureMatrix, targetUserSample.like_features, k);
//		
//		// Calculate deck recommendations
//		List<LikedDecks> listOfLikedDecks = userFeatures.getLikedDecks(kClosestUsers);
//		TreeMap<String, Integer> sortedDecks = calculateRecommendation(listOfLikedDecks);
//		
//		for(Map.Entry<String,Integer> entry : sortedDecks.entrySet()) {
//			  String key = entry.getKey();
//			  Integer value = entry.getValue();
//
//			  System.out.println(key + " => " + value);
//			}
    }
}
