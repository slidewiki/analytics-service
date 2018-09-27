package rs.ac.bg.imp.analytics.prediction.matrixfactorization;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.evaluation.RegressionMetrics;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import rs.ac.bg.imp.analytics.config.Config;

import scala.Tuple2;

//Student performance prediction based on past exam results [SlideWiki]
public class PerformancePrediction {

    // Run Matrix Factorization via Spark's MLLIB and calculate RMSE
    public static PerformancePredictionResult runMatrixFactorization(String student_id, String deck_id, boolean useDummyData) throws IOException{

        if ("win".equals(Config.getProperty("OS"))) {
            System.setProperty("hadoop.home.dir", Config.getProperty("HADOOP_HOME_DIR_WIN"));
        }

        

        //Load the input data from local text files
        //String path_file="mat_fact_train_batch.txt";
        //String path_file="temp_test.txt";
        //String path_exam="mat_fact_test_batch.txt";
        //JavaRDD<String> data_train = sc.textFile(path_file);

        

        //Create and fetch the student exam matrix
        StudentExamMatrix studentExamMatrix = new StudentExamMatrix(student_id, deck_id);
        List<String> inputMatrix = studentExamMatrix.getInputMatrix();
        int noOfStudents = studentExamMatrix.getNoOfStudents();
        int noOfDecks = studentExamMatrix.getNoOfDecks();
        
//        System.out.println(inputMatrix);
        
        List predictions = inputMatrix.isEmpty() ? new ArrayList() : sparkIt(inputMatrix, student_id + "::" + deck_id + "::00");
        
        if (predictions.isEmpty() && useDummyData) {//Data is not valid - use demo data
            inputMatrix = studentExamMatrix.getDummyInputMatrix();//use testing data
            student_id = "632680";
            deck_id = "7";
            noOfStudents = 0;
            noOfDecks = 0;
            
            predictions = sparkIt(inputMatrix, "632680::7::00");
        
        };
        
        if (!predictions.isEmpty()) {
            PerformancePredictionResult predictionResult = new PerformancePredictionResult(predictions.toString());
            predictionResult.setNoOfUsers(noOfStudents);
            predictionResult.setNoOfDecks(noOfDecks);

            System.clearProperty("spark.driver.port");

            return predictionResult;
        } else {
            PerformancePredictionResult predictionResult = new PerformancePredictionResult();
            predictionResult.setAccuracy(-1.0);
            return predictionResult;
        }
    }
    
    private static List sparkIt(List<String> inputMatrix, String targetStudentString) {
        SparkConf conf = new SparkConf().setAppName("Matrix factorization"); // loads defaults from system properties and the classpath, set a name for application
        conf.setMaster("local"); // the master URL to connect to, such as "local" to run locally with one thread
        JavaSparkContext sc = new JavaSparkContext(conf); // create JavaSparkContext that loads settings from system properties
        
        //System.out.println(inputMatrix.toString());
        JavaRDD<String> data_train = sc.parallelize(inputMatrix);

        // map - Return a new RDD by applying a function to all elements of this RDD.
        JavaRDD<Rating> ratings_train = data_train.map(line -> {
                String[] parts = line.split("::");
                return new Rating(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Double.parseDouble(parts[2]));});

        // cache - Persist this RDD with the default storage level (`MEMORY_ONLY`)
        ratings_train.cache();

        // Train an ALS (alternating least squares) model
        // train - Train a matrix factorization model given an RDD of ratings given by users to some products, in the form of (userID, productID, rating) pairs. 
        // We approximate the ratings matrix as the product of two lower-rank matrices of a given rank (number of features). 
        // To solve for these features, we run a given number of iterations of ALS. 
        // The level of parallelism is determined automatically based on the number of partitions in ratings.
        // ratings - RDD of (userID, productID, rating) pairs
        // rank - number of features to use
        // iterations - number of iterations of ALS (recommended: 10-20)
        // lambda - regularization factor (recommended: 0.01)
        MatrixFactorizationModel model = ALS.train(JavaRDD.toRDD(ratings_train), 2, 10, 0.01);

        //JavaRDD<String> data_test = sc.textFile(path_exam);

        //TODO: fetch target student tuple in form of String: student::deck/exam::-1 (or actual score for validation)
        List<String> targetStudent = new ArrayList<>();
        
        
        targetStudent.add(targetStudentString);
        

        JavaRDD<String> data_test = sc.parallelize(targetStudent);

        JavaRDD<Rating> ratings_test = data_test.map(line -> {
                String[] parts = line.split("::");
                return new Rating(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Double.parseDouble(parts[2]));});

        ratings_test.cache();

        // Evaluate the model using numerical ratings and regression metrics
        JavaRDD<Tuple2<Object, Object>> test_features =
                        ratings_test.map(r -> new Tuple2<>(r.user(), r.product()));

        // Predicting performance on test data
        JavaPairRDD<Tuple2<Integer, Integer>, Object> predictions = JavaPairRDD.fromJavaRDD(
                        model.predict(JavaRDD.toRDD(test_features)).toJavaRDD().map(r ->
                        new Tuple2<>(new Tuple2<>(r.user(), r.product()), r.rating())));

        // RMSE as validation metric
        //JavaRDD<Tuple2<Object, Object>> ratesAndPreds =
        //		JavaPairRDD.fromJavaRDD(ratings_test.map(r ->
        //		new Tuple2<Tuple2<Integer, Integer>, Object>(
        //				new Tuple2<>(r.user(), r.product()),
        //		          r.rating()))).join(predictions).values();

        //RegressionMetrics regressionMetrics = new RegressionMetrics(ratesAndPreds.rdd());

        //double RMSE=regressionMetrics.rootMeanSquaredError();
        //System.out.format("RMSE = %f\n", RMSE);
        
        List collectedPredictions = predictions.collect();
        
        sc.stop();
        sc.close();
        
        return collectedPredictions;
    }
}