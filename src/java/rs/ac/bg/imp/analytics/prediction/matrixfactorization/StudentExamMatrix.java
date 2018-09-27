package rs.ac.bg.imp.analytics.prediction.matrixfactorization;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import rs.ac.bg.imp.analytics.config.Config;
import rs.ac.bg.imp.analytics.connector.ActivitiesServiceConnector;

class StudentExamMatrix {
	
    private List<String> inputMatrix = new ArrayList<>();
    private int noOfStudents = 0;
    private int noOfDecks = 0;

    public int getNoOfStudents() {
        return noOfStudents;
    }

    public void setNoOfStudents(int noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    public int getNoOfDecks() {
        return noOfDecks;
    }

    public void setNoOfDecks(int noOfDecks) {
        this.noOfDecks = noOfDecks;
    }

    public StudentExamMatrix(String student_id, String deck_id) {
        //Create the student exam matrix
        compileInputMatrix(student_id, deck_id); 
        // target student ID as args[0]; deck ID as args[1]
    }

    private void compileInputMatrix(String student_id, String deck_id) {

        //TODO: fetch data from Learning Locker
        
        //Create input matrix
        HashMap<String, String> examsForRequestedUser = ActivitiesServiceConnector.getMapOfExamsForUser(student_id);//get all exams for requested user
        HashMap<String, String> usersForRequestedExam = ActivitiesServiceConnector.getMapOfExamsForDeck(deck_id);//get all exams for requested deck (different users)
        
        noOfStudents = usersForRequestedExam.size();
        noOfDecks = examsForRequestedUser.size();
        
        for (String currentUserId: usersForRequestedExam.keySet()) {
            
            HashMap<String, String> currentUserExams = ActivitiesServiceConnector.getMapOfExamsForUser(currentUserId);
            for (String currentDeckId: examsForRequestedUser.keySet()) {
                String currentUserScoreForCurrentExam = currentUserExams.get(currentDeckId);
                
                if (currentUserScoreForCurrentExam == null) {//current user has not taken current exam
                    currentUserScoreForCurrentExam = "0";
                }
                
                String line = currentUserId + "::" + currentDeckId + "::" + currentUserScoreForCurrentExam;
                inputMatrix.add(line);
            }
                        
            String line = currentUserId + "::" + deck_id + "::" + usersForRequestedExam.get(currentUserId);
            inputMatrix.add(line);
        }
        
        //add scores for the requested user (the last row)
        for (String currentDeckId: examsForRequestedUser.keySet()) {
            String line = student_id + "::" + currentDeckId + "::" + examsForRequestedUser.get(currentDeckId);
            inputMatrix.add(line);  
        }
    }

    public List<String> getInputMatrix() {
        return inputMatrix;
    }
    
    public List<String> getDummyInputMatrix() {
        
        inputMatrix = new ArrayList<>();
        
        String path_file = ("win".equals(Config.getProperty("OS"))) ? Config.getProperty("TEMP_TEST_FILE_PATH_WIN") : Config.getProperty("TEMP_TEST_FILE_PATH_UBUNTU");
        try (BufferedReader br = new BufferedReader(new FileReader(path_file))) {
            String line;
            while ((line = br.readLine()) != null) {
                inputMatrix.add(line);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        
        return inputMatrix;
    }
	
}