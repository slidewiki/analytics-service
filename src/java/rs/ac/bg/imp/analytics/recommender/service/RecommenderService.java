package rs.ac.bg.imp.analytics.recommender.service;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

import rs.ac.bg.imp.analytics.recommender.knn.Recommender;

/**
 * REST Web Service
 *
 * @author Dejan
 */
@Path("recommender")
public class RecommenderService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RecommenderService
     */
    public RecommenderService() {
    }

    /**
     * Retrieves representation of an instance of recommender.RecommenderService
     * @param userId
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@QueryParam("userid") int userId) {
        try {
            List<JSONObject> listOfJSONRecommendations = Recommender.getRecommendations(String.valueOf(userId));
//            JSONObject[] arrayOfJSONRecommendations = listOfJSONRecommendations.toArray(new JSONObject[listOfJSONRecommendations.size()]);
//            String jsonList = arrayOfJSONRecommendations.toString();
//            String jsonList = "[" + "{\"text\": \"Hello World!! userid=" + userId + "\"}" + ",";
//            jsonList += "{\"text\": \"Bonjour monde!! c=" + 0 + "\"}" + "]";
//            return jsonList;
            return listOfJSONRecommendations.toString();
        } catch (IOException ex) {
            Logger.getLogger(RecommenderService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "[]";
    }

    /**
     * PUT method for updating or creating an instance of RecommenderService
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putXml(String content) {
    }
}
