package rs.ac.bg.imp.analytics.recommender.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Dejan
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(rs.ac.bg.imp.analytics.prediction.entities.service.PredictionJobFacadeREST.class);
        resources.add(rs.ac.bg.imp.analytics.recommender.service.RecommenderService.class);
    }
    
}
