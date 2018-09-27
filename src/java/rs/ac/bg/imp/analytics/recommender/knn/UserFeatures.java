package rs.ac.bg.imp.analytics.recommender.knn;

import rs.ac.bg.imp.analytics.connector.ActivitiesServiceConnector;
import java.util.HashMap;

//import imp.bg.ac.rs.KNNrecomm.Recommender.UserSample;

public class UserFeatures {
	
//	private static List<String> listOfLikedDecks = new ArrayList<String>(); // decks liked by target user
//	private static List<String> listOfVisitedDecks = new ArrayList<String>(); // decks visited by target user
//	private static UserSample targetUserSample = new UserSample();
//	private static List<UserSample> userFeatureMatrix = new ArrayList<UserSample>();	
//	
//	// UserSample as a set of user features (e.g. likes per decks) 
//	public static class LikedDecks {
//		String userID;
//		String [] likedDecks; // decks liked by user
//	}
//	
//	// Fetching the target user features from LRS
//	private static UserSample getTargetUserFeatures(String userID) {
//		// set listOfLikedDecks
////		listOfLikedDecks = null;
//		// TODO: ...
//		
//		// set listOfVisitedDecks
//		listOfVisitedDecks = null;
//		// TODO: ...
//		
//		UserSample targetUserSample = new UserSample();
//		// Code for fetching the target user features from LRS
//		// TODO: ...
//		return targetUserSample;
//	}
//	
//	// Fetching the user feature matrix from LRS (based on listOfLikedDecks)
//	private static List<UserSample> calculateUserFeatureMatrix() {
//		List<UserSample> userFeatureMatrix = new ArrayList<UserSample>();
//		// Code for fetching the user features from LRS
//		// TODO: ...
//		return userFeatureMatrix;
//	}
//	
//	// Normalization of user features
//	private static void featureNormalization(List<UserSample> listOfUsers, UserSample targetUser) {
//		// Normalization to [0-1]
//		// could be deployed later on
//		targetUserSample = targetUser;
//		userFeatureMatrix = listOfUsers;
//	}
//	
//	public List<LikedDecks> getLikedDecks(List<UserSample> listOfClosestUsers) {
//		// fetch decks liked by the closest neighbors
//		// TODO: ...
//		List<LikedDecks> listOfDecks = null;
//		return listOfDecks;
//	}
	
	
	
	
	
	
	
	
	
	
	

    private String userId;

    private HashMap<String, Double> mapOfLikedDecks = new HashMap<>();


    public UserFeatures(String userId) {
        this.userId = userId;

        this.mapOfLikedDecks = ActivitiesServiceConnector.getMapOfLikedDecks(userId);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public HashMap<String, Double> getMapOfLikedDecks() {
        return mapOfLikedDecks;
    }

    public void setMapOfLikedDecks(HashMap<String, Double> listOfLikedDecks) {
        this.mapOfLikedDecks = listOfLikedDecks;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public UserFeatures() {
//		
//
//		// Fetching the use features
//		UserSample targetUserSample_t = getTargetUserFeatures(userId); // target user ID as args[0]
//		List<UserSample> userFeatureMatrix_t = calculateUserFeatureMatrix(); 
//			
//		// Normalization of user features
//		featureNormalization(userFeatureMatrix_t, targetUserSample_t);
//	}
//
//	public static UserSample getTargetUserSample() {
//		return targetUserSample;
//	}
//
//	public static List<UserSample> getUserFeatureMatrix() {
//		return userFeatureMatrix;
//	}
	
}