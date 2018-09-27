package rs.ac.bg.imp.analytics.prediction.matrixfactorization;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dejan
 */
public class PredictionDummy {
    public static double[] getResults() {
        double result[] = new double[2];
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(PredictionDummy.class.getName()).log(Level.SEVERE, null, ex);
        }
        Random rand = new Random();
        result[0] = rand.nextInt(100) + 1; 
        result[1] = rand.nextInt(100) + 1; 
        return result;
    }
    
}
