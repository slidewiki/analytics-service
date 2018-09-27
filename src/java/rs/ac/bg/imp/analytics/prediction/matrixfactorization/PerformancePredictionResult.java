package rs.ac.bg.imp.analytics.prediction.matrixfactorization;

/**
 *
 * @author Dejan
 */
public class PerformancePredictionResult {
    private Integer pred_user = 0;
    private Integer pred_product = 0;
    private Double pred_rating = 0.0;
    private int noOfUsers = 0;
    private int noOfDecks = 0;
    private Double accuracy = 0.0;
    
    public PerformancePredictionResult(String result) {
        String[] stringResult = result.split(",");
        
        String result_1 = stringResult[0].replaceAll("[^\\d.]","");
        String result_2 = stringResult[1].replaceAll("[^\\d.]","");
        String result_3 = stringResult[2].replaceAll("[^\\d.]","");
        
        pred_user = Integer.parseInt(result_1);
        pred_product = Integer.parseInt(result_2);
        pred_rating = Double.parseDouble(result_3);
    }

    public PerformancePredictionResult() {
        pred_user = 0;
        pred_product = 0;
        pred_rating = 0.0;
        noOfUsers = 0;
        noOfDecks = 0;
        accuracy = 0.0;
    }
    
    
    public Integer getPred_user() {
        return pred_user;
    }

    public void setPred_user(Integer pred_user) {
        this.pred_user = pred_user;
    }

    public Integer getPred_product() {
        return pred_product;
    }

    public void setPred_product(Integer pred_product) {
        this.pred_product = pred_product;
    }

    public Double getPred_rating() {
        return pred_rating;
    }

    public void setPred_rating(Double pred_rating) {
        this.pred_rating = pred_rating;
    }

    public int getNoOfUsers() {
        return noOfUsers;
    }

    public void setNoOfUsers(int noOfUsers) {
        this.noOfUsers = noOfUsers;
    }

    public int getNoOfDecks() {
        return noOfDecks;
    }

    public void setNoOfDecks(int noOfDecks) {
        this.noOfDecks = noOfDecks;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }
    
    
}
