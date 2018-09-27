package rs.ac.bg.imp.analytics.prediction.entities.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONException;
import org.json.JSONObject;
import rs.ac.bg.imp.analytics.prediction.entities.PredictionJob;
import rs.ac.bg.imp.analytics.prediction.matrixfactorization.PerformancePrediction;
import rs.ac.bg.imp.analytics.prediction.matrixfactorization.PerformancePredictionResult;
import rs.ac.bg.imp.analytics.connector.ActivitiesServiceConnector;
//import rs.ac.bg.imp.analytics.connector.DBConnector;
//import javax.persistence.Query;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaDelete;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Order;
//import javax.persistence.criteria.Root;
//import rs.ac.bg.imp.analytics.prediction.matrixfactorization.PredictionDummy;

/**
 *
 * @author Dejan
 */
@javax.ejb.Stateless
@Path("predictionjob")
public class PredictionJobFacadeREST extends AbstractFacade<PredictionJob> {

//    @PersistenceContext(unitName = "AnalyticsPU")
    private EntityManager em;

    public PredictionJobFacadeREST() {
        super(PredictionJob.class);
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PredictionJob create(String entity) {
//        long id = 0;
        try {
            JSONObject jsonObject = new JSONObject(entity);
            String userId = jsonObject.get("user_id").toString();
            String deckId = jsonObject.get("deck_id").toString();
            String jwt = jsonObject.get("jwt").toString();
            boolean useDummyData = jsonObject.getBoolean("dummy");
            //{"user_id": 16, "deck_id": 5, "dummy": true}
            
            PredictionJob pj = new PredictionJob();
            pj.setUserId(userId);
            pj.setDeckId(deckId);
            pj.setStarted(new Date());
            
//            em = getEntityManager();
//            em.getTransaction().begin();
//            em.persist(pj);
//            em.getTransaction().commit();
//            id = pj.getId();

//            id = DBConnector.savePredictionJob(userId, deckId);
            
            //SPARK PREDICTION
//            double result[] = PredictionDummy.getResults();




// CREATE A NEW THREAD FOR THIS?

            
            
            PerformancePredictionResult predictionResult = PerformancePrediction.runMatrixFactorization(userId, deckId, useDummyData);
                                                                                                                                     
            Integer pred_user = predictionResult.getPred_user();
            Integer pred_product = predictionResult.getPred_product();
            Double pred_rating = predictionResult.getPred_rating();
            Double pred_accuracy = predictionResult.getAccuracy();
            int noOfUsers = predictionResult.getNoOfUsers();
            int noOfDecks = predictionResult.getNoOfDecks();
            
//            System.out.println("User: " + pred_user);
//            System.out.println("Assessment: " + pred_product);
//            System.out.println("Prediction: " + pred_rating);
            
            
            

//            em = getEntityManager();
//            em.getTransaction().begin();
            pj.setFinished(new Date());
            pj.setResult(pred_rating);
            pj.setAccuracy(pred_accuracy);
            
            pj.setNoOfUsers(noOfUsers);
            pj.setNoOfDecks(noOfDecks);
            
            //CREATE NEW ACTIVITY
            ActivitiesServiceConnector.createActivity(pj, jwt);
            
            
            
//            pj.setAccuracy(pred_product);      //this is not accuracy
            
//            em.persist(pj);
//            em.getTransaction().commit();
            
            
//            DBConnector.updatePredictionJob(id, pred_product, pred_rating);
            
//            return findByUserId(userId);
            return pj;
//        } catch (JSONException ex) {
        } catch (JSONException | IOException ex) {
            Logger.getLogger(PredictionJobFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new PredictionJob();
    }

    @GET
    @Path("{userId}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<PredictionJob> findByUserId(@PathParam("userId") String userId) {
//        em = getEntityManager();
//        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//        CriteriaQuery cq = criteriaBuilder.createQuery();
//        Root predictionJob = cq.from(PredictionJob.class);
//        cq.where(criteriaBuilder.equal(predictionJob.get("userId"), userId));
//        //order by started
//        List<Order> orderList = new ArrayList();
//        orderList.add(criteriaBuilder.desc(predictionJob.get("started")));
//        cq.orderBy(orderList);
//
//        Query q = em.createQuery(cq);
//        List<PredictionJob> result = q.getResultList();
//        return result;

        return new ArrayList();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, PredictionJob entity) {
//        System.out.println("2222");
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
//        em = getEntityManager();
//        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//        
//        CriteriaDelete<PredictionJob> cd = criteriaBuilder.createCriteriaDelete(PredictionJob.class);
//        Root<PredictionJob> predictionJob = cd.from(PredictionJob.class);
//        cd.where(criteriaBuilder.equal(predictionJob.get("id"), id));
//        em.getTransaction().begin();
//        int result = em.createQuery(cd).executeUpdate();
//        em.getTransaction().commit();
        
//        super.remove(super.find(id));
    }
    
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<PredictionJob> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<PredictionJob> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        em = Persistence.createEntityManagerFactory("AnalyticsPU").createEntityManager(); 
        return em;
    }
    
}
