package rs.ac.bg.imp.analytics.prediction.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-01T15:22:06")
@StaticMetamodel(PredictionJob.class)
public class PredictionJob_ { 

    public static volatile SingularAttribute<PredictionJob, Double> result;
    public static volatile SingularAttribute<PredictionJob, Integer> noOfDecks;
    public static volatile SingularAttribute<PredictionJob, String> deckId;
    public static volatile SingularAttribute<PredictionJob, Integer> noOfUsers;
    public static volatile SingularAttribute<PredictionJob, Double> accuracy;
    public static volatile SingularAttribute<PredictionJob, Date> started;
    public static volatile SingularAttribute<PredictionJob, Date> finished;
    public static volatile SingularAttribute<PredictionJob, Integer> id;
    public static volatile SingularAttribute<PredictionJob, String> userId;
    public static volatile SingularAttribute<PredictionJob, String> relatedPredictionActivityId;

}